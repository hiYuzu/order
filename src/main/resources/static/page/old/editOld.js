var row;
var typeCodePn = 'PN';
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
            elem: '#completeDate'
            // ,min: formatTime(new Date())
        });
        laydate.render({
            elem: '#beginTime'
        });
        laydate.render({
            elem: '#endTime'
        });

        //初始化下拉框
        initSelect();

        function initSelect() {
            //P/N
            $.ajax({
                url: "../../ParamController/getParamByType",
                type: "post",
                dataType: "json",
                async: false,
                data: {typeCode: typeCodePn},
                error: function () {
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
        initPartsTable(tableData);

        function initPartsTable(tableData) {
            table.render({
                elem: '#partsListTable'
                , data: tableData
                , page: false
                , cols: [[
                    {type: 'numbers', title: '序号', width: '80'}
                    , {field: 'partTypeCode', title: '部件类型编码', minWidth: '100', align: 'center'}
                    , {field: 'partTypeName', title: '部件类型', minWidth: '160', align: 'center'}
                    , {field: 'partNo', title: '部件序号', minWidth: '200', align: 'center'}
                    , {field: 'partMemo', title: '部件备注', minWidth: '213', align: 'center'}
                ]]
                , limit: 100
                , done: function (res, curr, count) {
                    $("[data-field='partTypeCode']").css('display', 'none');
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
                $("#assembleId").val(row.assembleId);
                if(row.oldId != null && !isNaN(row.oldId)){
                    $("#oldId").val(row.oldId);
                }
                $("#pnCode").val(row.pnCode);
                $("#snCode").val(row.snCode);
                $("#jobNo").val(row.jobNo);
                $("#cruxNo").val(row.cruxNo);
                $("#completeDate").val(row.completeDate);
                $("#assembleMemo").val(row.assembleMemo);
                if (row.beginTime != null) {
                    $("#beginTime").val(row.beginTime);
                } else {
                    $("#beginTime").val(row.testEndTime);
                }
                $("#endTime").val(row.endTime);
                $("#oldMemo").val(row.oldMemo);
                //部件列表
                $.ajax({
                    url: "../../AssemblePartController/getPartsByAssembleId",
                    type: "post",
                    dataType: "json",
                    async: false,
                    data: {assembleId: row.assembleId},
                    error: function () {
                        top.layer.msg("初始化部件列表失败！");
                    },
                    success: function (json) {
                        if (json != null && json.data != null) {
                            for (var i = 0; i < json.data.length; i++) {
                                var currentRow = json.data[i];
                                var addData = {
                                    "partTypeCode": currentRow.partTypeCode,
                                    "partTypeName": currentRow.partTypeName,
                                    "partNo": currentRow.partNo,
                                    "partMemo": currentRow.partMemo
                                };
                                tableData.push(addData);
                            }
                            table.reload('partsListTable', {
                                data: tableData
                            });
                        } else {
                            top.layer.msg("获取部件列表信息失败！");
                        }
                    }
                });
                form.render();
            }
        }

        form.on("submit(updateOld)", function (data) {
            var dataPara = data.field;
            //弹出loading
            var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
            $.ajax({
                url: "../../AssembleOldController/operateAssembleOld",
                type: "post",
                dataType: "json",
                data: dataPara,
                error: function () {
                    top.layer.close(index);
                    top.layer.msg("组装老化操作失败！");
                },
                success: function (json) {
                    top.layer.close(index);
                    if (json.result) {
                        top.layer.msg("组装老化操作成功！");
                        layer.closeAll("iframe");
                        //刷新父页面
                        parent.location.reload();
                    } else {
                        top.layer.msg("组装老化操作失败,失败信息为:" + json.detail);
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