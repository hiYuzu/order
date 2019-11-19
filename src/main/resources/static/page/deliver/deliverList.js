var typeCodeCt = 'CT';
layui.config({
    base: "js/"
}).use(['form', 'layer', 'jquery', 'table'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        table = layui.table,
        $ = layui.jquery;

    $.ajax({
        url: "../../AuthoritySetController/isAuthorityPage",
        type: "post",
        dataType: "json",
        data: {
            "pageCode": "deliver"
        },
        error: function (json) {
            parent.window.location.href = "../../login.html";
        },
        success: function (json) {
            if (json != null) {
                if (json.result) {
                    initDeliverPage();
                } else {
                    layer.open({
                        content: json.detail
                        , skin: 'msg'
                        , time: 2000//2秒后自动关闭
                        , end: function () {
                            location.href = 'page/404.html';
                        }
                    });
                }
            } else {
                top.layer.msg("页面权限判断失败！");
            }
        }
    });

    function initDeliverPage() {
        //初始化下拉框
        initSelect();
        //初始化列表
        var ctCode = $("#searchCtCode").val();
        var ctType = $("#searchCtType").val();
        var customerName = $("#searchCustomerName").val();
        var deliverStatus = $("#searchDeliverStatus").val();
        var finishFlag = $("#searchFinishFlag").val();
        searchDeliver(ctCode, ctType, customerName, deliverStatus, finishFlag);
    }


    function initSelect() {
        //合同类型
        $.ajax({
            url: "../../ParamController/getParamByType",
            type: "post",
            dataType: "json",
            data: {typeCode: typeCodeCt},
            error: function (json) {
                top.layer.msg("初始化合同类型失败！");
            },
            success: function (json) {
                if (json != null && json.data != null) {
                    var optionStr = "<option value=''>---请选择---</option>";
                    $.each(json.data, function (index, value) {
                        optionStr += "<option value='" + value.paramCode + "'>" + value.paramName + "</option>";
                    });
                    $("#searchCtType").append(optionStr);
                    form.render('select');
                } else {
                    top.layer.msg("初始化合同类型失败！");
                }
            }
        });
        //发货单状态
        $.ajax({
            url: "../../DeliverStatusController/getDeliverStatus",
            type: "post",
            dataType: "json",
            error: function () {
                top.layer.msg("初始化发货单状态失败！");
            },
            success: function (json) {
                if (json != null && json.data != null) {
                    var optionStr = "<option value=''>---请选择---</option>";
                    $.each(json.data, function (index, value) {
                        optionStr += "<option value='" + value.statusCode + "'>" + value.statusName + "</option>";
                    });
                    $("#searchDeliverStatus").append(optionStr);
                    form.render('select');
                } else {
                    top.layer.msg("初始化发货单状态失败！");
                }
            }
        });
    }

    function searchDeliver(ctCode, ctType, customerName, deliverStatus, finishFlag) {
        table.render({
            id: 'deliverId,deliverType,contractTypeCode,deliverStatusCode,goodsModelList',
            elem: '#deliverListTable',
            url: '../../DeliverController/getDeliver',
            method: 'post',
            where: {
                contractCode: ctCode,
                contractTypeCode: ctType,
                customerName: customerName,
                deliverStatusCode: deliverStatus,
                finishFlag: finishFlag
            } //传参*/
            ,
            page: true //开启分页
            ,
            cols: [[{field: 'contractCode', title: '合同号码', minWidth: '160', sort: true}
                , {field: 'contractTypeName', title: '合同类型', minWidth: '100', sort: true}
                , {field: 'deliverTypeName', title: '单据类型', minWidth: '100', sort: true}
                , {field: 'customerName', title: '客户名称', minWidth: '160', sort: true}
                , {field: 'deliverDate', title: '发货日期', minWidth: '120', sort: true}
                , {field: 'warrantyDate', title: '发保日期', minWidth: '120', sort: true}
                , {field: 'deliverAddress', title: '发货起点', minWidth: '180'}
                , {field: 'deliverStatusName', title: '发货单状态', minWidth: '140', sort: true}
                , {field: 'deliverRemark', title: '备注', minWidth: '180'}
                , {field: 'optUserName', title: '操作者', minWidth: '120'}
                , {field: 'optTime', title: '操作时间', minWidth: '180'}
                , {title: '操作', fixed: 'right', minWidth: 160, align: 'center', toolbar: '#barDeliverList'}
            ]]
        });
    }

    //监听工具条
    table.on('tool(deliverListFilter)', function (obj) {
        var data = obj.data;
        if (data == null || data == "") {
            layer.msg("请选择需要操作的数据！");
            return;
        }
        if (obj.event === 'select') {
            var content;
            if (data.deliverType === "2") {
                content = "../outsourcing/outGoodsList.html";
            } else {
                content = "goodsList.html";
            }
            var index = layui.layer.open({
                title: "设备列表"
                , type: 2
                , shadeClose: true
                , area: ['90%', '80%']
                , shade: 0.8
                , content: content
                , success: function (layero, index) {
                    var frameWin = window[layero.find('iframe')[0]['name']];
                    frameWin.showFormData(data.deliverId);
                }
            });
        } else if (obj.event === 'del') {
            layer.confirm('确认删除合同编号是\'' + data.contractCode + '\'的发货单吗', function (index) {
                var idArray = [];
                idArray.push(data.deliverId);
                //弹出loading
                var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
                $.ajax({
                    url: "../../DeliverController/deleteDelivers",
                    type: "post",
                    dataType: "json",
                    data: {"list": idArray},
                    error: function (json) {
                        top.layer.close(index);
                        top.layer.msg("发货单删除失败！");
                    },
                    success: function (json) {
                        top.layer.close(index);
                        if (json.result) {
                            top.layer.msg("发货单删除成功！");
                            var ctCode = $("#searchCtCode").val();
                            var ctType = $("#searchCtType").val();
                            var customerName = $("#searchCustomerName").val();
                            var deliverStatus = $("#searchDeliverStatus").val();
                            var finishFlag = $("#searchFinishFlag").val();
                            searchDeliver(ctCode, ctType, customerName, deliverStatus, finishFlag);
                        } else {
                            top.layer.msg(json.detail);
                        }
                    }
                });
            });
        } else if (obj.event === 'edit') {
            var content;
            if (data.deliverType === "2") {
                content = "../outsourcing/editOutsourcing.html";
            } else {
                content = "editDeliver.html";
            }
            var index = layui.layer.open({
                title: "修改发货单",
                type: 2,
                content: content,
                success: function (layero, index) {
                    var frameWin = window[layero.find('iframe')[0]['name']];
                    frameWin.showFormData(data);
                }
            });
            //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
            $(window).resize(function () {
                layui.layer.full(index);
            });
            layui.layer.full(index);
        }
    });
    //添加查询
    $(".search_btn").click(function () {
        var ctCode = $("#searchCtCode").val();
        var ctType = $("#searchCtType").val();
        var customerName = $("#searchCustomerName").val();
        var deliverStatus = $("#searchDeliverStatus").val();
        var finishFlag = $("#searchFinishFlag").val();
        searchDeliver(ctCode, ctType, customerName, deliverStatus, finishFlag);
    });

    //添加发货单
    $(".usersAdd_btn").click(function () {
        var index = layui.layer.open({
            title: "添加发货单",
            type: 2,
            content: "addDeliver.html",
            success: function (layero, index) {
                // layui.layer.tips('点击此处返回文章列表', '.layui-layer-setwin .layui-layer-close', {
                // 	tips: 3
                // });
            }
        });
        //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
        $(window).resize(function () {
            layui.layer.full(index);
        });
        layui.layer.full(index);
    });

    //添加导出
    $(".exportExcel_btn").click(function () {
        var ctCode = $("#searchCtCode").val();
        var ctType = $("#searchCtType").val();
        var customerName = $("#searchCustomerName").val();
        var deliverStatus = $("#searchDeliverStatus").val();
        var finishFlag = $("#searchFinishFlag").val();
        var params = {
            "contractCode": ctCode,
            "contractTypeCode": ctType,
            "customerName": customerName,
            "deliverStatusCode": deliverStatus,
            "finishFlag": finishFlag,
            "type": "deliver"
        };
        outPutDeliverData(params);
    });

    /**
     * 导出发货记录
     */
    function outPutDeliverData(params) {
        var form = $("<form>");//定义一个form表单
        form.attr("style", "display:none");
        form.attr("target", "");
        form.attr("method", "post");
        form.attr("action", "../../ReportController/downloadDeliverListExcel");
        for (var key in params) {
            var input = $("<input>");
            input.attr("type", "hidden");
            input.attr("name", key);
            input.attr("value", params[key]);
            form.append(input);
        }
        $("body").append(form);//将表单放置在web中
        form.submit();//表单提交
    }

});