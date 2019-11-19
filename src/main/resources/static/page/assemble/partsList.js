var assembleId;
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
            id: 'partsId,assembleId,partTypeCode'
            , elem: '#partsListTable'
            , url: '../../AssemblePartController/getPartsByAssembleId'
            , method: 'post'
            , where: {assembleId: assembleId} //传参*/
            , page: false
            , cols: [[
                {field: 'partTypeName', title: '部件类型', minWidth: '160', align: 'center', sort: true}
                , {field: 'partNo', title: '部件序号', minWidth: '200', align: 'center', sort: true}
                , {field: 'partMemo', title: '部件备注', minWidth: '200', align: 'center'}
            ]]
            , done: function (res, curr, count) {
                $("[data-field='partTypeCode']").css('display', 'none');
            }
        });
    }

});

function showFormData(data) {
    assembleId = data;
}