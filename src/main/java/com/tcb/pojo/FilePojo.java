package com.tcb.pojo;

/**
 * @Author: WangLei
 * @Description: 文件操作POJO
 * @Date: Create in 2018/2/27 10:17
 * @Modify by WangLei
 */
public class FilePojo extends BasePojo {

	private long fileId;
	private GoodsInstallPojo goodsInstallPojo;
	private String fileName;
	private String filePath;
	private String uploadTime;

	public long getFileId() {
		return fileId;
	}

	public void setFileId(long fileId) {
		this.fileId = fileId;
	}

	public GoodsInstallPojo getGoodsInstallPojo() {
		return goodsInstallPojo;
	}

	public void setGoodsInstallPojo(GoodsInstallPojo goodsInstallPojo) {
		this.goodsInstallPojo = goodsInstallPojo;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

}
