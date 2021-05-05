$(function () {
    var name = $("#name").val();
    if (name=="")
        name="*";
    $("#jqGrid").jqGrid({
        url: '/platform/api/credit/findByNm1/'+name,
        datatype: "json",
        colModel: [
            {label: '授信编码', name: 'creditId', index: 'creditId', width: 60, key: true},
            {label: '供应商名称', name: 'suppNm', index: 'suppNm', width: 60},
            {label: '分销商名称', name: 'distrNm', index: 'distrNm', width: 60},
            {label: '授信金额', name: 'creditAmout', index: 'creditAmout', width: 60}
            //{label: '操作',width: 60,formatter: commROperateFormatter}
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
 * 审核通过
 */
function rEnable() {
    var ids = getSelectedRows();
    if(ids == null)
        return;
    swal({
        title: "确认弹框",
        text: "确认通过吗?",
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
                            swal("审核通过", {
                                icon: "success",
                            });
                            $("#jqGrid").trigger("reloadGrid");
                        } else {
                            swal("审核失败", {
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
    $('#jqGrid').jqGrid('setGridParam', {url: '/platform/api/credit/findByNm1/'+name}).trigger('reloadGrid');
}
/**
 * 审核驳回
 */
function rDisable() {
    var ids = getSelectedRows();
    if(ids == null)
        return;
    swal({
        title: "确认弹框",
        text: "确认要驳回申请吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/platform/api/credit/reject",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 200) {
                            swal("驳回成功", {
                                icon: "success",
                            });
                            $("#jqGrid").trigger("reloadGrid");
                        } else {
                            swal("驳回失败", {
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
