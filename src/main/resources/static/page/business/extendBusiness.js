var row;
var typeCodeCt = 'CT';
var typeCodePn = 'PN';
var userTypeSw = '2';
layui.config({
    base: "js/"
}).use(['form', 'layer', 'jquery', 'table', 'laydate'], function () {
        var form = layui.form,
            layer = parent.layer === undefined ? layui.layer : parent.layer,
            table = layui.table,
            $ = layui.jquery,
            laydate = layui.laydate;

        //初始化时间
        laydate.render({
            elem: '#deliverDate'
        });
        laydate.render({
            elem: '#warrantyDate'
        });
        laydate.render({
            elem: '#contractBeginTime'
        });
        laydate.render({
            elem: '#contractEndTime'
        });

        //初始化下拉框
        initSelect();

        function initSelect() {
            //合同类型
            $.ajax({
                url: "../../ParamController/getParamByType",
                type: "post",
                dataType: "json",
                data: {typeCode: typeCodeCt},
                async: false,
                error: function (json) {
                    top.layer.msg("初始化合同类型失败！");
                },
                success: function (json) {
                    if (json != null && json.data != null) {
                        var optionStr = "";
                        $.each(json.data, function (index, value) {
                            optionStr += "<option value='" + value.paramCode + "'>"
                                + value.paramName + "</option>";
                        });
                        $("#contractType").append(optionStr);
                        form.render('select', 'contractTypeFilter');
                    } else {
                        top.layer.msg("初始化合同类型失败！");
                    }
                }
            });
            //商务处理
            $.ajax({
                url: "../../UserController/getUser",
                type: "post",
                dataType: "json",
                data: {userType: userTypeSw},
                async: false,
                error: function (json) {
                    top.layer.msg("初始化商务处理失败！");
                },
                success: function (json) {
                    if (json != null && json.data != null) {
                        var optionStr = "";
                        $.each(json.data, function (index, value) {
                            optionStr += "<option value='" + value.userId + "'>"
                                + value.userName + "</option>";
                        });
                        $("#businessUser").append(optionStr);
                        form.render('select', 'businessUserFilter');
                    } else {
                        top.layer.msg("初始化商务处理失败！");
                    }
                }
            });
            //P/N
            $.ajax({
                url: "../../ParamController/getParamByType",
                type: "post",
                dataType: "json",
                async: false,
                data: {typeCode: typeCodePn},
                error: function (json) {
                    top.layer.msg("初始化P/N失败！");
                },
                success: function (json) {
                    if (json != null && json.data != null) {
                        var optionStr = "";
                        $.each(json.data, function (index, value) {
                            optionStr += "<option value='" + value.paramCode + "'>"
                                + value.paramName + "</option>";
                        });
                        $("#pnCode").append(optionStr);
                        form.render('select', 'pnCodeFilters');
                    } else {
                        top.layer.msg("初始化P/N失败！");
                    }
                }
            });
        }

        var tableData = new Array(); // 用于存放表格数据
        //初始化表格
        initGoodsTable(tableData);

        function initGoodsTable(tableData) {
            table.render({
                elem: '#goodsListTable'
                , data: tableData
                , page: false
                , cols: [[
                    {field: 'goodsId', title: '货物ID', minWidth: '100', align: 'center'}
                    , {field: 'deliverId', title: '发货单ID', minWidth: '100', align: 'center'}
                    , {field: 'pnCode', title: 'P/N编码', minWidth: '100', align: 'center'}
                    , {field: 'pnName', title: 'P/N', minWidth: '100', align: 'center'}
                    , {field: 'snCode', title: 'S/N', minWidth: '150', align: 'center'}
                    , {field: 'installEnterprise', title: '安装企业', minWidth: '200', align: 'center'}
                    , {field: 'jobNo', title: '生产工作单', minWidth: '160', align: 'center'}
                    , {field: 'analyzerNumber', title: '分析仪序列号', minWidth: '160', align: 'center'}
                    , {field: 'goodsAmount', title: '设备金额', minWidth: '120', align: 'center'}
                ]]
                , done: function (res, curr, count) {
                    $("[data-field='goodsId']").css('display', 'none');
                    $("[data-field='deliverId']").css('display', 'none');
                    $("[data-field='pnCode']").css('display', 'none');
                }
            });
        }

        initFormData();

        function initFormData() {
            if (row != null && row != '') {
                //基本信息
                $("#deliverId").val(row.deliverId);
                $("#contractCode").val(row.contractCode);
                $("#contractType").val(row.contractTypeCode);
                $("#customerName").val(row.customerName);
                $("#deliverDate").val(row.deliverDate);
                $("#warrantyDate").val(row.warrantyDate);
                $("#deliverAddress").val(row.deliverAddress);
                $("#businessUser").val(row.businessUserId);
                $("#deliverRemark").val(row.deliverRemark);
                $("#deliverNo").val(row.deliverNo);
                $("#deliverNo").val(row.deliverNo);
                $("#contractAmount").val(row.contractAmount);
                $("#deliverRemark").val(row.deliverRemark);
                $("#contractNewCode").val(row.contractNewCode);
                $("#contractBeginTime").val(row.contractBeginTime);
                $("#contractEndTime").val(row.contractEndTime);
                $("#contractAmount").val(row.contractAmount);
                //安装设备
                $.ajax({
                    url: "../../DeliverGoodsController/getGoodsDeliverById",
                    type: "post",
                    dataType: "json",
                    async: false,
                    data: {deliverId: row.deliverId},
                    error: function (json) {
                        top.layer.msg("初始化P/N失败！");
                    },
                    success: function (json) {
                        if (json != null && json.data != null) {
                            for (var i = 0; i < json.data.length; i++) {
                                var currentRow = json.data[i];
                                var addData = {
                                    "goodsId": currentRow.goodsId,
                                    "deliverId": currentRow.deliverId,
                                    "pnCode": currentRow.pnCode,
                                    "pnName": currentRow.pnName,
                                    "snCode": currentRow.snCode,
                                    "installEnterprise": currentRow.installEnterprise,
                                    "jobNo": currentRow.jobNo,
                                    "analyzerNumber": currentRow.analyzerNumber,
                                    "goodsAmount": currentRow.goodsAmount
                                };
                                tableData.push(addData);
                            }
                            table.reload('goodsListTable', {
                                data: tableData
                            });
                        } else {
                            top.layer.msg("获取设备信息失败！");
                        }
                    }
                });
                form.render();
            }
        };

        //自定义验证规则
        form.verify({
            contractNewCode: function (value, item) {
                if (value == "" || value == null) {
                    return "请输入合同编码";
                }
            },
            contractBeginTime: function (value, item) {
                if (value == "" || value == null) {
                    return "请输入合同开始时间";
                }
            },
            contractEndTime: function (value, item) {
                if (value == "" || value == null) {
                    return "请输入合同结束时间";
                }
            }
        });

        form.on("submit(extendBusiness)", function (data) {
            var dataPara = data.field;
            //弹出loading
            var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
            $.ajax({
                url: "../../BusinessController/extendBusiness",
                type: "post",
                dataType: "json",
                data: dataPara,
                error: function (json) {
                    top.layer.close(index);
                    top.layer.msg("合同延保处理失败！");
                },
                success: function (json) {
                    top.layer.close(index);
                    if (json.result) {
                        top.layer.msg("合同延保处理成功！");
                        layer.closeAll("iframe");
                        //刷新父页面
                        parent.location.reload();
                    } else {
                        top.layer.msg("合同延保处理失败,失败信息为:" + json.detail);
                    }
                }
            });
            return false;
        });

    }
);

function showFormData(data) {
    row = data;
}