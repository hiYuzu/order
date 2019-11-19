package com.lly.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.lly.dao.IFileOperateDao;
import com.lly.pojo.FilePojo;
import com.lly.service.IFileOperateService;
import com.lly.util.FunctionUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: WangLei
 * @Description: 文件操作服务接口实现类
 * @Date: Create in 2018/2/27 13:48
 * @Modify by WangLei
 */
@Service("fileOperateService")
@Transactional(rollbackFor = Exception.class)
public class FileOperateServiceImpl implements IFileOperateService {

	@Resource
	private IFileOperateDao fileOperateDao;
	
	@Override
	public int getInstallGoodsFileCount(String installId) {
		return fileOperateDao.getInstallGoodsFileCount(installId);
	}
	
	@Override
	public List<FilePojo> getInstallGoodsFile(String installId) {
		return fileOperateDao.getInstallGoodsFile(installId);
	}

	@Override
	public int insertInstallGoodsFile(FilePojo filePojo) throws Exception {
		int intResult = 0;
		if (filePojo != null && !filePojo.getFileName().isEmpty()) {
			//先删除相关数据
			fileOperateDao.deleteInstallGoodsFile(String.valueOf(filePojo.getGoodsInstallPojo().getInstallId()), filePojo.getFileName());
			intResult = fileOperateDao.insertInstallGoodsFile(filePojo);
		}
		return intResult;
	}

	@Override
	public int deleteInstallGoodsFile(String fileId) throws Exception {
		int intResult = 0;
		if (fileId != null && !fileId.isEmpty()) {
			FilePojo filePojo = fileOperateDao.getInstallGoodsFileSingle(fileId);
			if(filePojo != null && filePojo.getGoodsInstallPojo() != null){
				String installId = String.valueOf(filePojo.getGoodsInstallPojo().getInstallId());
				String fileName = filePojo.getFileName();
				String filePath = filePojo.getFilePath();
				intResult = fileOperateDao.deleteInstallGoodsFile(installId, fileName);
				if (intResult>0) {
					//删除
					if(filePath != null && !filePath.isEmpty() 
							&& fileName != null && !fileName.isEmpty()){
						FunctionUtil.deleteFile(filePojo.getFilePath()+"\\"+filePojo.getFileName());
					}
				}
			}
		}
		return intResult;
	}

}
