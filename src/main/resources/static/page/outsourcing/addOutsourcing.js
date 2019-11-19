var typeCodeOs = 'OS';
var userTypeSw = '2';
var selectRows;
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
        //商务处理
        $.ajax({
            url: "../../UserController/getUser",
            type: "post",
            dataType: "json",
            data: {userType: userTypeSw},
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
                    form.render('select');
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
            data: {typeCode: typeCodeOs},
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
                {type: 'numbers', title: '序号', width: '80'}
                , {field: 'pnCode', title: '商品类型编码', minWidth: '100', align: 'center'}
                , {field: 'pnName', title: '商品类型', minWidth: '100', align: 'center'}
                , {field: 'snCode', title: '商品编码', minWidth: '150', align: 'center', edit: 'text'}
                , {field: 'goodsName', title: '商品名称', minWidth: '150', align: 'center', edit: 'text'}
                , {field: 'purchaseContract', title: '采购合同', minWidth: '150', align: 'center', edit: 'text'}
                , {field: 'storageDate', title: '入库日期', minWidth: '150', align: 'center', edit: 'text'}
                , {field: 'goodsSupplier', title: '供应商名称', minWidth: '150', align: 'center', edit: 'text'}
                , {field: 'deliverPoint', title: '发货起点', minWidth: '150', align: 'center', edit: 'text'}
                , {field: 'warrantyClause', title: '质保条款', minWidth: '200', align: 'center', edit: 'text'}
                , {field: 'goodsMemo', title: '外购商品备注', minWidth: '180', align: 'center', edit: 'text'}
                , {title: '操作', fixed: 'right', minWidth: '80', align: 'center', toolbar: '#barOutsourcingList'}
            ]]
            , limit: 100
            , done: function (res, curr, count) {
                $("[data-field='pnCode']").css('display', 'none');
            }
        });
    }

    //监听工具条
    table.on('tool(outsourcingListFiler)', function (obj) {
        var data = obj.data;
        if (data == null || data == "") {
            layer.msg("请选择需要操作的数据！");
            return;
        }
        if (obj.event === 'del') {
            layer.confirm('确认删除\'' + data.snCode + '\'吗', function (index) {
                obj.del();
                table.reload('outsourcingListTable', {
                    data: formatTableCacheData(table.cache["outsourcingListTable"])
                });
                layer.close(index);
            });
        }
    });

    form.on("submit(addOutsourcing)", function (data) {
        var dataPara = data.field;
        dataPara.goodsModelString = JSON.stringify(formatTableCacheData(table.cache["outsourcingListTable"]));
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
        $.ajax({
            url: "../../DeliverController/insertDeliver",
            type: "post",
            dataType: "json",
            data: dataPara,
            error: function (json) {
                top.layer.close(index);
                top.layer.msg("外购单添加失败！");
            },
            success: function (json) {
                top.layer.close(index);
                if (json.result) {
                    top.layer.msg("外购单添加成功！");
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                } else {
                    top.layer.msg("外购单添加失败！");
                }
            }
        });
        return false;
    });

    //点击加号按钮时，新添一行  
    $("#addRow").click(function () {
        var oldData = table.cache["outsourcingListTable"];
        var pnCode = $('#pnCode').val();
        var pnName = $("#pnCode").find("option:selected").text();
        var addData = {
            "pnCode": pnCode,
            "pnName": pnName,
            "snCode": "",
            "goodsName": "",
            "purchaseContract": "",
            "storageDate": "",
            "goodsSupplier": "",
            "deliverPoint": "",
            "warrantyClause": "",
            "goodsMemo": ""
        };
        if (oldData == null) {
            oldData = new Array();
        }
        oldData.push(addData);
        table.reload('outsourcingListTable', {
            data: table.cache["outsourcingListTable"] == null ? formatTableCacheData(oldData) : oldData
        });
        return false;
    });

});

function showGoodsData(data) {
    selectRows = data;
}