var row;
var typeCodeCt = 'CT';
var typeCodeOS = 'OS';
layui.config({
    base: "js/"
}).use(['form', 'layer', 'jquery', 'laydate'], function () {
        var form = layui.form,
            layer = parent.layer === undefined ? layui.layer : parent.layer,
            $ = layui.jquery,
            laydate = layui.laydate;

        //初始化时间
        laydate.render({
            elem: '#beginTime'
            , max: formatTime(new Date())
        });
        laydate.render({
            elem: '#endTime'
            , max: formatTime(new Date())
        });
        laydate.render({
            elem: '#warrantyPeriod'
            // , min: formatTime(new Date())
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
            //P/N
            $.ajax({
                url: "../../ParamController/getParamByType",
                type: "post",
                dataType: "json",
                async: false,
                data: {typeCode: typeCodeOS},
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
                        form.render('select', 'pnCodeFilters');
                    } else {
                        top.layer.msg("初始化P/N失败！");
                    }
                }
            });
        }

        initFormData();

        function initFormData() {
            if (row != null && row != '') {
                //基本信息
                $("#installId").val(row.installId);
                $("#deliverId").val(row.deliverId);
                $("#goodsId").val(row.goodsId);
                $("#contractCode").val(row.contractCode);
                $("#contractType").val(row.contractTypeCode);
                $("#installEnterprise").val(row.installEnterprise);
                $("#warrantyClause").val(row.warrantyClause);
                $("#pn").val(row.pnCode);
                $("#snCode").val(row.snCode);
                $("#installUser").val(row.installUser);
                $("#beginTime").val(row.beginTime);
                $("#endTime").val(row.endTime);
                $("#instrumentBrand").val(row.instrumentBrand);
                $("#installMemo").val(row.installMemo);
                $("#warrantyPeriod").val(row.warrantyPeriod);
                var installStatusCode = row.installStatusCode;
                if (installStatusCode) {
                    installStatusCode = "1";
                } else {
                    installStatusCode = "0";
                }
                $("input[name='installStatusCode'][value=\'" + installStatusCode + "\']").prop("checked", true);
                form.render();
            }
        };

        //自定义验证规则
        form.verify({
            installEnterprise: function (value, item) {
                if ($("input[name='installStatusCode']:checked").val() == "1") {
                    if (value == "" || value == null) {
                        return "请输入安装企业";
                    }
                }
            },
            installUser: function (value, item) {
                if ($("input[name='installStatusCode']:checked").val() == "1") {
                    if (value == "" || value == null) {
                        return "请输入安装人员";
                    }
                }
            },
            beginTime: function (value, item) {
                if ($("input[name='installStatusCode']:checked").val() == "1") {
                    if (value == "" || value == null) {
                        return "请输入开始时间";
                    }
                }
            },
            endTime: function (value, item) {
                if ($("input[name='installStatusCode']:checked").val() == "1") {
                    if (value == "" || value == null) {
                        return "请输入结束时间";
                    }
                }
            },
            warrantyPeriod: function (value, item) {
                if ($("input[name='installStatusCode']:checked").val() == "1") {
                    if (value == "" || value == null) {
                        return "请输入质保期限";
                    }
                }
            }
        });

        form.on("submit(installGoods)", function (data) {
            var dataPara = data.field;
            //弹出loading
            var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
            $.ajax({
                url: "../../InstallController/operateGoodsInstall",
                type: "post",
                dataType: "json",
                data: dataPara,
                error: function (json) {
                    top.layer.close(index);
                    top.layer.msg("安装处理失败！");
                },
                success: function (json) {
                    top.layer.close(index);
                    if (json.result) {
                        top.layer.msg("安装处理成功！");
                        layer.closeAll("iframe");
                        //刷新父页面
                        parent.location.reload();
                    } else {
                        top.layer.msg("安装处理失败,失败信息为:" + json.detail);
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