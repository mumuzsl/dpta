$(function () {
    $("#jqGrid").jqGrid({
        url: '/platform/api/settleM/all',
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'id', index: 'id', width: 60, key: true},
            {label: '分销商', name: 'distrNm', index: 'distrNm', width: 60},
            {label: '成本', name: 'basP', index: 'basP', width: 60},
            {label: '佣金', name: 'comP', index: 'comP', width: 60},
            {label: '税费', name: 'taxP', index: 'taxP', width: 60},
            {label: '平台收益', name: 'plaP', index: 'pafP', width: 60},
            {label: '结算时间', name: 'settleTm', index: 'settleTm', width: 60}
        ],
        height: 760,
        rowNum: 20,
        rowList: [20, 50, 80],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });
});

/**
 * jqGrid重新加载
 */
function reload() {
    $("#month").val("");
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        url: '/platform/api/settleM/all'
    }).trigger("reloadGrid");
}
function reset() {

}

$("#month").on("change",function () {
    var month = $("#month").val();
    $("#jqGrid").jqGrid('setGridParam',{
        url: "/platform/api/settleM/month?month="+month
    }).trigger("reloadGrid");
});


