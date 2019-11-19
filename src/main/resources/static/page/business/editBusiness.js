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
            // , min: formatTime(new Date())
        });
        laydate.render({
            elem: '#warrantyDate'
            // , min: formatTime(new Date())
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
                    , {field: 'assembleId', title: '组装单ID', minWidth: '100', align: 'center'}
                    , {field: 'pnCode', title: 'P/N编码', minWidth: '100', align: 'center'}
                    , {field: 'pnName', title: 'P/N', minWidth: '100', align: 'center'}
                    , {field: 'snCode', title: 'S/N', minWidth: '150', align: 'center'}
                    , {field: 'installEnterprise', title: '安装企业', minWidth: '200', align: 'center'}
                    , {field: 'jobNo', title: '生产工作单', minWidth: '160', align: 'center'}
                    // , {field: 'analyzerNumber', title: '分析仪序列号', minWidth: '160', align: 'center'}
                    , {field: 'goodsMemo', title: '发货设备备注', minWidth: '180', align: 'center'}
                    , {field: 'goodsAmount', title: '设备金额', minWidth: '120', align: 'center', edit: 'text'}
                    , {title: '操作', fixed: 'right', minWidth: '80', align: 'center', toolbar: '#barBusinessList'}
                ]]
                , limit: 100
                , done: function (res, curr, count) {
                    $("[data-field='goodsId']").css('display', 'none');
                    $("[data-field='deliverId']").css('display', 'none');
                    $("[data-field='assembleId']").css('display', 'none');
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
                $("#contractWarranty").val(row.contractWarranty);
                if (row.contractNewCode == null || row.contractNewCode == "") {
                    $("#contractNewCode").val(row.contractCode);
                } else {
                    $("#contractNewCode").val(row.contractNewCode);
                }
                $("#contractBeginTime").val(row.contractBeginTime);
                $("#contractEndTime").val(row.contractEndTime);
                $("#contractAmount").val(row.contractAmount);
                if (row.contractTypeCode == "CT_TS") {
                    $("#itemCw").hide();
                    $("#itemCnc").hide();
                    $("#itemCbt").show();
                    $("#itemCet").show();
                } else {
                    $("#itemCw").show();
                    $("#itemCnc").hide();
                    $("#itemCbt").hide();
                    $("#itemCet").hide();
                }
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
                                    "assembleId": currentRow.assembleId,
                                    "pnCode": currentRow.pnCode,
                                    "pnName": currentRow.pnName,
                                    "snCode": currentRow.snCode,
                                    "installEnterprise": currentRow.installEnterprise,
                                    "jobNo": currentRow.jobNo,
                                    // "analyzerNumber": currentRow.analyzerNumber,
                                    "goodsMemo": currentRow.goodsMemo,
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
        }

        //监听工具条
        table.on('tool(goodsListFiler)', function (obj) {
            var data = obj.data;
            if (data == null || data == "") {
                layer.msg("请选择需要操作的数据！");
                return;
            }
            if (obj.event === 'select') {
                layui.layer.open({
                    title: "部件列表"
                    , type: 2
                    , shadeClose: true
                    , area: ['90%', '80%']
                    , shade: 0.8
                    , content: "../assemble/partsList.html"
                    , success: function (layero, index) {
                        var frameWin = window[layero.find('iframe')[0]['name']];
                        frameWin.showFormData(data.assembleId);
                    }
                });
            }
        });

        //自定义验证规则
        form.verify({
            deliverNo: function (value, item) {
                if ($("input[name='optType']:checked").val() == "1") {
                    if (value == "" || value == null) {
                        return "请输入运单号";
                    }
                }
            },
            contractWarranty: function (value, item) {
                if ($("input[name='optType']:checked").val() == "1" && $("#contractType").val() == "CT_SL") {
                    if (value == "" || value == null) {
                        return "请输入合同质保期";
                    }
                }
            },
            contractNewCode: function (value, item) {
                if ($("input[name='optType']:checked").val() == "1" && $("#contractType").val() == "CT_TS") {
                    if (value == "" || value == null) {
                        return "请输入合同编码";
                    }
                }
            },
            contractBeginTime: function (value, item) {
                if ($("input[name='optType']:checked").val() == "1" && $("#contractType").val() == "CT_TS") {
                    if (value == "" || value == null) {
                        return "请输入合同开始时间";
                    }
                }
            },
            contractEndTime: function (value, item) {
                if ($("input[name='optType']:checked").val() == "1" && $("#contractType").val() == "CT_TS") {
                    if (value == "" || value == null) {
                        return "请输入合同结束时间";
                    }
                }
            },
            contractAmount: function (value, item) {
                if ($("input[name='optType']:checked").val() == "1") {
                    if (value == "" || value == null) {
                        return "请输入合同金额";
                    }
                }
            }
        });

        form.on("submit(businessDeliver)", function (data) {
            var dataPara = data.field;
            dataPara.goodsModelString = JSON.stringify(formatTableCacheData(table.cache["goodsListTable"]));
            //弹出loading
            var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
            $.ajax({
                url: "../../BusinessController/businessDeliver",
                type: "post",
                dataType: "json",
                data: dataPara,
                error: function (json) {
                    top.layer.close(index);
                    top.layer.msg("发货单商务处理失败！");
                },
                success: function (json) {
                    top.layer.close(index);
                    if (json.result) {
                        top.layer.msg("发货单商务处理成功！");
                        layer.closeAll("iframe");
                        //刷新父页面
                        parent.location.reload();
                    } else {
                        top.layer.msg("发货单商务处理失败,失败信息为:" + json.detail);
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