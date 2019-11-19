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

        //初始化下拉框
        initSelect();

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

        initFromData();

        function initFromData() {
            if (selectRow != null) {
                $("#repairId").val(selectRow.repairId);
                $("#installId").val(selectRow.installId);
                $("#installEnterprise").val(selectRow.installEnterprise);
                $("#warrantyPeriod").val(selectRow.warrantyPeriod);
                $("#pn").val(selectRow.pnCode);
                $("#snCode").val(selectRow.snCode);
                $("#repairUser").val(selectRow.repairUser);
                $("#repairTime").val(selectRow.repairTime);
                $("#materielName").val(selectRow.materielName);
                $("#materielNumber").val(selectRow.materielNumber);
                form.render();
            }
        };

        //重置页面内同容
        $("#reset").click(function () {
            initFromData();
        });

        form.on("submit(editRepair)", function (data) {
            var dataPara = data.field;
            //弹出loading
            var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
            $.ajax({
                url: "../../RepairController/updateGoodsRepair",
                type: "post",
                dataType: "json",
                data: dataPara,
                error: function (json) {
                    top.layer.close(index);
                    top.layer.msg("更新维修记录失败！");
                },
                success: function (json) {
                    top.layer.close(index);
                    if (json.result) {
                        top.layer.msg("更新维修记录成功！");
                        layer.closeAll("iframe");
                        //刷新父页面
                        parent.location.reload();
                    } else {
                        top.layer.msg("更新维修记录失败,失败信息为:" + json.detail);
                    }
                }
            });
            return false;
        });
    }
);

function showFormData(data) {
    selectRow = data;
}

