var typeCodeCt = 'CT';
var bsUserType = 2;
var loginUserId;
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
            "pageCode": "business"
        },
        error: function (json) {
            parent.window.location.href = "../../login.html";
        },
        success: function (json) {
            if (json != null) {
                if (json.result) {
                    initBusinessPage();
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

    function initBusinessPage() {
        $.ajax({
            url: "../../UserController/getLoginUser",
            type: "post",
            dataType: "json",
            async: false,
            error: function (json) {
                layer.msg("获取登录用户信息失败！");
            },
            success: function (json) {
                if (json != null) {
                    loginUserId = json.userId;
                    form.render();
                } else {
                    layer.msg("获取登录用户信息失败！");
                }
            }
        });
        //初始化下拉框
        initSelect();
        //初始化列表
        var bsUser = $("#searchBsUser").val();
        var ctCode = $("#searchCtCode").val();
        var ctType = $("#searchCtType").val();
        var customerName = $("#searchCustomerName").val();
        var deliverRemark = $("#searchDeliverRemark").val();
        var deliverStatus = $("#searchDeliverStatus").val();
        var finishFlag = $("#searchFinishFlag").val();
        searchDeliver(bsUser, ctCode, ctType, customerName, deliverRemark, deliverStatus, finishFlag);
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
        //商务用户
        $.ajax({
            url: "../../UserController/getUser",
            type: "post",
            dataType: "json",
            data: {userType: bsUserType},
            async: false,
            error: function (json) {
                top.layer.msg("初始化商务用户失败！");
            },
            success: function (json) {
                if (json != null && json.data != null) {
                    var optionStr = "<option value=''>---请选择---</option>";
                    $.each(json.data, function (index, value) {
                        optionStr += "<option value='" + value.userId + "'>" + value.userName + "</option>";
                    });
                    $("#searchBsUser").append(optionStr);
                    $("#searchBsUser option[value='" + loginUserId + "']").attr("selected", true);
                    form.render('select');
                } else {
                    top.layer.msg("初始化商务用户失败！");
                }
            }
        });
        //发货单状态
        $.ajax({
            url: "../../DeliverStatusController/getDeliverStatus",
            type: "post",
            dataType: "json",
            // async: false,
            error: function () {
                top.layer.msg("初始化发货单状态失败！");
            },
            success: function (json) {
                if (json != null && json.data != null) {
                    var optionStr = "<option value=''>---请选择---</option>";
                    $.each(json.data, function (index, value) {
                        if (value.statusCode != "100" && value.statusCode != "102") {//去掉已保存和被拒绝
                            optionStr += "<option value='" + value.statusCode + "'>" + value.statusName + "</option>";
                        }
                    });
                    $("#searchDeliverStatus").append(optionStr);
                    form.render('select');
                } else {
                    top.layer.msg("初始化发货单状态失败！");
                }
            }
        });
    }

    function searchDeliver(bsUser, ctCode, ctType, customerName, deliverRemark, deliverStatus, finishFlag) {
        table.render({
            id: 'deliverId,deliverType,contractTypeCode,deliverStatusCode,deliverDate,warrantyDate,deliverAddress,goodsModelList,contractNewCode',
            elem: '#deliverListTable',
            url: '../../BusinessController/getDeliver',
            method: 'post',
            where: {
                businessUserId: bsUser,
                contractCode: ctCode,
                contractTypeCode: ctType,
                customerName: customerName,
                deliverRemark: deliverRemark,
                deliverStatusCode: deliverStatus,
                finishFlag: finishFlag
            } //传参*/
            ,
            page: true //开启分页
            ,
            cols: [[{field: 'contractCode', title: '合同号码', minWidth: '160', sort: true}
                , {field: 'contractTypeName', title: '合同类型', minWidth: '100', sort: true}
                , {field: 'contractAmount', title: '合同金额', minWidth: '100', sort: true}
                , {field: 'deliverTypeName', title: '单据类型', minWidth: '100', sort: true}
                , {field: 'customerName', title: '客户名称', minWidth: '160', sort: true}
                , {field: 'deliverNo', title: '运单号', minWidth: '160'}
                , {field: 'contractWarranty', title: '合同保质期', minWidth: '120', sort: true}
                , {field: 'contractBeginTime', title: '合同开始时间', minWidth: '120', sort: true}
                , {field: 'contractEndTime', title: '合同结束时间', minWidth: '120', sort: true}
                , {field: 'deliverStatusName', title: '发货单状态', minWidth: '140', sort: true}
                , {field: 'deliverRemark', title: '备注', minWidth: '180'}
                , {field: 'optUserName', title: '操作者', minWidth: '80'}
                , {field: 'optTime', title: '操作时间', minWidth: '160'}
                , {title: '操作', fixed: 'right', minWidth: '240', align: 'center', toolbar: '#barDeliverList'}
            ]]
        });
    }

    //监听工具条
    table.on('tool(deliverListFilter)', function (obj) {
        var data = obj.data;
        if (data == null || data == "") {
            layer.msg("请选择需要操作的用户！");
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
        } else if (obj.event === 'edit') {
            var content;
            if (data.deliverType === "2") {
                content = "editOutBusiness.html";
            } else {
                content = "editBusiness.html";
            }
            var index = layui.layer.open({
                title: "发货单商务处理",
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
        } else if (obj.event === 'extend') {
            if (data.contractTypeCode == "CT_TS") {
                var index = layui.layer.open({
                    title: "合同延保处理",
                    type: 2,
                    content: "extendBusiness.html",
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
            } else {
                top.layer.msg("只有技术服务合同能够延保！");
            }
        }
    });
    //添加查询
    $(".search_btn").click(function () {
        var bsUser = $("#searchBsUser").val();
        var ctCode = $("#searchCtCode").val();
        var ctType = $("#searchCtType").val();
        var customerName = $("#searchCustomerName").val();
        var deliverRemark = $("#searchDeliverRemark").val();
        var deliverStatus = $("#searchDeliverStatus").val();
        var finishFlag = $("#searchFinishFlag").val();
        searchDeliver(bsUser, ctCode, ctType, customerName, deliverRemark, deliverStatus, finishFlag);
    });
    //添加导出
    $(".exportExcel_btn").click(function () {
        var bsUser = $("#searchBsUser").val();
        var ctCode = $("#searchCtCode").val();
        var ctType = $("#searchCtType").val();
        var customerName = $("#searchCustomerName").val();
        var deliverRemark = $("#searchDeliverRemark").val();
        var deliverStatus = $("#searchDeliverStatus").val();
        var finishFlag = $("#searchFinishFlag").val();
        var params = {
            "businessUserId": bsUser,
            "contractCode": ctCode,
            "contractTypeCode": ctType,
            "customerName": customerName,
            "deliverRemark": deliverRemark,
            "deliverStatusCode": deliverStatus,
            "finishFlag": finishFlag,
            "type": "business"
        };
        outPutBusinessData(params);
    });

    /**
     * 导出商务记录
     */
    function outPutBusinessData(params) {
        var form = $("<form>");//定义一个form表单
        form.attr("style", "display:none");
        form.attr("target", "");
        form.attr("method", "post");
        form.attr("action", "../../ReportController/downloadBusinessListExcel");
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