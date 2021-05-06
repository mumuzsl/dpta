$(function () {
    var name = $("#name").val();
    if (name=="")
        name="*";
    $("#jqGrid").jqGrid({
        url: '/platform/api/refund-rule/findByNm/'+name,
        datatype: "json",
        colModel: [
            {label: '规则编码', name: 'refundId', index: 'refundId', width: 60, key: true},
            {label: '规则名称', name: 'refundNm', index: 'refundNm', width: 60},
            {label: '规则状态', name: 'state', index: 'state', width: 60,formatter: rStatusFormatter},
            {label: '规则类型', name: 'type', index: 'type', width: 60},
            {label: '分润预留时间（天）', name: 'reserveTm', index: 'reserveTm', width: 60},
            {label: '退款条件', name: 'refundIf', index: 'refundIf', width: 60},
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

    function rStatusFormatter(cellvalue) {
        //规则状态 0-禁用 1-启用
        if (cellvalue == 0) {
            return "<button type=\"button\" class=\"btn btn-block btn-secondary btn-sm\" style=\"width: 80%;\">禁用中</button>";
        }
        if (cellvalue == 1) {
            return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 80%;\">启用中</button>";
        }
    }

    function commROperateFormatter(cellvalue, rowObject) {
        return "<a href=\'##\' onclick=display(" + rowObject.rowId + ")>查看绑定的商品</a>"
    }


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
    $("#refundNm").val('');
    $("#type").val('');
    $("#reserveTm").val(0);
    $("#refundIf").val('');
    $('#edit-error-msg').css("display", "none");
}

/**
 * 查看绑定的商品
 */
function display(commrId) {
    $('.modal-title').html('查看绑定的商品');
    $('#comminfoModal').modal('show');
    $("#jqGridc").jqGrid({
        url: '/platform/api/paf-comm-rule/detail/'+commrId,
        datatype: "json",
        colModel: [
            {label: '商品编号', name: 'commId', index: 'commId', width: 60, key: true},
            {label: '商品名称', name: 'commNm', index: 'commNm', width: 120},
            {label: '商品简介', name: 'commD', index: 'commD', width: 120},
            {label: '商品图片', name: 'imgUrl', index: 'imgUrl', width: 120, formatter: coverImageFormatter},
            {
                label: '上架状态',
                name: 'state',
                index: 'state',
                width: 80,
                formatter: SellStatusFormatter
            }
        ],
        width: 800,
        height: 760,
        rowNum: 20,
        rowList: [20, 50, 80],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager1",
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order",
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGridc").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    $(window).resize(function () {
        $("#jqGridc").setGridWidth($(".card-body").width());
    });
}
function SellStatusFormatter(cellvalue) {
    //商品上架状态 1-上架 0-下架
    if (cellvalue == 1) {
        return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 80%;\">销售中</button>";
    }
    if (cellvalue == 0) {
        return "<button type=\"button\" class=\"btn btn-block btn-secondary btn-sm\" style=\"width: 80%;\">已下架</button>";
    }
}

function coverImageFormatter(cellvalue) {
    return "<img src='" + cellvalue + "' height=\"80\" width=\"80\" alt='商品主图'/>";
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
                    url: "/platform/api/refund-rule/enable",
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
    $('#jqGrid').jqGrid('setGridParam', {url: '/platform/api/refund-rule/findByNm/'+name}).trigger('reloadGrid');
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
                    url: "/platform/api/refund-rule/disable",
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
    $("#refundId").val(id);
    $("#refundNm").val(rowData.refundNm);
    $("#type").val(rowData.type);
    $("#reserveTm").val(rowData.reserveTm);
    $("#fla").val(2);
}

/**
 * 删除退款规则
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
                    url: "/platform/api/refund-rule/del",
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
    var refundId = $("#refundId").val();
    var refundNm = $("#refundNm").val();
    var type = $("#type").val();
    var reserveTm = $("#reserveTm").val();
    var refundIf = $("#refundIf").val();
    if (refundNm == null) return;
    var flag = $("#fla").val();
    //var id = getSelectedRowWithoutAlert();
    var url;
    var data;
    var mes;
    if (flag == 1) {
        mes = "新增成功";
        url = '/platform/api/refund-rule/add';
        data = {
            "refundNm": refundNm,
            "type": type,
            "state": 1,
            "reserveTm": reserveTm,
            "refundIf": refundIf
        }
    }
    else if (flag == 2) {
        mes = "修改成功";
        url = '/platform/api/refund-rule/modif';
        data = {
            "refundId": refundId,
            "refundNm": refundNm,
            "type": type,
            "reserveTm": reserveTm,
            "refundIf": refundIf
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

/**
 * 删除商品
 */

function deleteGoods() {
    var ids = getSelectedRows();
    if (ids = null){
        return;
    }
    swal({
        title:"确认弹框",
        text:"确认要删除数据吗",
        icon:"warning",
        buttons:true,
        dangerMode:true,
    }).then(flag=>{
        if(flag){
            $.ajax({
                type:"POST",
                url:"admin/goods/delete",
                contentType:"application/json",
                data:JSON.stringify(ids),
                success:function (r) {
                    if (r.resultCode == 200){
                        swal("删除成功",{
                            icon:"success",
                        });
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    else{
                        swal(r.message,{
                            icon:"error",
                        });
                    }
                }
            })
        }
    });
}
/**
 * 上架
 */
function putUpGoods() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要执行上架操作吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "PUT",
                    url: "/admin/goods/status/0",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.resultCode == 200) {
                            swal("上架成功", {
                                icon: "success",
                            });
                            $("#jqGrid").trigger("reloadGrid");
                        } else {
                            swal(r.message, {
                                icon: "error",
                            });
                        }
                    }
                });
            }
        }
    )
    ;
}

/**
 * 下架
 */
function putDownGoods() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要执行下架操作吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "PUT",
                    url: "/admin/goods/status/1",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.resultCode == 200) {
                            swal("下架成功", {
                                icon: "success",
                            });
                            $("#jqGrid").trigger("reloadGrid");
                        } else {
                            swal(r.message, {
                                icon: "error",
                            });
                        }
                    }
                });
            }
        }
    )
    ;
}

