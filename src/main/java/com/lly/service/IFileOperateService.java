package com.lly.service;

import java.util.List;
import com.lly.pojo.FilePojo;

/**
 * @Author: WangLei
 * @Description: 文件操作服务接口
 * @Date: Create in 2018/2/27 13:48
 * @Modify by WangLei
 */
public interface IFileOperateService {

	/**
	 * 获取货物安装上传文件个数
	 * @param installId
	 * @return
	 */
	int getInstallGoodsFileCount(String installId);

	/**
	 * 获取货物安装上传文件
	 * @param installId
	 * @return
	 */
	List<FilePojo> getInstallGoodsFile(String installId);

	/**
	 * 新增安装货物上传文件
	 * @param filePojo
	 * @return
	 */
	int insertInstallGoodsFile(FilePojo filePojo) throws Exception;

	/**
	 * 删除安装货物上传文件
	 * @param fileId
	 * @return
	 */
	int deleteInstallGoodsFile(String fileId) throws Exception;
	
}
