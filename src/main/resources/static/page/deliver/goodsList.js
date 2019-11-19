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
            id: 'goodsId,deliverId,assembleId'
            , elem: '#goodsListTable'
            , url: '../../DeliverGoodsController/getGoodsDeliverById'
            , method: 'post'
            , where: {deliverId: deliverId} //传参*/
            , page: false
            , cols: [[
                {field: 'pnCode', title: 'P/N编码', minWidth: '100', align: 'center'}
                , {field: 'pnName', title: 'P/N', minWidth: '100', align: 'center'}
                , {field: 'snCode', title: 'S/N', minWidth: '150', align: 'center'}
                , {field: 'installEnterprise', title: '安装企业', minWidth: '180', align: 'center'}
                , {field: 'jobNo', title: '生产工作单', minWidth: '160', align: 'center'}
                // , {field: 'analyzerNumber', title: '分析仪序列号', minWidth: '160', align: 'center'}
                , {field: 'goodsMemo', title: '发货设备备注', minWidth: '180', align: 'center', edit: 'text'}
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
                        top.layer.msg("更新发货设备备注失败！");
                    },
                    success: function (json) {
                        top.layer.close(index);
                        if (json.result) {
                            top.layer.msg("更新发货设备备注成功！");
                            initPage();
                        } else {
                            top.layer.msg(json.detail);
                        }
                    }
                });
                layer.closeAll('dialog');
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

});

function showFormData(data) {
    deliverId = data;
}