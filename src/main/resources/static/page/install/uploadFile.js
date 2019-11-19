var installId;
layui.config({
    base: "js/"
}).use(['form', 'layer', 'jquery', 'table'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        table = layui.table,
        $ = layui.jquery,
        upload = layui.upload;

    searchFile();
    function searchFile(){
        table.render({
            id: 'fileId,installId,fileRelativePath'
            , elem: '#fileListTable'
            , url: '../../FileOperateController/queryFile'
            , method: 'post'
            , where: {installId:installId} //传参*/
            ,page:false
            , cols: [[
                {field: 'fileName', title: '名称', minWidth: '160', align: 'center'}
                ,{field: 'filePath', title: '路径', minWidth: '100', align: 'center'}
                , {field: 'uploadTime', title: '上传时间', minWidth: '180', align: 'center'}
                , {title: '操作', fixed: 'right', maxWidth:'80', align: 'center', toolbar: '#barFileList'}
            ]]
            ,done: function (res, curr, count) {
                $("[data-field='filePath']").css('display', 'none');
            }
        });
    }

    //监听工具条
    table.on('tool(fileListFilter)', function (obj) {
        var data = obj.data;
        if (data == null || data == "") {
            layer.msg("请选择需要操作的设备！");
            return;
        }
        if(obj.event === 'view'){
            window.open(data.fileRelativePath);
        }else if (obj.event === 'delete') {
            layer.confirm('确认删除\'' + data.fileName + '\'吗', function (index) {
                //弹出loading
                var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
                $.ajax({
                    url: "../../FileOperateController/deleteInstallGoodsFile",
                    type: "post",
                    dataType: "json",
                    data: {"fileId": data.fileId},
                    error: function (json) {
                        top.layer.close(index);
                        top.layer.msg("文件删除失败！");
                    },
                    success: function (json) {
                        top.layer.close(index);
                        if (json.result) {
                            top.layer.msg("文件删除成功！");
                            searchFile();
                        } else {
                            top.layer.msg(json.detail);
                        }
                    }
                });
                layer.closeAll('dialog');
            });
        }
    });

    //添加上传文件
    $("#uploadSubmit").click(function () {
        if(installId <=0){
            layer.msg("请先安装设备后再进行文件上传！");
            return false;
        }
        var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
        $.ajaxFileUpload({
            url: '../../FileOperateController/fileUpload', //用于文件上传的服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: 'uploadFile', //文件上传域的ID
            dataType: 'json', //返回值类型 一般设置为json
            type: 'post',
            data:{"installId":installId},
            success: function(data)  //服务器成功响应处理函数
            {
                top.layer.close(index);
                if(data != null && data.result){
                    layer.msg(data.detail);
                    searchFile();
                }else{
                    layer.msg(data.detail);
                }
            },
            error: function (data, status, e)//服务器响应失败处理函数
            {
                top.layer.close(index);
                layer.msg("异常");
            }
        });
        top.layer.close(index);
    });

});

function showFormData(data) {
    installId = data;
}