package com.tcb.util;

/**
 * @Author: WangLei
 * @Description:
 * @Date: Create in 2018/1/26 14:15
 * @Modify by WangLei
 */
public class DefaultParam {

    public static final int liSelectOk = 0;

    public static final int liSelectError = 1;

    /**
     * pwd默认值:111111
     */
    public static final String PWD_DEFAULT = "111111";

    /**
     * YES默认值:1
     */
    public static final String YES = "1";

    /**
     * NO默认值:0
     */
    public static final String NO = "0";

    /**
     * 外购默认值:2
     */
    public static final String OUTSOURCING = "2";

    /**
     * 定义组装状态
     */
    public static String PRODUCT_ASSEMBLE = "060";//组装中
    public static String PRODUCT_ASSEMBLED = "061";//已组装，待测试
    public static String PRODUCT_ASSEMBLE_REJECT = "062";//被驳回,待组装
    public static String PRODUCT_TEST = "070";//测试中
    public static String PRODUCT_TESTED = "071";//已测试,待老化
    public static String PRODUCT_TEST_REJECT = "072";//被驳回,待测试
    public static String PRODUCT_OLD = "080";//老化中
    public static String PRODUCT_OLDED = "081";//已老化,待发货
    public static String PRODUCT_DELIVER = "090";//已发货
    public static String PRODUCT_DELIVER_RESET = "092";//已重置,待发货

    /**
     * 定义发货单状态
     */
    public static String DELIVER_SAVE = "100";//已保存，待提交
    public static String DELIVER_SUBMIT = "101";//已提交，待商务
    public static String DELIVER_REJECT = "102";//被拒绝，待修改
    public static String DELIVER_BUSINESS = "200";//已商务，待安装
    public static String DELIVER_INSTALLING = "300";//安装中
    public static String DELIVER_INSTALLED = "301";//已安装

    /**
     * 报表类型
     */
    public static String REPORT_DELIVER="deliver";//发货单
    public static String REPORT_BUSINESS="business";//商务单
    public static String REPORT_INSTALL="install";//安装单
    public static String REPORT_REPAIR="repair";//维修单
    public static String REPORT_ASSEMBLE="assemble";//组装单

    /**
     * 上传文件夹名称
     */
    public static String UPLOAD_FOLDER = "upload";

}
