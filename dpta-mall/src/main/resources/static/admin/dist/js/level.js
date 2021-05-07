$(function () {
    $("#jqGrid").jqGrid({
        url: '/platform/api/distr-level/list',
        datatype: "json",
        colModel: [
            {label: '等级编码', name: 'levelId', index: 'levelId', width: 60, key: true},
            {label: '等级名称', name: 'levelNm', index: 'levelNm', width: 60},
            {label: '分润比例', name: 'yield', index: 'yield', width: 60},
            {label: '入驻时长（月）', name: 'settledM', index: 'settledM', width: 60},
            {label: '平台收益', name: 'pafP', index: 'pafP', width: 60},
            {label: '更新日期', name: 'updateTm', index: 'updateTm', width: 60}
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
    $("#levelId").val('');
    $("#levelNm").val('');
    $("#settledM").val('');
    $("#yield").val('');
    $("#pafP").val('');
    $("#updateTm").val('');
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
    $("#levelId").val(id);
    $("#levelNm").val(rowData.levelNm);
    $("#yield").val(rowData.yield);
    $("#settledM").val(rowData.settledM);
    $("#updateTm").val(rowData.updateTm);
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
                    url: "/platform/api/distr-level/del",
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
    var levelId = $("#levelId").val();
    var levelNm = $("#levelNm").val();
    var yield = $("#yield").val();
    var settledM = $("#settledM").val();
    var pafP = $("#pafP").val();
    var updateTM = $("#updateTm").val();
    var flag = $("#fla").val();
    //var id = getSelectedRowWithoutAlert();
    var url;
    var data;
    var mes;
    if (flag == 1) {
        mes = "新增成功";
        url = '/platform/api/distr-level/add';
        data = {
            "levelNm": levelNm,
            "yield": yield,
            "settledM": settledM,
            "pafP": pafP,
            "updateTm": updateTM
        }
    }
    else if (flag == 2) {
        mes = "修改成功";
        url = '/platform/api/distr-level/modif';
        data = {
            "levelId": levelId,
            "levelNm": levelNm,
            "yield": yield,
            "settledM": settledM,
            "pafP": pafP,
            "updateTm": updateTM
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

