package com.lly.util;

import java.io.File;

/**
 * @Author: WangLei
 * @Description: 常用静态方法
 * @Date: Create in 2018/2/27 13:48
 * @Modify by WangLei
 */
public class FunctionUtil {

	/**
	 * 删除单个文件
	 * @param fileName
	 * @return
	 */
	public static boolean deleteFile(String fileName) {
    	File file = new File(fileName);
    	//如果文件路径所对应的文件存在，并且是一个文件，则直接删除
    	if (file.exists() && file.isFile()) {
    		if (file.delete()) {
    			System.out.println("删除单个文件" + fileName + "成功！");
    			return true;
    		} else {
    			System.out.println("删除单个文件" + fileName + "失败！");
    			return false;
    		}
    	}else{
    		System.out.println("删除单个文件失败：" + fileName + "不存在！");
    		return false;
    	}
    }
    
}
