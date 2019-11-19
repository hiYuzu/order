layui.config({
	base : "js/"
}).use(['form','element','layer','jquery'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		element = layui.element,
		$ = layui.jquery;

	$(".panel a").on("click",function(){
		window.parent.addTab($(this));
	});

	//获取公告列表
    $.ajax({
        url : "NewsController/getNews",
        type : "post",
        dataType : "json",
        data : {
        	"newsShow":true,
            "page" : 1,
			"limit":10
        },
        error : function(json) {
            top.layer.msg("获取公告信息失败！");
        },
        success : function(json) {
            if (json != null) {
                if(json.count>0){
                    initNews(json.data);
                }else{
                    top.layer.msg(json.detail);
                }
            } else {
                top.layer.msg("获取公告信息失败！");
            }
        }
    });
    function initNews(data){
        //加载最新文章
        var hotNewsHtml = '';
        for(var i=0;i<data.length;i++){
            hotNewsHtml += '<tr>'
                +'<td align="left">'+data[i].newsTitle+'</td>'
                +'<td align="left">'+data[i].newsContent+'</td>'
                +'<td>'+data[i].showTime+'</td>'
                +'</tr>';
        }
        $(".hot_news").html(hotNewsHtml);
    }

    //待组装数量
    $.get("AssembleController/getAssembleCount?assembleType=assemble",
        function(data){
            $(".assembleCount span").text(data);
        }
    );
    //待测试数量
    $.get("AssembleController/getAssembleCount?assembleType=test",
        function(data){
            $(".testCount span").text(data);
        }
    );
    //待老化数量
    $.get("AssembleController/getAssembleCount?assembleType=old",
        function(data){
            $(".oldCount span").text(data);
        }
    );

	//待发货数量
	$.get("DeliverController/getDeliverCount?deliverType=deliver",
		function(data){
			$(".deliverCount span").text(data);
		}
	);

	//待商务数量
	$.get("DeliverController/getDeliverCount?deliverType=business",
		function(data){
			$(".businessCount span").text(data);
		}
	);

	//待安装数量
	$.get("DeliverController/getDeliverCount?deliverType=install",
		function(data){
			$(".installCount span").text(data);
		}
	);


	//数字格式化
	$(".panel span").each(function(){
		$(this).html($(this).text()>9999 ? ($(this).text()/10000).toFixed(2) + "<em>万</em>" : $(this).text());	
	});

	//系统基本参数
	if(window.sessionStorage.getItem("systemParameter")){
		var systemParameter = JSON.parse(window.sessionStorage.getItem("systemParameter"));
		fillParameter(systemParameter);
	}else{
		$.ajax({
			url : "../json/systemParameter.json",
			type : "get",
			dataType : "json",
			success : function(data){
				fillParameter(data);
			}
		})
	}

	//填充数据方法
 	function fillParameter(data){
 		//判断字段数据是否存在
 		function nullData(data){
 			if(data == '' || data == "undefined"){
 				return "未定义";
 			}else{
 				return data;
 			}
 		}
 		$(".version").text(nullData(data.version));      //当前版本
		$(".author").text(nullData(data.author));        //开发作者
		$(".homePage").text(nullData(data.homePage));    //网站首页
		$(".server").text(nullData(data.server));        //服务器环境
		$(".dataBase").text(nullData(data.dataBase));    //数据库版本
		$(".maxUpload").text(nullData(data.maxUpload));    //最大上传限制
		$(".userRights").text(nullData(data.userRights));//当前用户权限
 	}

});
