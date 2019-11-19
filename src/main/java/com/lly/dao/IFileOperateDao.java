package com.lly.dao;

import java.util.List;
import com.lly.pojo.FilePojo;
import org.apache.ibatis.annotations.Param;

public interface IFileOperateDao {

	/**
	 * 获取货物安装上传文件个数
	 * @param installId
	 * @return
	 */
	int getInstallGoodsFileCount(@Param("installId") String installId);

	/**
	 * 获取货物安装上传文件
	 * @param installId
	 * @return
	 */
	List<FilePojo> getInstallGoodsFile(@Param("installId") String installId);

	/**
	 * 获取货物安装上传文件(单个)
	 * @param fileId
	 * @return
	 */
	FilePojo getInstallGoodsFileSingle(@Param("fileId") String fileId);

	/**
	 * 新增安装货物上传文件
	 * @param filePojo
	 * @return
	 */
	int insertInstallGoodsFile(@Param("filePojo") FilePojo filePojo);

	/**
	 * 删除安装货物上传文件
	 * @param installId
	 * @param fileName
	 * @return
	 */
	int deleteInstallGoodsFile(@Param("installId") String installId,@Param("fileName") String fileName);
	
}
