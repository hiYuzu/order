var row;
layui.config({
    base : "js/"
}).use(['form','layer','jquery','layedit','laydate'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        layedit = layui.layedit,
        laydate = layui.laydate,
        $ = layui.jquery;

    //初始化时间
    laydate.render({
        elem: '#showTime'
    });

    //创建一个编辑器并赋值
    var editIndex = layedit.build('newsContent');

    form.verify({
        newsContent: function(value) {
            return layedit.sync(editIndex);
        }
    });

    initFormData();

    //重置页面内同容
    $("#reset").click(function () {
        initFormData();
    });

    function initFormData() {
        if (row != null && row != '') {
            $("#newsId").val(row.newsId);
            $("#newsTitle").val(row.newsTitle);
            $("#newsAuthor").val(row.newsAuthor);
            if(row.newsShow){
                $("input[name='newsShow']").prop("checked", true);
            }
            $("#showTime").val(row.showTime);
            try
            {
                layedit.setContent(editIndex,row.newsContent);
            }catch(e){
                console.log(e.message);
            }
            form.render();
        }
    }

    form.on("submit(editNews)",function(data){
        var dataPara=data.field;
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        $.ajax({
            url : "../../NewsController/updateNews",
            type : "post",
            dataType : "json",
            data : dataPara,
            error : function(json) {
                top.layer.close(index);
                top.layer.msg("公告添加失败！");
            },
            success : function(json) {
                top.layer.close(index);
                if (json.result) {
                    top.layer.msg("公告添加成功！");
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                } else {
                    top.layer.msg("公告添加失败！");
                }
            }
        });
        return false;
    });

});

function showFormData(data) {
    row = data;
}
