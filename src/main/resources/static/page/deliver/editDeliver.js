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

        //初始化时间
        laydate.render({
            elem: '#deliverDate'
            , min: formatTime(new Date())
        });
        laydate.render({
            elem: '#warrantyDate'
            , min: formatTime(new Date())
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
                    {type: 'numbers', title: '序号', width: '80'}
                    , {field: 'assembleId', title: '组装ID', minWidth: '100', align: 'center'}
                    , {field: 'pnCode', title: 'P/N编码', minWidth: '100', align: 'center'}
                    , {field: 'pnName', title: 'P/N', minWidth: '100', align: 'center'}
                    , {field: 'snCode', title: 'S/N', minWidth: '150', align: 'center', edit: 'text'}
                    , {field: 'installEnterprise', title: '安装企业', minWidth: '200', align: 'center', edit: 'text'}
                    , {field: 'jobNo', title: '生产工作单', minWidth: '160', align: 'center', edit: 'text'}
                    // , {field: 'analyzerNumber', title: '分析仪序列号', minWidth: '160', align: 'center', edit: 'text'}
                    , {field: 'goodsMemo', title: '发货设备备注', minWidth: '180', align: 'center', edit: 'text'}
                    , {title: '操作', fixed: 'right', minWidth: '120', align: 'center', toolbar: '#barDeliverList'}
                ]]
                , limit: 100
                , done: function (res, curr, count) {
                    $("[data-field='assembleId']").css('display', 'none');
                    $("[data-field='pnCode']").css('display', 'none');
                }
            });
        }

        initFormData();

        //重置页面内同容
        $("#reset").click(function () {
            initFormData();
        });

        function initFormData() {
            if (row != null && row != '') {
                //基本信息
                $("#deliverId").val(row.deliverId);
                $("#contractCode").val(row.contractCode);
                $("#userCode").val(row.userCode);
                $("#userName").val(row.userName);
                $("#contractType").val(row.contractTypeCode);
                $("#customerName").val(row.customerName);
                $("#deliverDate").val(row.deliverDate);
                $("#warrantyDate").val(row.warrantyDate);
                $("#deliverAddress").val(row.deliverAddress);
                $("#businessUser").val(row.businessUserId);
                $("#deliverRemark").val(row.deliverRemark);
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
                                    "assembleId": currentRow.assembleId == "null" ? null:currentRow.assembleId,
                                    "pnCode": currentRow.pnCode,
                                    "pnName": currentRow.pnName,
                                    "snCode": currentRow.snCode,
                                    "installEnterprise": currentRow.installEnterprise,
                                    "jobNo": currentRow.jobNo,
                                    // "analyzerNumber": currentRow.analyzerNumber,
                                    "goodsMemo": currentRow.goodsMemo
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


        //监听工具条
        table.on('tool(goodsListFiler)', function (obj) {
            var data = obj.data;
            if (data == null || data == "") {
                layer.msg("请选择需要操作的数据！");
                return;
            }
            if (obj.event === 'del') {
                layer.confirm('删除后将不能再选择此设备，确认删除\'' + data.snCode + '\'吗', function (index) {
                    obj.del();
                    table.reload('goodsListTable', {
                        data: formatTableCacheData(table.cache["goodsListTable"])
                    });
                    layer.close(index);
                });
            } else if (obj.event === 'select') {
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

        //点击加号按钮时，新添一行  
        // $("#addRow").click(function () {
        //     var oldData = table.cache["goodsListTable"];
        //     var pnCode = $('#pnCode').val();
        //     var pnName = $("#pnCode").find("option:selected").text();
        //     var addData = {
        //         "pnCode": pnCode,
        //         "pnName": pnName,
        //         "snCode": "",
        //         "installEnterprise": "",
        //         "jobNo": "",
        //         "analyzerNumber": "",
        //         "goodsMemo": ""
        //     };
        //     if (oldData == null) {
        //         oldData = new Array();
        //     }
        //     oldData.push(addData);
        //     table.reload('goodsListTable', {
        //         data: table.cache["goodsListTable"] == null ? formatTableCacheData(oldData) : oldData
        //     });
        //     return false;
        // });

        form.on("submit(updateDeliver)", function (data) {
            var dataPara = data.field;
            dataPara.goodsModelString = JSON.stringify(formatTableCacheData(table.cache["goodsListTable"]));
            //弹出loading
            var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
            $.ajax({
                url: "../../DeliverController/updateDeliver",
                type: "post",
                dataType: "json",
                data: dataPara,
                error: function (json) {
                    top.layer.close(index);
                    top.layer.msg("发货单更新失败！");
                },
                success: function (json) {
                    top.layer.close(index);
                    if (json.result) {
                        top.layer.msg("发货单更新成功！");
                        layer.closeAll("iframe");
                        //刷新父页面
                        parent.location.reload();
                    } else {
                        top.layer.msg("发货单更新失败,失败信息为:" + json.detail);
                    }
                }
            });
            return false;
        });

        //点击加号按钮时，新添一行  
        $("#addRow").click(
            function () {
                selectAssemble();
                return false;
            }
        );

        function selectAssemble() {
            var index = layui.layer.open({
                title: "选择设备"
                , type: 2
                , shadeClose: true
                , area: ['90%', '80%']
                , shade: 0.8
                , content: "assembleSelect.html"
                , end: function () {
                    if (selectRows != null) {
                        setGoodsListData(selectRows);
                        form.render();
                    } else {
                        layer.msg("请选择发货设备");
                    }
                }
            });
        }

        function setGoodsListData(dataRows) {
            var oldData = table.cache["goodsListTable"];
            layui.each(dataRows, function (index, item) {
                var assembleId = item.assembleId;
                var exist = false;
                layui.each(oldData, function (goodsIndex, goodsItem) {
                    var goodsAssembleId = goodsItem.assembleId;
                    if (goodsAssembleId === assembleId) {
                        exist = true;
                    }
                });
                if (!exist) {
                    var pnCode = item.pnCode;
                    var pnName = item.pnName;
                    var snCode = item.snCode;
                    var jobNo = item.jobNo;
                    var addData = {
                        "assembleId": assembleId,
                        "pnCode": pnCode,
                        "pnName": pnName,
                        "snCode": snCode,
                        "jobNo": jobNo,
                        "installEnterprise": "",
                        // "analyzerNumber": "",
                        "goodsMemo": ""
                    };
                    if (oldData == null) {
                        oldData = new Array();
                    }
                    oldData.push(addData);
                }
            });
            table.reload('goodsListTable', {
                data: table.cache["goodsListTable"] == null ? formatTableCacheData(oldData) : oldData
            });
            return false;
        }
    }
);

function showFormData(data) {
    row = data;
}

function showGoodsData(data) {
    selectRows = data;
}