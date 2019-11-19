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
                    form.render('select');
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
    //监听单元格编辑
    form.on('tool(partsListTable)', function (obj) {
        var value = obj.value //得到修改后的值
            , data = obj.data //得到所在行所有键值
            , field = obj.field; //得到字段
        layer.msg('[ID: ' + data.id + '] ' + field + ' 字段更改为：' + value);
    });

    form.on("submit(addAssemble)", function (data) {
        var dataPara = data.field;
        dataPara.partsModelString = JSON.stringify(formatTableCacheData(table.cache["partsListTable"]));
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
        $.ajax({
            url: "../../AssembleController/insertAssemble",
            type: "post",
            dataType: "json",
            data: dataPara,
            error: function () {
                top.layer.close(index);
                top.layer.msg("组装单添加失败！");
            },
            success: function (json) {
                top.layer.close(index);
                if (json.result) {
                    top.layer.msg("组装单添加成功！");
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                } else {
                    top.layer.msg("组装单添加失败！");
                }
            }
        });
        return false;
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

});