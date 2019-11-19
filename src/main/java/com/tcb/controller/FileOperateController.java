package com.tcb.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tcb.config.SystemConfig;
import com.tcb.model.FileModel;
import com.tcb.model.ResultListModel;
import com.tcb.model.ResultModel;
import com.tcb.model.UserModel;
import com.tcb.pojo.FilePojo;
import com.tcb.pojo.GoodsInstallPojo;
import com.tcb.service.IFileOperateService;
import com.tcb.util.DateUtil;
import com.tcb.util.DefaultParam;
import com.tcb.util.ZipUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

/**
 * @Author: WangLei
 * @Description: 文件操作控制器（查询、上传、下载）
 * @Date: Create in 2018/2/27 13:48
 * @Modify by WangLei
 */
@Controller
@RequestMapping("/FileOperateController")
public class FileOperateController {

	/**
	 * 日志输出标记
	 */
	private static final String LOG = "FileOperateController";
	/**
	 * 声明日志对象
	 */
	private static Logger logger = Logger.getLogger(FileOperateController.class);
	
	/**
	 * 声明gson对象
	 */
	private Gson gson = new Gson();
	
	/**
	 * 声明文件操作服务
	 */
	@Resource
	private IFileOperateService fileOperateService;

	@Resource
	private SystemConfig systemConfig;

	/**
	 * 获取上传文件信息
	 * @param installId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/queryFile", method = { RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public ResultListModel<FileModel> queryFile(@RequestParam("installId") String installId,HttpServletRequest request,HttpServletResponse response) {
		ResultListModel<FileModel> resultListModel = new ResultListModel<FileModel>();
		List<FileModel> fileModelList = new ArrayList<FileModel>();
		int count = fileOperateService.getInstallGoodsFileCount(installId);
		if (count > 0) {
			List<FilePojo> filePojoList = fileOperateService.getInstallGoodsFile(installId);
			for (FilePojo temp : filePojoList) {
				FileModel fileModel = ConvertFileModel(temp,request.getRequestURL().toString());
				if (fileModel != null) {
					fileModelList.add(fileModel);
				}
			}
			resultListModel.setData(fileModelList);
		}
		resultListModel.setCount(count);
		resultListModel.setCode(DefaultParam.liSelectOk);
		return resultListModel;
	}

	/**
	 * 查询上传文件个数
	 * @param installId
	 * @param httpsession
	 * @return
	 */
	@RequestMapping(value="/queryFileCount")
	public @ResponseBody String queryFileCount(@RequestParam("installId") String installId,HttpSession httpsession){
		int count = 0;
		Gson gson = new Gson();
		count = fileOperateService.getInstallGoodsFileCount(installId);
        return gson.toJson(count);
	}
	
