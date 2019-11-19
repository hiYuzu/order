var typeCodePn = 'PN';
layui.config({
    base: "js/"
}).use(['form', 'layer', 'jquery', 'table'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        table = layui.table,
        $ = layui.jquery;

    //初始化下拉框
    initSelect();

    function initSelect() {
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

    //查询数据
    startSearchAssemble();

    function startSearchAssemble() {
        var pn = $("#searchPn").val();
        var snCode = $("#searchSnCode").val();
        var jobNo = $("#searchJobNo").val();
        searchAssemble(pn, snCode, jobNo);
    }

    function searchAssemble(pn, snCode, jobNo) {
        table.render({
            id: 'oldId,assembleId,pnCode,assembleStatusCode',
            elem: '#assembleListTable',
            url: '../../AssembleStockController/getAssembleStock',
            method: 'post',
            where: {
                pnCode: pn,
                finishFlag: false,
                snCode: snCode,
                jobNo: jobNo
            } //传参*/
            ,
            page: true //开启分页
            ,
            cols: [[
                {type: 'checkbox', fixed: 'left'}
                , {field: 'pnName', title: 'P/N', minWidth: '100', sort: true}
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
            ]]
        });
    }

    //选择设备
    $(".batchSelect").click(function () {
        var checkStatus = table.checkStatus('oldId,assembleId,pnCode,assembleStatusCode')
            , data = checkStatus.data;
        if (data == null || data.length == 0) {
            layer.alert("请选择发货设备！");
            return;
        }
        layui.each(data, function (index, item) {
            var assembleStatusCode = item.assembleStatusCode;
            if (assembleStatusCode === "090") {
                layer.alert("不能选择已发货设备！");
                return;
            }
        });
        parent.showGoodsData(data);//返回选择数据
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    });

    //查询
    $(".search_btn").click(function () {
        startSearchAssemble();
    });

});