layui.config({
    base : "js/"
}).use(['form','layer','jquery'],function() {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery;

    $.ajax({
        url : "../../AuthoritySetController/isAuthorityPage",
        type : "post",
        dataType : "json",
        data : {
            "pageCode" : "authority"
        },
        error : function(json) {
            parent.window.location.href = "../../login.html";
        },
        success : function(json) {
            if (json != null) {
                if(json.result){
                    initAllAuthorityItem();
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
    
    function initAllAuthorityItem(){
        initAuthorityItem("1");
        initAuthorityItem("2");
        initAuthorityItem("3");
        initAuthorityItem("4");
        initAuthorityItem("5");
        initAuthorityItem("6");
        initAuthorityItem("7");
        initAuthorityItem("8");
        initAuthorityItem("9");
        initAuthorityItem("10");
    };


    function initAuthorityItem(userType) {
        //合同类型
        $.ajax({
            url : "../../PageController/getPage",
            type : "post",
            dataType : "json",
            data : {
                "userType" : userType
            },
            error : function(json) {
                top.layer.msg("初始化权限项目失败！");
            },
            success : function(json) {
                if (json != null && json.data!=null) {
                    // $("#"+userType+"").append(combineInput(userType,json.data));
                    $("#"+userType+"").append(combineInput(userType,json.data));
                    form.render();
                } else {
                    top.layer.msg("初始化权限项目失败！");
                }
            }
        });
    }

    function combineInput(name,data){
        var optionStr = "";
        $.each(data,function (index, value) {
            optionStr += "<input type='checkbox' title='" + value.pageNameCn
                + "' name='"+value.pageCode+name+"'"+(value.pageChecked===true?"checked":"")+">";
        });
        return optionStr;
    }

    form.on("submit(authoritySubmit)", function (data) {
        var dataPara = data.field;
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
        $.ajax({
            url: "../../AuthoritySetController/operateAuthoritySet",
            type: "post",
            dataType: "json",
            data: dataPara,
            error: function (json) {
                top.layer.close(index);
                top.layer.msg("设置权限页面记录失败！");
            },
            success: function (json) {
                top.layer.close(index);
                if (json.result) {
                    top.layer.msg("设置权限页面成功！");
                    location.reload();
                } else {
                    top.layer.msg("设置权限页面失败,失败信息为:"+json.detail);
                }
            }
        });
        return false;
    });

});