   /**
     * 这里这里用的是MultipartFile[] myfiles参数,所以前台就要用<input type="file" name="myFiles"/>
     * 上传文件完毕后返回给前台[0`filepath],0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
     */
    @RequestMapping(value="/fileUpload")
	@ResponseBody
    public ResultModel fileUpload(@RequestParam("installId") String installId,@RequestParam("myFiles") MultipartFile[] myFiles,
						   HttpServletRequest request, HttpServletResponse response) throws IOException{
        ResultModel resultModel = new ResultModel();
    	//可以在上传文件的同时接收其它参数
        System.out.println("收到安装ID[" + installId + "]的文件上传请求");
        //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\文件夹中
        //这里实现文件上传操作用的是commons.io.FileUtils类,它会自动判断/upload是否存在,不存在会自动创建
        String realPath = request.getSession().getServletContext().getRealPath("/"+DefaultParam.UPLOAD_FOLDER);
        //设置响应给前台内容的数据格式
        response.setContentType("text/plain; charset=UTF-8");
        //上传文件的原名(即上传前的文件名字)
        String originalFilename = null;
        //如果只是上传一个文件,则只需要MultipartFile类型接收文件即可,而且无需显式指定@RequestParam注解
        //如果想上传多个文件,那么这里就要用MultipartFile[]类型来接收文件,并且要指定@RequestParam注解
        //上传多个文件时,前台表单中的所有<input type="file"/>的name都应该是myfiles,否则参数里的myfiles无法获取到所有上传的文件
        for(MultipartFile myfile : myFiles){
            if(myfile.isEmpty()){
				resultModel.setResult(false);
				resultModel.setDetail("请选择文件后上传");
                return resultModel;
            }else{
                originalFilename = installId+"-"+myfile.getOriginalFilename();
                System.out.println("文件名称: " + myfile.getOriginalFilename());
                System.out.println("文件长度: " + myfile.getSize());
                try {
                    //这里不必处理IO流关闭的问题,因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
                    //此处也可以使用Spring提供的MultipartFile.transferTo(File dest)方法实现文件的上传
                    FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, originalFilename));
                    //更新数据库记录
					FilePojo filePojo = new FilePojo();
					GoodsInstallPojo goodsInstallPojo = new GoodsInstallPojo();
					goodsInstallPojo.setInstallId(Long.valueOf(installId));
					filePojo.setGoodsInstallPojo(goodsInstallPojo);
					filePojo.setFileName(originalFilename);
					filePojo.setFilePath(realPath);
					filePojo.setUploadTime(DateUtil.TimestampToString(DateUtil.GetSystemDateTime(0),DateUtil.DATA_TIME_SECOND));
					UserModel loginUser = (UserModel) request.getSession().getAttribute(request.getSession().getId());
					String optUserId = loginUser.getUserId();
					filePojo.setOptUserId(Integer.valueOf(optUserId));
					fileOperateService.insertInstallGoodsFile(filePojo);
                } catch (IOException e) {
                    logger.error(LOG+"：文件[" + originalFilename + "]上传失败,堆栈轨迹如下");
                    e.printStackTrace();
					resultModel.setResult(false);
					resultModel.setDetail("文件上传失败，请重试！");
					return resultModel;
                } catch (Exception e) {
                	logger.error(LOG+"：文件[" + originalFilename + "]上传失败,堆栈轨迹如下");
                    e.printStackTrace();
					resultModel.setResult(false);
					resultModel.setDetail("文件上传失败，请重试！");
					return resultModel;
				}
            }
        }
        System.out.println("文件位置: " + request.getContextPath() + "/upload/" + originalFilename);
		resultModel.setResult(true);
		resultModel.setDetail("文件上传成功！");
		return resultModel;
    }

	/**
	 * 打包压缩下载文件
	 * @param installId
	 * @param response
	 * @throws IOException
	 */
    @RequestMapping(value = "/downLoadZipFile")
    public void downLoadZipFile(@RequestParam("installId") String installId,HttpServletResponse response) throws IOException{
        String zipName = "order_files.zip";
        response.setContentType("APPLICATION/OCTET-STREAM");  
        response.setHeader("Content-Disposition","attachment; filename="+zipName);
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
        try {
            if(installId != null){
				//查询任务上传文件
				List<FilePojo> fileList = fileOperateService.getInstallGoodsFile(installId);
				if(fileList != null && fileList.size()>0){
					for(Iterator<FilePojo> it = fileList.iterator();it.hasNext();){
						FilePojo file = it.next();
						ZipUtil.doCompress(file.getFilePath()+"\\"+file.getFileName(), out);
					}
					response.flushBuffer();
				}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            out.close();
        }
    }

	/**
	 * 删除任务上传文件
	 * @param fileId
	 * @return
	 */
	@RequestMapping(value = "/deleteInstallGoodsFile", method = { RequestMethod.POST })
    public @ResponseBody ResultModel deleteInstallGoodsFile(String fileId){
		ResultModel resultModel = new ResultModel();
		if (fileId != null && !fileId.isEmpty()) {
			try {
				int result = fileOperateService.deleteInstallGoodsFile(fileId);
				if(result>0){
					resultModel.setResult(true);
					resultModel.setDetail("删除文件成功！");
				}else{
					resultModel.setResult(false);
					resultModel.setDetail("删除文件失败！");
				}
			} catch (Exception e) {
				resultModel.setResult(false);
				resultModel.setDetail("删除文件失败！");
				logger.error(LOG + "：删除文件失败，信息为:" + e.getMessage());
			}
		} else {
			resultModel.setResult(false);
			resultModel.setDetail("删除文件失败，错误原因：服务器未接收到删除数据！");
		}
		return resultModel;
    }

	/**
	 * 将Object转换为FileModel
	 * @param object
	 * @param requestUrl
	 * @return
	 */
    public FileModel ConvertFileModel(Object object,String requestUrl){
    	FileModel fileModel = new FileModel();
    	if(object != null){
    		if(object.getClass().equals(FilePojo.class)){
				FilePojo filePojo = (FilePojo)object;
    			fileModel.setFileId(String.valueOf(filePojo.getFileId()));
    			String fileName = filePojo.getFileName();
    			fileModel.setFileName(fileName);
    			fileModel.setFilePath(filePojo.getFilePath());
				fileModel.setUploadTime(filePojo.getUploadTime());
    			if(fileName != null && fileName.contains(".")){
    				fileModel.setInstallId(String.valueOf(filePojo.getGoodsInstallPojo().getInstallId()));
    				//设置文件相对路径
    				String sysUrl = systemConfig.getSysUrl();
    				if(sysUrl != null && !sysUrl.isEmpty()){
    					requestUrl = sysUrl;
    				}else{
        				if(requestUrl != null && requestUrl.contains("/")){
        					requestUrl = requestUrl.substring(0,requestUrl.indexOf("/", 8)+1);
        				}
    				}
    				String relativePath = requestUrl+DefaultParam.UPLOAD_FOLDER+"/"+fileName;
    				fileModel.setFileRelativePath(relativePath);
    			}
    		}
    	}
    	return fileModel;
    }

}
