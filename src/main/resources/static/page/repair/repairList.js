var typeCodeCt = 'CT';
var typeCodePn = 'PN';
layui.config({
    base: "js/"
}).use(['form', 'layer', 'jquery', 'table', 'laydate'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        table = layui.table,
        $ = layui.jquery,
        laydate = layui.laydate;


    $.ajax({
        url : "../../AuthoritySetController/isAuthorityPage",
        type : "post",
        dataType : "json",
        data : {
            "pageCode" : "repair"
        },
        error : function(json) {
            parent.window.location.href = "../../login.html";
        },
        success : function(json) {
            if (json != null) {
                if(json.result){
                    initRepairPage();
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

    function initRepairPage() {

        //初始化时间
        laydate.render({
            elem: '#searchRepairTime'
        });

        //初始化下拉框
        initSelect();

        //查询数据
        startSearchRepair();
    }

    function initSelect() {
        //合同类型
        $.ajax({
            url: "../../ParamController/getParamByType",
            type: "post",
            dataType: "json",
            data: {typeCode: typeCodeCt},
            error: function (json) {
                top.layer.msg("初始化合同类型失败！");
            },
            success: function (json) {
                if (json != null && json.data != null) {
                    var optionStr = "<option value=''>---请选择---</option>";
                    $.each(json.data, function (index, value) {
                        optionStr += "<option value='" + value.paramCode + "'>" + value.paramName + "</option>";
                    });
                    $("#searchCtType").append(optionStr);
                    form.render('select','searchCtType');
                } else {
                    top.layer.msg("初始化合同类型失败！");
                }
            }
        });
        //P/N
        $.ajax({
            url: "../../ParamController/getParamByType",
            type: "post",
            dataType: "json",
            async: false,
            data: {typeCode: typeCodePn},
            error: function (json) {
                top.layer.msg("初始化P/N失败！");
            },
            success: function (json) {
                if (json != null && json.data != null) {
                    var optionStr = "<option value=''>---请选择---</option>";
                    $.each(json.data, function (index, value) {
                        optionStr += "<option value='" + value.paramCode + "'>"
                            + value.paramName + "</option>";
                    });
                    $("#searchPn").append(optionStr);
                    form.render('select','searchPn');
                } else {
                    top.layer.msg("初始化P/N失败！");
                }
            }
        });
    }

    function startSearchRepair(){
        var ctType = $("#searchCtType").val();
        var pn = $("#searchPn").val();
        var ctCode = $("#searchCtCode").val();
        var snCode = $("#searchSnCode").val();
        var repairTime = $("#searchRepairTime").val();
        searchGoodsInstall(ctType,pn, ctCode,snCode,repairTime);
    }

    function searchGoodsInstall(ctType,pn, ctCode,snCode,repairTime) {
        table.render({
            id: 'repairId,installId,contractTypeCode,pnCode,deliverId,goodsId,warrantyPeriod'
            , elem: '#repairListTable'
            , url: '../../RepairController/getGoodsRepair'
            , method: 'post'
            , where: {contractTypeCode: ctType,pnCode: pn,contractCode:ctCode, snCode: snCode,repairTime:repairTime} //传参*/
            , page: true //开启分页
            , cols: [[{field: 'contractCode', title: '合同号码', minWidth: '160',sort: true}
                , {field: 'contractTypeName', title: '合同类型', minWidth: '100',sort: true}
                , {field: 'pnName', title: 'P/N', minWidth: '100',sort: true}
                , {field: 'snCode', title: 'S/N', minWidth: '160',sort: true}
                , {field: 'repairUser', title: '维修人员', minWidth: '120', sort: true}
                , {field: 'repairTime', title: '维修时间', minWidth: '180',sort: true}
                , {field: 'materielName', title: '物料名称', minWidth: '160',sort: true}
                , {field: 'materielNumber', title: '物料数量', minWidth: '120'}
                , {field: 'warrantyPeriod', title: '质保期限', minWidth: '180',sort: true}
                , {field: 'optUserName', title: '操作者', minWidth: '120'}
                , {field: 'optTime', title: '操作时间', minWidth: '180'}
                , {title: '操作', fixed: 'right', minWidth: 160, align: 'center', toolbar: '#barRepairList'}
            ]]
        });
    }

    //监听工具条
    table.on('tool(repairListFilter)', function (obj) {
        var data = obj.data;
        if (data == null || data == "") {
            layer.msg("请选择需要操作的设备！");
            return;
        }
        if (obj.event === 'edit') {
            var index = layui.layer.open({
                title: "更新维修记录",
                type: 2,
                content: "editRepair.html",
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
        }else if(obj.event === 'delete'){
            layer.confirm('确认删除S/N是\'' + data.snCode + '\'的维修记录吗', function (index) {
                //弹出loading
                var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
                $.ajax({
                    url: "../../RepairController/deleteGoodsRepair",
                    type: "post",
                    dataType: "json",
                    data: {"repairId": data.repairId},
                    error: function (json) {
                        top.layer.close(index);
                        top.layer.msg("维修记录删除失败！");
                    },
                    success: function (json) {
                        top.layer.close(index);
                        if (json.result) {
                            top.layer.msg("维修记录删除成功！");
                            startSearchRepair();
                        } else {
                            top.layer.msg(json.detail);
                        }
                    }
                });
            });
        }
    });

    //添加维修记录
    $(".repairAdd_btn").click(function () {
        var index = layui.layer.open({
            title: "添加维修记录",
            type: 2,
            content: "addRepair.html",
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

    //添加查询
    $(".search_btn").click(function () {
        startSearchRepair();
    });

    //添加导出
    $(".exportExcel_btn").click(function () {
        var ctType = $("#searchCtType").val();
        var pn = $("#searchPn").val();
        var ctCode = $("#searchCtCode").val();
        var snCode = $("#searchSnCode").val();
        var repairTime = $("#searchRepairTime").val();
        var params = {"contractTypeCode":ctType,"pnCode":pn,"contractCode":ctCode,"snCode":snCode,"repairTime":repairTime,"type":"repair"};
        outPutRepairData(params);
    });

    /**
     * 导出维修记录
     */
    function outPutRepairData(params){
        var form=$("<form>");//定义一个form表单
        form.attr("style","display:none");
        form.attr("target","");
        form.attr("method","post");
        form.attr("action","../../ReportController/downloadRepairListExcel");
        for(var key in params){
            var input=$("<input>");
            input.attr("type","hidden");
            input.attr("name",key);
            input.attr("value",params[key]);
            form.append(input);
        }
        $("body").append(form);//将表单放置在web中
        form.submit();//表单提交
    }

});
