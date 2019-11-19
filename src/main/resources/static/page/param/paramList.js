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
            "pageCode" : "param"
        },
        error : function(json) {
            parent.window.location.href = "../../login.html";
        },
        success : function(json) {
            if (json != null) {
                if(json.result){
                    startSearchParam();
                }else{
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

    function startSearchParam() {
        var paramType = $("#searchParamType").val();
        var paramValue = $("#searchParamValue").val();
        searchParam(paramType, paramValue);
    }

    function searchParam(paramTypeCode, paramName) {
        table.render({
            id: 'paramId,paramTypeCode'
            , elem: '#paramListTable'
            , url: '../../ParamController/getParam'
            , method: 'post'
            , where: {paramTypeCode: paramTypeCode, paramName: paramName} //传参*/
            , page: true //开启分页
            , cols: [[
                {field: 'paramTypeName', title: '参数类型', minWidth: '100', sort: true}
                , {field: 'paramCode', title: '参数编码', minWidth: '120'}
                , {field: 'paramName', title: '参数数值', minWidth: '120'}
                , {field: 'optTime', title: '操作时间', minWidth: '180'}
                , {title: '操作', fixed: 'right', minWidth: 120, align: 'center', toolbar: '#barParamList'}
            ]]
        });
    }

    //监听工具条
    table.on('tool(paramListFilter)', function (obj) {
        var data = obj.data;
        if (data == null || data == "") {
            layer.msg("请选择需要操作的参数！");
            return;
        }
        if (obj.event === 'delete') {
            layer.confirm('确认删除\'' + data.paramName + '\'吗', function (index) {
                var idArray = [];
                idArray.push(data.paramId);
                //弹出loading
                var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
                $.ajax({
                    url: "../../ParamController/deleteParams",
                    type: "post",
                    dataType: "json",
                    data: {"list": idArray},
                    error: function (json) {
                        top.layer.close(index);
                        top.layer.msg("参数删除失败！");
                    },
                    success: function (json) {
                        top.layer.close(index);
                        if (json.result) {
                            top.layer.msg("参数删除成功！");
                            startSearchParam();
                        } else {
                            top.layer.msg(json.detail);
                        }
                    }
                });
            });
        } else if (obj.event === 'edit') {
            var index = layui.layer.open({
                title: "修改参数",
                type: 2,
                content: "editParam.html",
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
        startSearchParam();
    });

    //添加参数
    $(".paramAdd_btn").click(function () {
        var index = layui.layer.open({
            title: "添加参数",
            type: 2,
            content: "addParam.html"
        });
        //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
        $(window).resize(function () {
            layui.layer.full(index);
        });
        layui.layer.full(index);
    });

});