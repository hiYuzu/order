package com.lly.controller;

import com.lly.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lly.util.OutPutExcel;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ReportController")
public class ReportController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "ReportController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(ReportController.class);

    @Resource
    private RepairController repairController;

    @Resource
    private InstallController installController;

    @Resource
    private DeliverController deliverController;

    @Resource
    private DeliverGoodsController deliverGoodsController;

    @Resource
    private BusinessController businessController;

    @Resource
    private AssembleController assembleController;

    @Resource
    private AssemblePartController assemblePartController;

    /**
     * 导出发货记录EXCEL
     *
     * @param deliverModel
     * @param type
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/downloadDeliverListExcel")
    public void downloadDeliverListExcel(DeliverModel deliverModel, String type,
                                         HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        ResultListModel<DeliverModel> resultListModel = deliverController.getDeliver(deliverModel, request.getSession());
        if (resultListModel != null && resultListModel.getData() != null) {
            for (DeliverModel temp : resultListModel.getData()) {
                ResultListModel<DeliverGoodsModel> goodsModelResultListModel = deliverGoodsController.getGoodsDeliverById(temp.getDeliverId(), request.getSession());
                if (goodsModelResultListModel != null && goodsModelResultListModel.getData() != null) {
                    String goodsModelString = "";
                    for (DeliverGoodsModel temp1 : goodsModelResultListModel.getData()) {
                        goodsModelString += temp1.getSnCode() + ":" + (temp1.getInstallEnterprise() == null ? "" : temp1.getInstallEnterprise()) + ";";
                    }
                    temp.setGoodsModelString(goodsModelString);
                }
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        //给map填充数据
        map.put("deliverModelList", resultListModel.getData());
        //提示：在调用工具类生成Word文档之前应当检查所有字段是否完整
        //否则Freemarker的模板殷勤在处理时可能会因为找不到值而报错，这里暂时忽略这个步骤
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;

        try {
            //调用工具类WordGenerator的createDoc方法生成Word文档
            OutPutExcel outPutExcel = new OutPutExcel();
            file = outPutExcel.createExcel(type, map);
            fin = new FileInputStream(file);

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msexcel");
            response.addHeader("Content-Disposition", "attachment;filename=deliverList.xls");

            out = response.getOutputStream();
            byte[] buffer = new byte[1024];//缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fin != null) fin.close();
            if (out != null) out.close();
            if (file != null) file.delete(); // 删除临时文件
        }
    }

    /**
     * 导出商务记录EXCEL
     *
     * @param deliverModel
     * @param type
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/downloadBusinessListExcel")
    public void downloadBusinessListExcel(DeliverModel deliverModel, String type,
                                          HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        ResultListModel<DeliverModel> resultListModel = businessController.getDeliver(deliverModel, request.getSession());
        if (resultListModel != null && resultListModel.getData() != null) {
            for (DeliverModel temp : resultListModel.getData()) {
                ResultListModel<DeliverGoodsModel> goodsModelResultListModel = deliverGoodsController.getGoodsDeliverById(temp.getDeliverId(), request.getSession());
                if (goodsModelResultListModel != null && goodsModelResultListModel.getData() != null) {
                    String goodsModelString = "";
                    for (DeliverGoodsModel temp1 : goodsModelResultListModel.getData()) {
                        goodsModelString += temp1.getSnCode() + ":" + (temp1.getGoodsAmount() == null ? "" : temp1.getGoodsAmount()) + ";";
                    }
                    temp.setGoodsModelString(goodsModelString);
                }
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        //给map填充数据
        map.put("businessModelList", resultListModel.getData());
        //提示：在调用工具类生成Word文档之前应当检查所有字段是否完整
        //否则Freemarker的模板殷勤在处理时可能会因为找不到值而报错，这里暂时忽略这个步骤
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;

        try {
            //调用工具类WordGenerator的createDoc方法生成Word文档
            OutPutExcel outPutExcel = new OutPutExcel();
            file = outPutExcel.createExcel(type, map);
            fin = new FileInputStream(file);

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msexcel");
            response.addHeader("Content-Disposition", "attachment;filename=businessList.xls");

            out = response.getOutputStream();
            byte[] buffer = new byte[1024];//缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fin != null) fin.close();
            if (out != null) out.close();
            if (file != null) file.delete(); // 删除临时文件
        }
    }

    /**
     * 导出安装记录EXCEL
     *
     * @param goodsInstallModel
     * @param type
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/downloadInstallListExcel")
    public void downloadInstallListExcel(GoodsInstallModel goodsInstallModel, String type,
                                         HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        ResultListModel<GoodsInstallModel> resultListModel = installController.getGoodsInstall(goodsInstallModel, request.getSession());
        Map<String, Object> map = new HashMap<String, Object>();
        //给map填充数据
        map.put("installModelList", resultListModel.getData());
        //提示：在调用工具类生成Word文档之前应当检查所有字段是否完整
        //否则Freemarker的模板殷勤在处理时可能会因为找不到值而报错，这里暂时忽略这个步骤
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;

        try {
            //调用工具类WordGenerator的createDoc方法生成Word文档
            OutPutExcel outPutExcel = new OutPutExcel();
            file = outPutExcel.createExcel(type, map);
            fin = new FileInputStream(file);

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msexcel");
            response.addHeader("Content-Disposition", "attachment;filename=installList.xls");

            out = response.getOutputStream();
            byte[] buffer = new byte[1024];//缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fin != null) fin.close();
            if (out != null) out.close();
            if (file != null) file.delete(); // 删除临时文件
        }
    }

    /**
     * 导出维修记录EXCEL
     *
     * @param goodsRepairModel
     * @param type
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/downloadRepairListExcel")
    public void downloadRepairListExcel(GoodsRepairModel goodsRepairModel, String type,
                                        HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        ResultListModel<GoodsRepairModel> resultListModel = repairController.getGoodsRepair(goodsRepairModel, request.getSession());
        Map<String, Object> map = new HashMap<String, Object>();
        //给map填充数据
        map.put("repairModelList", resultListModel.getData());
        //提示：在调用工具类生成Word文档之前应当检查所有字段是否完整
        //否则Freemarker的模板殷勤在处理时可能会因为找不到值而报错，这里暂时忽略这个步骤
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;

        try {
            //调用工具类WordGenerator的createDoc方法生成Word文档
            OutPutExcel outPutExcel = new OutPutExcel();
            file = outPutExcel.createExcel(type, map);
            fin = new FileInputStream(file);

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msexcel");
            response.addHeader("Content-Disposition", "attachment;filename=repairList.xls");

            out = response.getOutputStream();
            byte[] buffer = new byte[1024];//缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fin != null) fin.close();
            if (out != null) out.close();
            if (file != null) file.delete(); // 删除临时文件
        }
    }

    /**
     * 导出组装记录EXCEL
     *
     * @param assembleModel
     * @param type
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/downloadAssembleListExcel")
    public void downloadAssembleListExcel(AssembleModel assembleModel, String type,
                                          HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        ResultListModel<AssembleModel> resultListModel = assembleController.getAssemble(assembleModel, request.getSession());
        if (resultListModel != null && resultListModel.getData() != null) {
            for (AssembleModel temp : resultListModel.getData()) {
                ResultListModel<AssemblePartModel> partsModelResultListModel = assemblePartController.getPartsByAssembleId(temp.getAssembleId(), request.getSession());
                if (partsModelResultListModel != null && partsModelResultListModel.getData() != null) {
                    String partsModelString = "";
                    for (AssemblePartModel temp1 : partsModelResultListModel.getData()) {
                        partsModelString += temp1.getPartTypeName() + ":" + (temp1.getPartNo() == null ? "" : temp1.getPartNo()) + ";";
                    }
                    temp.setPartsModelString(partsModelString);
                }
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        //给map填充数据
        map.put("assembleModelList", resultListModel.getData());
        //提示：在调用工具类生成Word文档之前应当检查所有字段是否完整
        //否则Freemarker的模板殷勤在处理时可能会因为找不到值而报错，这里暂时忽略这个步骤
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;

        try {
            //调用工具类WordGenerator的createDoc方法生成Word文档
            OutPutExcel outPutExcel = new OutPutExcel();
            file = outPutExcel.createExcel(type, map);
            fin = new FileInputStream(file);

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msexcel");
            response.addHeader("Content-Disposition", "attachment;filename=assembleList.xls");

            out = response.getOutputStream();
            byte[] buffer = new byte[1024];//缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fin != null) fin.close();
            if (out != null) out.close();
            if (file != null) file.delete(); // 删除临时文件
        }
    }

}
