$(function () {
    var name = $("#name").val();
    if (name=="")
        name="*";
    $("#jqGrid").jqGrid({
        url: '/platform/api/credit/findByNm/'+name,
        datatype: "json",
        colModel: [
            {label: '授信编码', name: 'creditId', index: 'creditId', width: 60, key: true},
            {label: '供应商名称', name: 'suppNm', index: 'suppNm', width: 60},
            {label: '分销商名称', name: 'distrNm', index: 'distrNm', width: 60},
            {label: '授信金额', name: 'creditAmout', index: 'creditAmout', width: 60},
            {label: '已用授信金额', name: 'usedAmout', index: 'usedAmout', width: 60},
            {label: '状态', name: 'state', index: 'state', width: 60,formatter: rStatusFormatter},
            {label: '操作',width: 60,formatter: operateFormatter}
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

    function rStatusFormatter(cellvalue) {
        //规则状态 2-禁用 1-启用
        if (cellvalue == 2) {
            return "<button type=\"button\" class=\"btn btn-block btn-secondary btn-sm\" style=\"width: 80%;\">禁用中</button>";
        }
        if (cellvalue == 1) {
            return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 80%;\">启用中</button>";
        }
    }

    function operateFormatter(cellvalue, rowObject) {
        return "<a href=\"#myPopup1\" onclick=viewRecords("+rowObject.rowId+") data-rel=\"popup\" class=\"ui-btn\" data-position-to=\"window\">浏览还款记录</a>" +
            "<a href=\"#myPopup2\" onclick=viewRecords1("+rowObject.rowId+") data-rel=\"popup\" class=\"ui-btn\" data-position-to=\"window\" style=\"margin-left:20px;\">浏览交易记录</a>"
    }
});

function viewRecords(id) {
    if (id == "") {
        return;
    }
    $('#myPopup1').modal('show');
    $("#jqGridr").jqGrid({
        url: '/platform/api/credit/viewCreditR/'+id,
        datatype: "json",
        colModel: [
            {label: '授信明细编码', name: 'dCreId', index: 'dCreId', width: 200, key: true},
            {label: '授信编码', name: 'creditId', index: 'creditId', width: 200},
            {label: '类型', name: 'type', index: 'type', width: 200,formatter: typeFormatter},
            {label: '金额', name: 'amount', index: 'amount', width: 200},
            {label: '订单编码', name: 'dealId', index: 'dealId', width: 200},
            {label: '已用授信金额', name: 'usedAmout', index: 'usedAmout', width: 200},
            {label: '创建日期', name: 'createTm', index: 'createTm', width: 200}
        ],
        height: 500,
        rowNum: 20,
        rowList: [20, 50, 80],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: false,
        multiselect: true,
        pager: "#jqGridrPager",
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
            $("#jqGridr").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
    $(window).resize(function () {
        $("#jqGridr").setGridWidth($(".card-body").width());
    });
    $('#jqGridr').jqGrid('setGridParam', {url: '/platform/api/credit/viewCreditR/'+id}).trigger('reloadGrid');
}

function viewRecords1(id) {
    if (id == "") {
        return;
    }
    $('#myPopup2').modal('show');
    $("#jqGridr1").jqGrid({
        url: '/platform/api/credit/viewCreditR1/'+id,
        datatype: "json",
        colModel: [
            {label: '订单编码', name: 'dealId', index: 'dealId', width: 200, key: true},
            {label: '商品名称', name: 'commNm', index: 'commNm', width: 200},
            {label: '购买数量', name: 'count', index: 'count', width: 200},
            {label: '商品单价', name: 'price', index: 'price', width: 200},
            {label: '购买时间', name: 'createTime', index: 'createTime', width: 200}
        ],
        height: 500,
        rowNum: 20,
        rowList: [20, 50, 80],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: false,
        multiselect: true,
        pager: "#jqGridrPager1",
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
            $("#jqGridr1").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
    $(window).resize(function () {
        $("#jqGridr1").setGridWidth($(".card-body").width());
    });
    $('#jqGridr1').jqGrid('setGridParam', {url: '/platform/api/credit/viewCreditR1/'+id}).trigger('reloadGrid');
}

function typeFormatter(cellvalue) {
//规则状态 2-授信还款 1-使用授信
    if (cellvalue == 1) {
        return "<button type=\"button\" class=\"btn btn-block btn-danger btn-sm\" style=\"width: 80%;\">使用授信</button>";
    }
    if (cellvalue == 2) {
        return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 80%;\">授信还款</button>";
    }
}

/**
 * jqGrid重新加载
 */
function reload() {
    //initFlatPickr();
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}
function reset() {

    $('#edit-error-msg').css("display", "none");
}

/**
 * 启用规则
 */
function rEnable() {
    var ids = getSelectedRows();
    if(ids == null)
        return;
    swal({
        title: "确认弹框",
        text: "确认要启用吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/platform/api/credit/enable",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 200) {
                            swal("启用成功", {
                                icon: "success",
                            });
                            $("#jqGrid").trigger("reloadGrid");
                        } else {
                            swal("启用失败", {
                                icon: "error",
                            });
                            $("#jqGrid").trigger("reloadGrid");
                        }
                    }
                });
            }
        }
    )
}

/**
 * 按照规则名称搜索
 */
function search() {
    var name = $("#name").val();
    if (name == "") {
        name = "*";
    }
    $('#jqGrid').jqGrid('setGridParam', {url: '/platform/api/credit/findByNm/'+name}).trigger('reloadGrid');
}
/**
 * 禁用规则
 */
function rDisable() {
    var ids = getSelectedRows();
    if(ids == null)
        return;
    swal({
        title: "确认弹框",
        text: "确认要禁用吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/platform/api/credit/disable",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 200) {
                            swal("禁用成功", {
                                icon: "success",
                            });
                            $("#jqGrid").trigger("reloadGrid");
                        } else {
                            swal("禁用失败", {
                                icon: "error",
                            });
                            $("#jqGrid").trigger("reloadGrid");
                        }
                    }
                });
            }
        }
    )
}
