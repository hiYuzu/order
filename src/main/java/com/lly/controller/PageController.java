package com.lly.controller;

import com.lly.model.PageModel;
import com.lly.model.ResultListModel;
import com.lly.pojo.PagePojo;
import com.lly.service.IAuthoritySetService;
import com.lly.service.IPageService;
import com.lly.util.DefaultParam;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: WangLei
 * @Description: 系统页面控制器
 * @Date: Create in 2018/3/8 11:56
 * @Modify by WangLei
 */
@Controller
@RequestMapping("/PageController")
public class PageController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "PageController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(PageController.class);

    @Resource
    private IPageService pageService;

    @Resource
    private IAuthoritySetService authoritySetService;

    /**
     * 获取系统页面信息
     * @param pageModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/getPage",method={RequestMethod.POST})
    @ResponseBody
    public ResultListModel<PageModel> getPage(String userType,PageModel pageModel, HttpSession httpSession){
        ResultListModel<PageModel> resultListModel = new ResultListModel<PageModel>();
        List<PageModel> pageModelList = new ArrayList<PageModel>();
        List<PagePojo> pagePojoList;
        PagePojo pagePojo = convertPagePojo(pageModel,httpSession);
        pagePojoList = pageService.getPage(pagePojo);
        if(pagePojoList != null && pagePojoList.size()>0){
            for (PagePojo temp : pagePojoList) {
                PageModel pageModelTemp = convertPageModel(temp);
                if (pageModelTemp != null) {
                    if(userType != null && !userType.isEmpty()){
                        if(authoritySetService.haveAuthority(userType,pageModelTemp.getPageCode())>0){
                            pageModelTemp.setPageChecked(true);
                        }else{
                            pageModelTemp.setPageChecked(false);
                        }
                    }
                    pageModelList.add(pageModelTemp);
                }
            }
        }
        resultListModel.setData(pageModelList);
        resultListModel.setCount(pageModelList.size());
        resultListModel.setCode(DefaultParam.liSelectOk);
        return resultListModel;
    }

    /**
     * 将PagePojo转换为PageModel
     * @param pagePojo
     * @return
     */
    public PageModel convertPageModel(PagePojo pagePojo){
        PageModel pageModel = new PageModel();
        if(pagePojo != null){
            pageModel.setPageId(String.valueOf(pagePojo.getPageId()));
            pageModel.setPageCode(pagePojo.getPageCode());
            pageModel.setPageUrl(pagePojo.getPageUrl());
            pageModel.setPageNameEn(pagePojo.getPageNameEn());
            pageModel.setPageNameCn(pagePojo.getPageNameCn());
            pageModel.setPageRemark(pagePojo.getPageRemark());
        }
        return pageModel;
    }

    /**
     * 将PageModel转换为PagePojo
     * @param pageModel
     * @param httpSession
     * @return
     */
    public PagePojo convertPagePojo(PageModel pageModel, HttpSession httpSession){
        PagePojo pagePojo = new PagePojo();
        if(pageModel != null) {
            try {
                if (pageModel.getPageId() != null && !pageModel.getPageId().isEmpty()) {
                    pagePojo.setPageId(Integer.valueOf(pageModel.getPageId()));
                }
                pagePojo.setPageCode(pageModel.getPageCode());
                pagePojo.setPageUrl(pageModel.getPageUrl());
                pagePojo.setPageNameEn(pageModel.getPageNameEn());
                pagePojo.setPageNameCn(pageModel.getPageNameCn());
                pagePojo.setPageRemark(pageModel.getPageRemark());
                pagePojo.setRowCount(pageModel.getRowCount());
                pagePojo.setRowIndex(pageModel.getRowIndex());
            } catch (Exception e) {
                logger.error(LOG + "：将PageModel转换为PagePojo失败，信息为" + e.getMessage());
            }
        }
        return pagePojo;
    }

}
