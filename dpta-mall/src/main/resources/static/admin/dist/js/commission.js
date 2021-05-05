$(function () {
    $("#jqGrid").jqGrid({
        url: '/platform/api/paf-comm-rule/all',
        datatype: "json",
        colModel: [
            {label: '规则编码', name: 'rCommId', index: 'rCommId', width: 60, key: true},
            {label: '规则名称', name: 'rCommNm', index: 'rCommNm', width: 60},
            {label: '规则状态', name: 'state', index: 'state', width: 60,formatter: commRStatusFormatter},
            {label: '规则类型', name: 'type', index: 'type', width: 60},
            {label: '分润值', name: 'value', index: 'value', width: 60},
            {label: '操作',width: 60,formatter: commROperateFormatter}
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
            commr: "commr",
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });

    function commRStatusFormatter(cellvalue) {
        //规则状态 0-禁用 1-启用
        if (cellvalue == 0) {
            return "<button type=\"button\" class=\"btn btn-block btn-secondary btn-sm\" style=\"width: 80%;\">禁用中</button>";
        }
        if (cellvalue == 1) {
            return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 80%;\">启用中</button>";
        }
    }

    function commROperateFormatter(cellvalue, rowObject) {
        return "<a href=\"#myPopup1\" onclick=viewRecords("+rowObject.rowId+") data-rel=\"popup\" class=\"ui-btn\" data-position-to=\"window\">查看绑定的商品</a>"
    }


});

function viewRecords(id) {
    if (id == "") {
        return;
    }
    $('#myPopup1').modal('show');
    $("#jqGridr").jqGrid({
        url: '/platform/api/paf-comm-rule/detail/'+id,
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
        height: 500,
        rowNum: 20,
        rowList: [20, 50, 80],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
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
    $('#jqGridr').jqGrid('setGridParam', {url: '/platform/api/paf-comm-rule/detail/'+id}).trigger('reloadGrid');
}


/**
 * jqGrid重新加载
 */
function reload() {
    //initFlatPickr();
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        url: "/platform/api/paf-comm-rule/all",
        page: page
    }).trigger("reloadGrid");
}
function reset() {
    $("#rCommNm").val('');
    $("#type").val('');
    $("#value").val(0);
    $('#edit-error-msg').css("display", "none");
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
function commrEnable() {
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
                    url: "/platform/api/paf-comm-rule/enable",
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
        reload();
    }else{
        $('#jqGrid').jqGrid('setGridParam', {url: '/platform/api/paf-comm-rule/search0/'+name}).trigger('reloadGrid');
    }
}
/**
 * 禁用规则
 */
function commrDisable() {
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
                    url: "/platform/api/paf-comm-rule/disable",
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
function commrAdd() {
    reset();
    $('.modal-title').html('新增规则');
    $('#commrInfoModal').modal('show');
    $("#fla").val(1);
}

/**
 * 规则编辑
 */
function commrEdit() {
    //reset();
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    var rowData = $("#jqGrid").jqGrid("getRowData", id);
    $('.modal-title').html('规则编辑');
    $('#commrInfoModal').modal('show');
    $("#rCommId").val(id);
    $("#rCommNm").val(rowData.rCommNm);
    $("#type").val(rowData.type);
    $("#value").val(rowData.value);
    $("#fla").val(2);
}

/**
 * 删除佣金规则
 */
function commrDel() {
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
                    url: "/platform/api/paf-comm-rule/del",
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
    var rCommId = $("#rCommId").val();
    var rCommNm = $("#rCommNm").val();
    var type = $("#type").val();
    var value = $("#value").val();
    var flag = $("#fla").val();
    //var id = getSelectedRowWithoutAlert();
    var url;
    var data;
    var mes;
    if (flag == 1) {
        mes = "新增成功";
        url = '/platform/api/paf-comm-rule/add';
        data = {
            "rCommNm": rCommNm,
            "type": type,
            "state": 1,
            "value": value
        }
    }
    else if (flag == 2) {
        mes = "修改成功";
        url = '/platform/api/paf-comm-rule/modif';
        data = {
            "rCommId": rCommId,
            "rCommNm": rCommNm,
            "type": type,
            "value": value
        }
    };
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
            if (result.code == 200) {
                $('#commrInfoModal').modal('hide');
                swal(mes, {
                    icon: "success"
                });
                reload();
            } else {
                $('#commrInfoModal').modal('hide');
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

