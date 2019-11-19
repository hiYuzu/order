package com.lly.controller;

import com.lly.model.NewsModel;
import com.lly.model.ResultListModel;
import com.lly.model.ResultModel;
import com.lly.model.UserModel;
import com.lly.pojo.NewsPojo;
import com.lly.service.INewsService;
import com.lly.util.DateUtil;
import com.lly.util.DefaultParam;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: WangLei
 * @Description: 公告控制器
 * @Date: Create in 2018/3/12 15:51
 * @Modify by WangLei
 */
@Controller
@RequestMapping("/NewsController")
public class NewsController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "NewsController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(NewsController.class);

    @Resource
    private INewsService newsService;

    /**
     * 获取公告
     * @param newsModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/getNews",method={RequestMethod.POST})
    @ResponseBody
    public ResultListModel<NewsModel> getNews(NewsModel newsModel, HttpSession httpSession){
        ResultListModel<NewsModel> resultListModel = new ResultListModel<NewsModel>();
        List<NewsModel> newsModelList = new ArrayList<NewsModel>();
        List<NewsPojo> newsPojoList;
        NewsPojo newsPojo = convertNewsPojo(newsModel,httpSession);
        int count = newsService.getNewsCount(newsPojo);
        if (count > 0) {
            newsPojoList = newsService.getNews(newsPojo);
            for (NewsPojo temp : newsPojoList) {
                NewsModel newsModelTemp = convertNewsModel(temp);
                if (newsModelTemp != null) {
                    newsModelList.add(newsModelTemp);
                }
            }
            resultListModel.setData(newsModelList);
        }
        resultListModel.setCount(count);
        resultListModel.setCode(DefaultParam.liSelectOk);
        return resultListModel;
    }

    /**
     * 新增公告
     * @param newsModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/insertNews", method = { RequestMethod.POST })
    @ResponseBody
    public ResultModel insertNews(NewsModel newsModel, HttpSession httpSession) {
        ResultModel resultModel = new ResultModel();
        if (newsModel != null) {
            try {
                NewsPojo newsPojo = convertNewsPojo(newsModel,httpSession);
                int intResult = newsService.insertNews(newsPojo);
                if (intResult > 0) {
                    resultModel.setResult(true);
                    resultModel.setDetail("新增公告成功！");
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("新增公告失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("新增公告失败！");
                logger.error(LOG + "：新增公告失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("没有可以操作的数据！");
        }
        return resultModel;
    }

    /**
     * 更新公告
     * @param newsModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/updateNews", method = { RequestMethod.POST })
    @ResponseBody
    public ResultModel updateNews(NewsModel newsModel, HttpSession httpSession) {
        ResultModel resultModel = new ResultModel();
        if (newsModel != null) {
            try {
                NewsPojo newsPojo = convertNewsPojo(newsModel,httpSession);
                int intResult = newsService.updateNews(newsPojo);
                if (intResult > 0) {
                    resultModel.setResult(true);
                    resultModel.setDetail("更新公告成功！");
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("更新公告失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("更新公告失败！");
                logger.error(LOG + "：更新公告失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("没有可以操作的数据！");
        }
        return resultModel;
    }

    /**
     * 删除公告
     * @param list
     * @return
     */
    @RequestMapping(value = "/deleteNews", method = { RequestMethod.POST })
    public @ResponseBody ResultModel deleteNews(@RequestParam(value = "list[]") List<String> list) {
        ResultModel resultModel = new ResultModel();
        if (list != null && list.size() > 0) {
            try {
                int intResult = newsService.deleteNews(list);
                if (intResult > 0) {
                    resultModel.setResult(true);
                    resultModel.setDetail("删除公告成功！");
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("删除公告失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("删除公告失败！");
                logger.error(LOG + "：删除公告失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("删除公告失败，错误原因：服务器未接收到删除数据！");
        }
        return resultModel;
    }

    /**
     * 将NewsPojo转换为NewsModel
     * @param newsPojo
     * @return
     */
    public NewsModel convertNewsModel(NewsPojo newsPojo){
        NewsModel newsModel = new NewsModel();
        if(newsPojo != null){
            newsModel.setNewsId(String.valueOf(newsPojo.getNewsId()));
            newsModel.setNewsTitle(newsPojo.getNewsTitle());
            newsModel.setNewsContent(newsPojo.getNewsContent());
            newsModel.setNewsAuthor(newsPojo.getNewsAuthor());
            newsModel.setNewsShow(newsPojo.getNewsShow());
            newsModel.setNewsShow(newsPojo.getNewsShow());
            if(newsPojo.getNewsShow()){
                newsModel.setNewsShowName("展示");
            }else{
                newsModel.setNewsShowName("不展示");
            }
            newsModel.setShowTime(newsPojo.getShowTime());
            newsModel.setOptUserId(String.valueOf(newsPojo.getOptUserId()));
            newsModel.setOptUserName(newsPojo.getOptUserName());
            newsModel.setOptTime(DateUtil.TimestampToString(newsPojo.getOptTime(),DateUtil.DATA_TIME_SECOND));
        }
        return newsModel;
    }

    /**
     * 将NewsModel转换为NewsPojo
     * @param newsModel
     * @param httpSession
     * @return
     */
    public NewsPojo convertNewsPojo(NewsModel newsModel, HttpSession httpSession){
        NewsPojo newsPojo = new NewsPojo();
        if(newsModel != null) {
            try {
                if (newsModel.getNewsId() != null && !newsModel.getNewsId().isEmpty()) {
                    newsPojo.setNewsId(Long.valueOf(newsModel.getNewsId()));
                }
                newsPojo.setNewsTitle(newsModel.getNewsTitle());
                newsPojo.setNewsContent(newsModel.getNewsContent());
                newsPojo.setNewsAuthor(newsModel.getNewsAuthor());
                newsPojo.setNewsShow(newsModel.getNewsShow());
                newsPojo.setShowTime(newsModel.getShowTime());
                UserModel loginUser = (UserModel) httpSession.getAttribute(httpSession.getId());
                if (loginUser != null && loginUser.getUserId() != null && !loginUser.getUserId().isEmpty()) {
                    newsPojo.setOptUserId(Integer.valueOf(loginUser.getUserId()));
                }
                if(newsModel.getOptTime() != null && !newsModel.getOptTime().isEmpty()){
                    newsPojo.setOptTime(DateUtil.StringToTimestamp(newsModel.getOptTime(), DateUtil.DATA_TIME_SECOND));
                }else{
                    newsPojo.setOptTime(DateUtil.GetSystemDateTime(0));
                }
                newsPojo.setRowCount(newsModel.getRowCount());
                newsPojo.setRowIndex(newsModel.getRowIndex());
            } catch (Exception e) {
                logger.error(LOG + "：将NewsModel转换成NewsPojo失败，信息为" + e.getMessage());
            }
        }
        return newsPojo;
    }


}
