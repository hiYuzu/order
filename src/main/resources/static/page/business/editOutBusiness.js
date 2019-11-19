var row;
var typeCodeCt = 'CT';
var userTypeSw = '2';
layui.config({
    base: "js/"
}).use(['form', 'layer', 'jquery', 'table', 'laydate'], function () {
        var form = layui.form,
            layer = parent.layer === undefined ? layui.layer : parent.layer,
            table = layui.table,
            $ = layui.jquery,
            laydate = layui.laydate;

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
        }

        var tableData = new Array(); // 用于存放表格数据
        //初始化表格
        initGoodsTable(tableData);

        function initGoodsTable(tableData) {
            table.render({
                elem: '#outsourcingListTable'
                , data: tableData
                , page: false
                , cols: [[
                    {field: 'goodsId', title: '货物ID', minWidth: '100', align: 'center'}
                    , {field: 'deliverId', title: '发货单ID', minWidth: '100', align: 'center'}
                    , {field: 'pnCode', title: '商品类型编码', minWidth: '100', align: 'center'}
                    , {field: 'pnName', title: '商品类型', minWidth: '100', align: 'center'}
                    , {field: 'snCode', title: '商品编码', minWidth: '150', align: 'center'}
                    , {field: 'goodsName', title: '商品名称', minWidth: '150', align: 'center'}
                    , {field: 'purchaseContract', title: '采购合同', minWidth: '150', align: 'center'}
                    , {field: 'storageDate', title: '入库日期', minWidth: '150', align: 'center'}
                    , {field: 'goodsSupplier', title: '供应商名称', minWidth: '150', align: 'center'}
                    , {field: 'deliverPoint', title: '发货起点', minWidth: '150', align: 'center'}
                    , {field: 'warrantyClause', title: '质保条款', minWidth: '200', align: 'center'}
                    , {field: 'goodsMemo', title: '外购商品备注', minWidth: '180', align: 'center'}
                ]]
                , limit: 100
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
                $("#businessUser").val(row.businessUserId);
                $("#deliverRemark").val(row.deliverRemark);
                $("#contractWarranty").val(row.contractWarranty);
                //外购设备
                $.ajax({
                    url: "../../DeliverGoodsController/getGoodsDeliverById",
                    type: "post",
                    dataType: "json",
                    async: false,
                    data: {deliverId: row.deliverId},
                    error: function (json) {
                        top.layer.msg("初始化设备列表失败！");
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
                                    "goodsName": currentRow.goodsName,
                                    "purchaseContract": currentRow.purchaseContract,
                                    "storageDate": currentRow.storageDate,
                                    "goodsSupplier": currentRow.goodsSupplier,
                                    "deliverPoint": currentRow.deliverPoint,
                                    "warrantyClause": currentRow.warrantyClause,
                                    "goodsMemo": currentRow.goodsMemo
                                };
                                tableData.push(addData);
                            }
                            table.reload('outsourcingListTable', {
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

        //自定义验证规则
        form.verify({
            contractCode: function (value, item) {
                if ($("input[name='optType']:checked").val() == "1") {
                    if (value == "" || value == null) {
                        return "请输入合同编码";
                    }
                }
            },
            contractType: function (value, item) {
                if ($("input[name='optType']:checked").val() == "1") {
                    if (value == "" || value == null) {
                        return "请选择合同类型";
                    }
                }
            },
            contractWarranty: function (value, item) {
                if ($("input[name='optType']:checked").val() == "1") {
                    if (value == "" || value == null) {
                        return "请输入合同质保期";
                    }
                }
            }
        });

        form.on("submit(businessOutsourcing)", function (data) {
            var dataPara = data.field;
            dataPara.goodsModelString = JSON.stringify(formatTableCacheData(table.cache["outsourcingListTable"]));
            //弹出loading
            var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
            $.ajax({
                url: "../../BusinessController/businessDeliver",
                type: "post",
                dataType: "json",
                data: dataPara,
                error: function (json) {
                    top.layer.close(index);
                    top.layer.msg("外购单商务处理失败！");
                },
                success: function (json) {
                    top.layer.close(index);
                    if (json.result) {
                        top.layer.msg("外购单商务处理成功！");
                        layer.closeAll("iframe");
                        //刷新父页面
                        parent.location.reload();
                    } else {
                        top.layer.msg("外购单商务处理失败,失败信息为:" + json.detail);
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