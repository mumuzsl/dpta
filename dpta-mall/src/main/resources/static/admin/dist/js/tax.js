$(function () {
    $("#jqGrid").jqGrid({
        url: '/platform/api/tax-rule/list',
        datatype: "json",
        colModel: [
            {label: '规则编码', name: 'taxId', index: 'taxId', width: 60, key: true},
            {label: '起征点（元）', name: 'pStart', index: 'pStart', width: 60},
            {label: '起征上限（元）', name: 'pEnd', index: 'pEnd', width: 60},
            {label: '征税比例', name: 'rate', index: 'rate', width: 60},
            {label: '速算扣除数（元）', name: 'deduction', index: 'deduction', width: 60}
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
    $("#taxId").val('');
    $("#pStart").val(0);
    $("#pEnd").val(0);
    $("#rate").val(0.0);
    $("#deduction").val(0);
    $('#edit-error-msg').css("display", "none");
}


/**
 * 规则新增
 */
function rAdd() {
    reset();
    $('.modal-title').html('新增规则');
    $('#rInfoModal').modal('show');
    $("#fla").val(1);
}

/**
 * 规则编辑
 */
function rEdit() {
    //reset();
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    var rowData = $("#jqGrid").jqGrid("getRowData", id);
    $('.modal-title').html('规则编辑');
    $('#rInfoModal').modal('show');
    $("#taxId").val(id);
    $("#pStart").val(rowData.pStart);
    $("#pEnd").val(rowData.pEnd);
    $("#rate").val(rowData.rate);
    $("#deduction").val(rowData.deduction);
    $("#fla").val(2);
}

/**
 * 删除规则
 */
function rDel() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要删除吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/platform/api/tax-rule/del",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 200) {
                            swal("删除成功", {
                                icon: "success",
                            });
                            $("#jqGrid").trigger("reloadGrid");
                        } else {
                            if (r.code == 251) {
                                swal(r.message, {
                                    icon: "error",
                                });
                                $("#jqGrid").trigger("reloadGrid");
                            }
                            else {
                                swal(r.message, {
                                    icon: "error",
                                });
                            }
                        }
                    }
                });
            }
        }
    )
    ;
}
//绑定modal上的保存按钮
$('#saveButton').click(function () {
    var taxId = $("#taxId").val();
    var pStart = $("#pStart").val();
    var pEnd = $("#pEnd").val();
    var rate = $("#rate").val();
    var deduction = $("#deduction").val();
    var flag = $("#fla").val();
    //var id = getSelectedRowWithoutAlert();
    var url;
    var data;
    var mes;
    if (flag == 1) {
        mes = "新增成功";
        url = '/platform/api/tax-rule/add';
        data = {
            "pStart": pStart,
            "pEnd": pEnd,
            "rate": rate,
            "deduction": deduction
        }
    }
    else if (flag == 2) {
        mes = "修改成功";
        url = '/platform/api/tax-rule/modif';
        data = {
            "taxId": taxId,
            "pStart": pStart,
            "pEnd": pEnd,
            "rate": rate,
            "deduction": deduction
        }
    };
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
            if (result.code == 200) {
                $('#rInfoModal').modal('hide');
                swal(mes, {
                    icon: "success"
                });
                reload();
            } else {
                $('#rInfoModal').modal('hide');
                swal(result.message, {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    });
});

