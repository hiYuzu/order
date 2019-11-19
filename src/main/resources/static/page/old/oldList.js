var typeCodePn = 'PN';
layui.config({
    base: "js/"
}).use(['form', 'layer', 'jquery', 'table'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        table = layui.table,
        $ = layui.jquery;

    $.ajax({
        url: "../../AuthoritySetController/isAuthorityPage",
        type: "post",
        dataType: "json",
        data: {
            "pageCode": "old"
        },
        error: function () {
            parent.window.location.href = "../../login.html";
        },
        success: function (json) {
            if (json != null) {
                if (json.result) {
                    initOldPage();
                } else {
                    // top.layer.msg(json.detail);
                    layer.open({
                        content: json.detail
                        , skin: 'msg'
                        , time: 2000//2秒后自动关闭
                        , end: function () {
                            location.href = 'page/404.html';
                        }
                    });
                }
            } else {
                top.layer.msg("页面权限判断失败！");
            }
        }
    });

    function initOldPage() {
        //初始化下拉框
        initSelect();
        //查询数据
        startSearchOld();
    }


    function initSelect() {
        //P/N
        $.ajax({
            url: "../../ParamController/getParamByType",
            type: "post",
            dataType: "json",
            async: false,
            data: {typeCode: typeCodePn},
            error: function () {
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
                    form.render('select', 'searchPn');
                } else {
                    top.layer.msg("初始化P/N失败！");
                }
            }
        });
    }

    function startSearchOld() {
        var pn = $("#searchPn").val();
        var finishFlag = $("#searchFinishFlag").val();
        var snCode = $("#searchSnCode").val();
        var jobNo = $("#searchJobNo").val();
        searchOld(pn, finishFlag, snCode, jobNo);
    }

    function searchOld(pn, finishFlag, snCode, jobNo) {
        table.render({
            id: 'oldId,assembleId,pnCode,assembleStatusCode',
            elem: '#oldListTable',
            url: '../../AssembleOldController/getAssembleOld',
            method: 'post',
            where: {
                pnCode: pn,
                finishFlag: finishFlag,
                snCode: snCode,
                jobNo: jobNo
            },//传参*/
            page: true, //开启分页
            cols: [[{field: 'pnName', title: 'P/N', minWidth: '100', sort: true}
                , {field: 'snCode', title: 'S/N', minWidth: '100', sort: true}
                , {field: 'assembleStatusName', title: '组装单状态', minWidth: '120', sort: true}
                , {field: 'jobNo', title: '生产工作单', minWidth: '160', sort: true}
                , {field: 'cruxNo', title: '关键部件', minWidth: '160', sort: true}
                , {field: 'completeDate', title: '完成时间', minWidth: '120', sort: true}
                , {field: 'assembleMemo', title: '组装单备注', minWidth: '160'}
                , {field: 'testBeginTime', title: '测试开始时间', minWidth: '120', sort: true}
                , {field: 'testEndTime', title: '测试结束时间', minWidth: '120', sort: true}
                , {field: 'beginTime', title: '老化开始时间', minWidth: '120', sort: true}
                , {field: 'endTime', title: '老化结束时间', minWidth: '120', sort: true}
                , {field: 'oldMemo', title: '老化备注', minWidth: '160'}
                , {field: 'optUserName', title: '操作者', minWidth: '120'}
                , {field: 'optTime', title: '操作时间', minWidth: '180'}
                , {title: '操作', fixed: 'right', minWidth: 160, align: 'center', toolbar: '#barOldList'}
            ]]
        });
    }

    //监听工具条
    table.on('tool(oldListFilter)', function (obj) {
        var data = obj.data;
        if (data == null || data == "") {
            layer.msg("请选择需要操作的数据！");
            return;
        }
        if (obj.event === 'select') {
            layui.layer.open({
                title: "部件列表"
                , type: 2
                , shadeClose: true
                , area: ['90%', '80%']
                , shade: 0.8
                , content: "../assemble/partsList.html"
                , success: function (layero, index) {
                    var frameWin = window[layero.find('iframe')[0]['name']];
                    frameWin.showFormData(data.assembleId);
                }
            });
        } else if (obj.event === 'edit') {
            var index = layui.layer.open({
                title: "老化处理",
                type: 2,
                content: "editOld.html",
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
        startSearchOld();
    });

    //添加导出
    $(".exportExcel_btn").click(function () {
        var pn = $("#searchPn").val();
        var finishFlag = $("#searchFinishFlag").val();
        var snCode = $("#searchSnCode").val();
        var jobNo = $("#searchJobNo").val();
        var params = {
            "pnCode": pn,
            "finishFlag": finishFlag,
            "snCode": snCode,
            "jobNo": jobNo
        };
        outPutAssembleData(params);
    });

    /**
     * 导出组装记录
     */
    function outPutAssembleData(params) {
        var form = $("<form>");//定义一个form表单
        form.attr("style", "display:none");
        form.attr("target", "");
        form.attr("method", "post");
        form.attr("action", "../../ReportController/downloadAssembleListExcel");
        for (var key in params) {
            var input = $("<input>");
            input.attr("type", "hidden");
            input.attr("name", key);
            input.attr("value", params[key]);
            form.append(input);
        }
        $("body").append(form);//将表单放置在web中
        form.submit();//表单提交
    }

});