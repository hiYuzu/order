var $form;
var form;
var $;
layui.config({
    base: "../../js/"
}).use(['form', 'layer'], function () {
    form = layui.form;
    var layer = parent.layer === undefined ? layui.layer : parent.layer;
    $ = layui.jquery;
    $form = $('form');

    //获取登录用户信息
    $.ajax({
        url: "../../UserController/getLoginUser",
        type: "post",
        dataType: "json",
        error: function (json) {
            top.layer.msg("获取登录用户名失败！");
        },
        success: function (json) {
            if (json != null && json != null) {
                //重新查询
                $("#userName").val(json.userName);
            } else {
                top.layer.msg("获取登录用户名失败！");
            }
        }
    });

    //添加验证规则
    form.verify({
        userName: function (value, item) {
            if (value == "" || value== null) {
                return "请重新登录后尝试！";
            }
        },
        newPwd: function (value, item) {
            if ($("#oldPwd").val() == value) {
                return "新密码与旧密码一致，请重新输入！";
            }
        },
        confirmPwd: function (value, item) {
            if ($("#newPwd").val() != value) {
                return "两次输入密码不一致，请重新输入！";
            }
        }
    });

    //更新用户密码
    form.on("submit(changePwd)", function (data) {
        var index = layer.msg('提交中，请稍候', {icon: 16, time: false, shade: 0.8});
        //更新用户密码
        $.ajax({
            url: "../../UserController/updateUserPassword",
            type: "post",
            dataType: "json",
            data: {"oldPwd": $("#oldPwd").val(),"newPwd": $("#newPwd").val()},
            error: function (json) {
                top.layer.msg("更新用户密码失败！");
            },
            success: function (json) {
                if (json != null) {
                    if(json.result){
                        top.layer.msg("更新用户密码成功！");
                    }else{
                        top.layer.msg(json.detail);
                    }
                } else {
                    top.layer.msg("更新用户密码失败！");
                }
            }
        });
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    //重置页面内同容
    $("#reset").click(function () {
        $("#oldPwd").val("");
        $("#newPwd").val("");
        $("#confirmPwd").val("");
    });

});