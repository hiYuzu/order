layui.config({
	base : "js/"
}).use(['form','layer','jquery','table'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
        table = layui.table,
		$ = layui.jquery;

    $.ajax({
        url : "../../AuthoritySetController/isAuthorityPage",
        type : "post",
        dataType : "json",
        data : {
            "pageCode" : "news"
        },
        error : function(json) {
            parent.window.location.href = "../../login.html";
        },
        success : function(json) {
            if (json != null) {
                if(json.result){
                    initNewsPage();
                }else{
                    // top.layer.msg(json.detail);
                    layer.open({
                        content: json.detail
                        ,skin: 'msg'
                        ,time: 2000//2秒后自动关闭
                        ,end: function () {
                            location.href='page/404.html';
                        }
                    });
                }
            } else {
                top.layer.msg("页面权限判断失败！");
            }
        }
    });

	//加载页面数据
	function initNewsPage() {
		var searchNewsTitle = $("#searchNewsTitle").val();
        searchNews(searchNewsTitle);
    }

    function searchNews(newsTitle){
        table.render({
            id: 'newsId,newsShow,newsContent'
            , elem: '#newsListTable'
            , url: '../../NewsController/getNews'
            , method: 'post'
            , where: {newsTitle: newsTitle} //传参*/
            , page: true //开启分页
            , cols: [[
                {checkbox: true, fixed: true}
                , {field: 'newsTitle', title: '标题', minWidth: '100'}
                , {field: 'newsAuthor', title: '发布人', minWidth: '120'}
                , {field: 'newsShowName', title: '展示', minWidth: '120', sort: true}
                , {field: 'showTime', title: '发布时间', minWidth: '180', sort: true}
                , {field: 'optUserName', title: '操作者', minWidth: '120'}
                , {field: 'optTime', title: '操作时间', minWidth: '180'}
                , {title: '操作', fixed: 'right', minWidth: 160, align: 'center', toolbar: '#barNewsList'}
            ]]
        });
	}

	//查询
	$(".search_btn").click(function(){
        initNewsPage();
	});

	//添加文章
	$(".newsAdd_btn").click(function(){
		var index = layui.layer.open({
			title : "添加公告",
			type : 2,
			content : "newsAdd.html",
			success : function(layero, index){
			}
		});
		//改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
		$(window).resize(function(){
			layui.layer.full(index);
		});
		layui.layer.full(index);
	});

	//批量删除
	$(".batchDel").click(function(){
        layer.confirm('确认删除选择的公告吗?', function (index) {
            var idArray = [];
            var checkStatus = table.checkStatus('newsId,newsShow,newsContent')
                , data = checkStatus.data;
            if (data == null || data.length == 0) {
                layer.alert("请选择需要删除公告！");
                return;
            }
            layui.each(data, function (index, item) {
                idArray.push(item.newsId);
            });
            //弹出loading
            var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
            $.ajax({
                url: "../../NewsController/deleteNews",
                type: "post",
                dataType: "json",
                data: {"list": idArray},
                error: function (json) {
                    top.layer.close(index);
                    top.layer.msg("公告删除失败！");
                },
                success: function (json) {
                    top.layer.close(index);
                    if (json.result) {
                        top.layer.msg("公告删除成功！");
                        //重新查询
                        initNewsPage();
                    } else {
                        top.layer.msg(json.detail);
                    }
                }
            });
        });
	});

    //监听工具条
    table.on('tool(newsListFilter)', function (obj) {
        var data = obj.data;
        if (data == null || data == "") {
            layer.msg("请选择需要操作的公告！");
            return;
        }
        if (obj.event === 'del') {
            layer.confirm('确认删除\'' + data.newsTitle + '\'吗', function (index) {
                var idArray = [];
                idArray.push(data.newsId);
                //弹出loading
                var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
                $.ajax({
                    url: "../../NewsController/deleteNews",
                    type: "post",
                    dataType: "json",
                    data: {"list": idArray},
                    error: function (json) {
                        top.layer.close(index);
                        top.layer.msg("公告删除失败！");
                    },
                    success: function (json) {
                        top.layer.close(index);
                        if (json.result) {
                            top.layer.msg("公告删除成功！");
                            initNewsPage();
                        } else {
                            top.layer.msg(json.detail);
                        }
                    }
                });
            });
        } else if (obj.event === 'edit') {
            var index = layui.layer.open({
                title: "修改公告",
                type: 2,
                content: "newsEdit.html",
                success: function (layero, index) {
                    var frameWin = window[layero.find('iframe')[0]['name']];
                    frameWin.showFormData(data);
                }
            });
            //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
            $(window).resize(function () {
                layui.layer.full(index);
            });
            layui.layer.full(index);
        }
    });

});
