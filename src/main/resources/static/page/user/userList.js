layui.config({
    base: "js/"
}).use(['form', 'layer', 'jquery', 'table'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        table = layui.table,
        $ = layui.jquery;


    $.ajax({
        url : "../../AuthoritySetController/isAuthorityPage",
        type : "post",
        dataType : "json",
        data : {
            "pageCode" : "user"
        },
        error : function(json) {
            parent.window.location.href = "../../login.html";
        },
        success : function(json) {
            if (json != null) {
                if(json.result){
                    initUserPage();
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

    function initUserPage(){
        var userName = $("#searchName").val();
        var stopFlag = $("#stopFlag").val();
        searchUser(userName, stopFlag);
    }

    function searchUser(userName, stopFlag) {
        table.render({
            id: 'userId,userType,stopFlag'
            , elem: '#userListTable'
            , url: '../../UserController/getUser'
            , method: 'post'
            , where: {stopFlag: stopFlag, userName: userName} //传参*/
            , page: true //开启分页
            , cols: [[
                {checkbox: true, fixed: true}
                , {field: 'userCode', title: '编码', minWidth: '100'}
                , {field: 'userName', title: '名称', minWidth: '120'}
                , {field: 'userTypeName', title: '类型', minWidth: '120', sort: true}
                , {field: 'stopFlagName', title: '停用', minWidth: '100'}
                , {field: 'userDetail', title: '用户描述', minWidth: '160'}
                , {field: 'endTime', title: '登录时间', minWidth: '180'}
                , {field: 'optUserName', title: '操作者', minWidth: '120'}
                , {field: 'optTime', title: '操作时间', minWidth: '180'}
                , {title: '操作', fixed: 'right', minWidth: 120, align: 'center', toolbar: '#barUserList'}
            ]]
        });
    }

    //监听工具条
    table.on('tool(userListFilter)', function (obj) {
        var data = obj.data;
        if (data == null || data == "") {
            layer.msg("请选择需要操作的用户！");
            return;
        }
        if (obj.event === 'del') {
            layer.confirm('确认删除\'' + data.userName + '\'吗', function (index) {
                var idArray = [];
                idArray.push(data.userId);
                //弹出loading
                var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
                $.ajax({
                    url: "../../UserController/deleteUsers",
                    type: "post",
                    dataType: "json",
                    data: {"list": idArray},
                    error: function (json) {
                        top.layer.close(index);
                        top.layer.msg("用户删除失败！");
                    },
                    success: function (json) {
                        top.layer.close(index);
                        if (json.result) {
                            top.layer.msg("用户删除成功！");
                            initUserPage();
                        } else {
                            top.layer.msg(json.detail);
                        }
                    }
                });
            });
        } else if (obj.event === 'edit') {
            var index = layui.layer.open({
                title: "修改用户",
                type: 2,
                content: "editUser.html",
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
    //添加查询
    $(".search_btn").click(function () {
        initUserPage();
    });

    //添加会员
    $(".usersAdd_btn").click(function () {
        var index = layui.layer.open({
            title: "添加用户",
            type: 2,
            content: "addUser.html",
            success: function (layero, index) {
                // layui.layer.tips('点击此处返回文章列表', '.layui-layer-setwin .layui-layer-close', {
                // 	tips: 3
                // });
            }
        });
        //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
        $(window).resize(function () {
            layui.layer.full(index);
        });
        layui.layer.full(index);
    });

    //批量删除
    $(".batchDel").click(function () {
        layer.confirm('确认删除选择的用户吗?', function (index) {
            var idArray = [];
            var checkStatus = table.checkStatus('userId,userType,stopFlag')
                , data = checkStatus.data;
            if (data == null || data.length == 0) {
                layer.alert("请选择需要删除用户！");
                return;
            }
            layui.each(data, function (index, item) {
                idArray.push(item.userId);
            });
            //弹出loading
            var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
            $.ajax({
                url: "../../UserController/deleteUsers",
                type: "post",
                dataType: "json",
                data: {"list": idArray},
                error: function (json) {
                    top.layer.close(index);
                    top.layer.msg("用户删除失败！");
                },
                success: function (json) {
                    top.layer.close(index);
                    if (json.result) {
                        top.layer.msg("用户删除成功！");
                        //重新查询
                        initUserPage();
                    } else {
                        top.layer.msg(json.detail);
                    }
                }
            });
        });
    });

});