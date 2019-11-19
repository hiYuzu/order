var deliverId;
layui.config({
    base: "js/"
}).use(['form', 'layer', 'jquery', 'table'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        table = layui.table,
        $ = layui.jquery;

    initPage();

    function initPage() {
        table.render({
            id: 'goodsId,deliverId'
            , elem: '#goodsListTable'
            , url: '../../DeliverGoodsController/getGoodsDeliverById'
            , method: 'post'
            , where: {deliverId: deliverId} //传参*/
            , page: false
            , cols: [[
                {field: 'pnCode', title: '商品类型编码', minWidth: '100', align: 'center'}
                , {field: 'pnName', title: '商品类型', minWidth: '100', align: 'center'}
                , {field: 'snCode', title: '商品编码', minWidth: '150', align: 'center'}
                , {field: 'goodsName', title: '商品名称', minWidth: '150', align: 'center'}
                , {field: 'purchaseContract', title: '采购合同', minWidth: '150', align: 'center'}
                , {field: 'storageDate', title: '入库日期', minWidth: '150', align: 'center'}
                , {field: 'goodsSupplier', title: '供应商名称', minWidth: '150', align: 'center'}
                , {field: 'deliverPoint', title: '发货起点', minWidth: '150', align: 'center'}
                , {field: 'warrantyClause', title: '质保条款', minWidth: '200', align: 'center'}
                , {field: 'goodsMemo', title: '外购商品备注', minWidth: '180', align: 'center', edit: 'text'}
                , {title: '操作', fixed: 'right', minWidth: '130', align: 'center', toolbar: '#barGoodsList'}
            ]]
            , done: function (res, curr, count) {
                $("[data-field='pnCode']").css('display', 'none');
            }
        });
    }

    //监听工具条
    table.on('tool(goodsListFilter)', function (obj) {
        var data = obj.data;
        if (data == null || data == "") {
            layer.msg("请选择需要操作的设备！");
            return;
        }
        if (obj.event === 'save') {
            layer.confirm('确认更新设备S/N为\'' + data.snCode + '\'的备注吗', function (index) {
                //弹出loading
                var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
                $.ajax({
                    url: "../../DeliverGoodsController/updateDeliverGoodsMemo",
                    type: "post",
                    dataType: "json",
                    data: {"goodsId": data.goodsId, "goodsMemo": data.goodsMemo},
                    error: function (json) {
                        top.layer.close(index);
                        top.layer.msg("更新外购设备备注失败！");
                    },
                    success: function (json) {
                        top.layer.close(index);
                        if (json.result) {
                            top.layer.msg("更新外购设备备注成功！");
                            initPage();
                        } else {
                            top.layer.msg(json.detail);
                        }
                    }
                });
                layer.closeAll('dialog');
            });
        }
    });

});

function showFormData(data) {
    deliverId = data;
}