var typeCodeCt = 'CT';
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

    //查询数据
    startSearchInstall();

    function startSearchInstall(){
        var ctType = $("#searchCtType").val();
        var pn = $("#searchPn").val();
        var ctCode = $("#searchCtCode").val();
        var snCode = $("#searchSnCode").val();
        var installEnterprise = $("#searchInstallEnterprise").val();
        searchGoodsInstall(ctType,pn, ctCode,snCode,installEnterprise);
    }

    function searchGoodsInstall(ctType,pn, ctCode,snCode,installEnterprise) {
        table.render({
            id: 'installId,contractTypeCode,pnCode,deliverId,deliverType,goodsId,installStatusCode,installUser,beginTime,endTime,warrantyPeriod'
            , elem: '#installListTable'
            , url: '../../InstallController/getGoodsInstall'
            , method: 'post'
            , where: {contractTypeCode: ctType,pnCode: pn,contractCode:ctCode, snCode: snCode,installEnterprise:installEnterprise,installStatusCode:true} //传参*/
            , page: true //开启分页
            , cols: [[
                {title: '操作', fixed: 'left', minWidth: '160', align: 'center', toolbar: '#barInstallList'}
                , {field: 'installEnterprise', title: '安装企业', minWidth: '160', sort: true}
                , {field: 'warrantyPeriod', title: '质保期限', minWidth: '120'}
                , {field: 'pnName', title: 'P/N', minWidth: '100',sort: true}
                , {field: 'snCode', title: 'S/N', minWidth: '100',sort: true}
                , {field: 'contractCode', title: '合同号码', minWidth: '160',sort: true}
                , {field: 'contractTypeName', title: '合同类型', minWidth: '100',sort: true}
                , {field: 'contractWarranty', title: '合同保质期', minWidth: '120',sort: true}
                , {field: 'contractBeginTime', title: '合同开始时间', minWidth: '120',sort: true}
                , {field: 'contractEndTime', title: '合同结束时间', minWidth: '120',sort: true}
                , {field: 'installStatusName', title: '安装状态', minWidth: '140',sort: true}
                , {field: 'optUserName', title: '操作者', minWidth: '120'}
                , {field: 'optTime', title: '操作时间', minWidth: '180'}
            ]]
        });
    }

    //监听工具条
    table.on('tool(installListFilter)', function (obj) {
        var data = obj.data;
        if (data == null || data == "") {
            layer.msg("请选择需要操作的设备！");
            return;
        }
        if (obj.event === 'select') {
            parent.showFormData(data);
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }
    });
    //添加查询
    $(".search_btn").click(function () {
        startSearchInstall();
    });

});