var typeCodePnOrOs;
var selectRow;
layui.config({
    base: "js/"
}).use(['form', 'layer', 'jquery', 'laydate'], function () {
        var form = layui.form,
            layer = parent.layer === undefined ? layui.layer : parent.layer,
            $ = layui.jquery,
            laydate = layui.laydate;

        //初始化时间
        laydate.render({
            elem: '#repairTime'
            , max: formatTime(new Date())
        });

        function initSelect() {
            //判断
            if (selectRow != null && selectRow.deliverType === "2") {
                typeCodePnOrOs = "OS";
            } else {
                typeCodePnOrOs = "PN";
            }
            //P/N
            $.ajax({
                url: "../../ParamController/getParamByType",
                type: "post",
                dataType: "json",
                async: false,
                data: {typeCode: typeCodePnOrOs},
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
                        $("#pn").append(optionStr);
                        form.render('select');
                    } else {
                        top.layer.msg("初始化P/N失败！");
                    }
                }
            });
        }

        initPage();

        function initPage() {
            var index = layui.layer.open({
                title: "选择设备"
                , type: 2
                , shadeClose: true
                , area: ['90%', '80%']
                , shade: 0.8
                , content: "installSelect.html"
                , end: function () {
                    initSelect();
                    if (selectRow != null) {
                        $("#installId").val(selectRow.installId);
                        $("#installEnterprise").val(selectRow.installEnterprise);
                        $("#warrantyPeriod").val(selectRow.warrantyPeriod);
                        $("#pn").val(selectRow.pnCode);
                        $("#snCode").val(selectRow.snCode);
                        form.render();
                    } else {
                        layer.msg("请选择设备");
                    }
                }
            });
        };

        form.on("submit(addCloseRepair)", function (data) {
            var dataPara = data.field;
            //弹出loading
            var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
            $.ajax({
                url: "../../RepairController/insertGoodsRepair",
                type: "post",
                dataType: "json",
                data: dataPara,
                error: function (json) {
                    top.layer.close(index);
                    top.layer.msg("新增维修记录失败！");
                },
                success: function (json) {
                    top.layer.close(index);
                    if (json.result) {
                        top.layer.msg("新增维修记录成功！");
                        layer.closeAll("iframe");
                        //刷新父页面
                        parent.location.reload();
                    } else {
                        top.layer.msg("新增维修记录失败,失败信息为:" + json.detail);
                    }
                }
            });
            return false;
        });
        form.on("submit(addNewRepair)", function (data) {
            var dataPara = data.field;
            //弹出loading
            var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
            $.ajax({
                url: "../../RepairController/insertGoodsRepair",
                type: "post",
                dataType: "json",
                data: dataPara,
                error: function (json) {
                    top.layer.close(index);
                    top.layer.msg("新增维修记录失败！");
                },
                success: function (json) {
                    top.layer.close(index);
                    if (json.result) {
                        top.layer.msg("新增维修记录成功！");
                        //刷新父页面
                        $("#materielName").val("");
                        $("#materielNumber").val("");
                    } else {
                        top.layer.msg("新增维修记录失败,失败信息为:" + json.detail);
                    }
                }
            });
            return false;
        });
        $("#selectGoods").click(function () {
            initPage();
            return false;
        });
    }
);

function showFormData(data) {
    selectRow = data;
}

