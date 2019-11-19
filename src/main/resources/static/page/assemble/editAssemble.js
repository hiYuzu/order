var row;
var typeCodePn = 'PN';
var typeCodePart = 'PT';
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
            //组装部件
            $.ajax({
                url: "../../ParamController/getParamByType",
                type: "post",
                dataType: "json",
                data: {typeCode: typeCodePart},
                error: function () {
                    top.layer.msg("初始化部件类型失败！");
                },
                success: function (json) {
                    if (json != null && json.data != null) {
                        var optionStr = "";
                        $.each(json.data, function (index, value) {
                            optionStr += "<option value='" + value.paramCode + "'>"
                                + value.paramName + "</option>";
                        });
                        $("#partType").append(optionStr);
                        form.render('select');
                    } else {
                        top.layer.msg("初始化部件类型失败！");
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
                    , {field: 'partNo', title: '部件序号', minWidth: '200', align: 'center', edit: 'text'}
                    , {field: 'partMemo', title: '部件备注', minWidth: '213', align: 'center', edit: 'text'}
                    , {title: '操作', fixed: 'right', minWidth: '80', align: 'center', toolbar: '#barAssembleList'}
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
                $("#pnCode").val(row.pnCode);
                $("#snCode").val(row.snCode);
                $("#jobNo").val(row.jobNo);
                $("#cruxNo").val(row.cruxNo);
                $("#completeDate").val(row.completeDate);
                $("#assembleMemo").val(row.assembleMemo);
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
        };


        //监听工具条
        table.on('tool(partsListFiler)', function (obj) {
            var data = obj.data;
            if (data == null || data == "") {
                layer.msg("请选择需要操作的数据！");
                return;
            }
            if (obj.event === 'del') {
                layer.confirm('确认删除序号\'' + data.partNo + '\'吗', function (index) {
                    obj.del();
                    table.reload('partsListTable', {
                        data: formatTableCacheData(table.cache["partsListTable"])
                    });
                    layer.close(index);
                });
            }
        });

        //点击加号按钮时，新添一行  
        $("#addRow").click(function () {
            var oldData = table.cache["partsListTable"];
            var partTypeCode = $('#partType').val();
            var partTypeName = $("#partType").find("option:selected").text();
            var addData = {
                "partTypeCode": partTypeCode,
                "partTypeName": partTypeName,
                "partNo": "",
                "partMemo": ""
            };
            if (oldData == null) {
                oldData = new Array();
            }
            oldData.push(addData);
            table.reload('partsListTable', {
                data: table.cache["partsListTable"] == null ? formatTableCacheData(oldData) : oldData
            });
            return false;
        });

        form.on("submit(updateAssemble)", function (data) {
            var dataPara = data.field;
            dataPara.partsModelString = JSON.stringify(formatTableCacheData(table.cache["partsListTable"]));
            //弹出loading
            var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
            $.ajax({
                url: "../../AssembleController/updateAssemble",
                type: "post",
                dataType: "json",
                data: dataPara,
                error: function () {
                    top.layer.close(index);
                    top.layer.msg("组装单更新失败！");
                },
                success: function (json) {
                    top.layer.close(index);
                    if (json.result) {
                        top.layer.msg("组装单更新成功！");
                        layer.closeAll("iframe");
                        //刷新父页面
                        parent.location.reload();
                    } else {
                        top.layer.msg("组装单更新失败,失败信息为:" + json.detail);
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