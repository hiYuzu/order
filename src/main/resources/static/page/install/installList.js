var typeCodeCt = 'CT';
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
            "pageCode": "install"
        },
        error: function (json) {
            parent.window.location.href = "../../login.html";
        },
        success: function (json) {
            if (json != null) {
                if (json.result) {
                    initInstallPage();
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

    function initInstallPage() {
        //初始化下拉框
        initSelect();
        //查询数据
        startSearchInstall();
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
                    form.render('select', 'searchCtType');
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
                    form.render('select', 'searchPn');
                } else {
                    top.layer.msg("初始化P/N失败！");
                }
            }
        });
    }

    function startSearchInstall() {
        var ctType = $("#searchCtType").val();
        var pn = $("#searchPn").val();
        var installStatus = $("#searchInstallStatus").val();
        var ctCode = $("#searchCtCode").val();
        var snCode = $("#searchSnCode").val();
        var installEnterprise = $("#searchInstallEnterprise").val();
        searchGoodsInstall(ctType, pn, installStatus, ctCode, snCode, installEnterprise);
    }

    function searchGoodsInstall(ctType, pn, installStatus, ctCode, snCode, installEnterprise) {
        table.render({
            id: 'installId,contractTypeCode,pnCode,deliverId,goodsId,assembleId,installStatusCode,installUser,beginTime,warrantyPeriod,deliverDate,warrantyClause'
            , elem: '#installListTable'
            , url: '../../InstallController/getGoodsInstall'
            , method: 'post'
            , where: {
                contractTypeCode: ctType,
                pnCode: pn,
                installStatusCode: installStatus,
                contractCode: ctCode,
                snCode: snCode,
                installEnterprise: installEnterprise
            } //传参*/
            ,
            page: true //开启分页
            ,
            cols: [[{field: 'contractCode', title: '合同号码', minWidth: '120', sort: true}
                , {field: 'contractTypeName', title: '合同类型', minWidth: '100', sort: true}
                , {field: 'deliverTypeName', title: '单据类型', minWidth: '100', sort: true}
                , {field: 'pnName', title: 'P/N', minWidth: '100', sort: true}
                , {field: 'snCode', title: 'S/N', minWidth: '160', sort: true}
                , {field: 'goodsMemo', title: '发货设备备注', minWidth: '160'}
                , {field: 'installEnterprise', title: '安装企业', minWidth: '160', sort: true}
                , {field: 'contractWarranty', title: '合同保质期', minWidth: '80', sort: true}
                , {field: 'contractBeginTime', title: '合同开始时间', minWidth: '120', sort: true}
                , {field: 'contractEndTime', title: '合同结束时间', minWidth: '120', sort: true}
                , {field: 'installStatusName', title: '安装状态', minWidth: '140', sort: true}
                , {field: 'endTime', title: '安装结束日期', minWidth: '120', sort: true}
                , {field: 'warrantyPeriod', title: '质保期限', minWidth: '120', sort: true}
                , {field: 'instrumentBrand', title: '仪器品牌', minWidth: '120', sort: true}
                , {field: 'installMemo', title: '安装备注', minWidth: '160'}
                , {field: 'optUserName', title: '操作者', minWidth: '100'}
                , {field: 'optTime', title: '操作时间', minWidth: '160'}
                , {title: '操作', fixed: 'right', minWidth: 220, align: 'center', toolbar: '#barInstallList'}
            ]]
        });
    }

    //监听工具条
    table.on('tool(installListFilter)', function (obj) {
        var data = obj.data;
        console.log(data);
        if (data == null || data == "") {
            layer.msg("请选择需要操作的设备！");
            return;
        }
        if (obj.event === 'select') {
            if (data.deliverType === "2") {
                layer.msg("外购设备无部件！");
            } else {
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
            }
        } else if (obj.event === 'edit') {
            var content;
            if (data.deliverType === "2") {
                content = "editOutInstall.html";
            } else {
                content = "editInstall.html";
            }
            var index = layui.layer.open({
                title: "安装处理",
                type: 2,
                content: content,
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
        } else if (obj.event === 'upload') {
            var index = layui.layer.open({
                title: "上传文件"
                , type: 2
                , shadeClose: true
                , area: ['90%', '80%']
                , shade: 0.8
                , content: "uploadFile.html"
                , success: function (layero, index) {
                    var frameWin = window[layero.find('iframe')[0]['name']];
                    frameWin.showFormData(data.installId);
                }
            });
        } else if (obj.event === 'download') {
            downLoadFileReport(data.installId, '/FileOperateController/downLoadZipFile');
        }
    });

    function downLoadFileReport(installId, url) {
        $.ajax({
            url: "../../FileOperateController/queryFileCount",
            type: "post",
            dataType: "json",
            data: {
                "installId": installId
            },
            error: function (json) {
                layer.msg("下载失败！");
            },
            success: function (json) {
                if (json > 0) {
                    downloadReportZip(url, installId);
                } else {
                    layer.msg("下载失败，没有上传文件！");
                }
            }
        });
    }

    //添加查询
    $(".search_btn").click(function () {
        startSearchInstall();
    });

    //添加导出
    $(".exportExcel_btn").click(function () {
        var ctType = $("#searchCtType").val();
        var pn = $("#searchPn").val();
        var installStatus = $("#searchInstallStatus").val();
        var ctCode = $("#searchCtCode").val();
        var snCode = $("#searchSnCode").val();
        var installEnterprise = $("#searchInstallEnterprise").val();
        var params = {
            "contractTypeCode": ctType,
            "pnCode": pn,
            "installStatusCode": installStatus,
            "contractCode": ctCode,
            "snCode": snCode,
            "installEnterprise": installEnterprise,
            "type": "install"
        };
        outPutInstallData(params);
    });

    /**
     * 下载已有的压缩文件
     * @param url
     * @param taskId
     */
    function downloadReportZip(url, installId) {
        var form = $("<form>");//定义一个form表单
        form.attr("style", "display:none");
        form.attr("target", "");
        form.attr("method", "post");
        form.attr("action", url);
        var input1 = $("<input>");
        input1.attr("type", "hidden");
        input1.attr("name", "installId");
        input1.attr("value", installId);
        $("body").append(form);//将表单放置在web中
        form.append(input1);
        form.submit();//表单提交
    }

    /**
     * 导出安装记录
     */
    function outPutInstallData(params) {
        var form = $("<form>");//定义一个form表单
        form.attr("style", "display:none");
        form.attr("target", "");
        form.attr("method", "post");
        form.attr("action", "../../ReportController/downloadInstallListExcel");
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