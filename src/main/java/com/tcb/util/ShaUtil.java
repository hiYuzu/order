package com.tcb.util;

import java.security.MessageDigest;

import org.apache.log4j.Logger;

/**
 * SHAA加密类
 */
public class ShaUtil {

	/**
	 * 日志输出标记
	 */
	private static final String LOG = "ShaUtil";

	/**
	 * 声明日志对象
	 */
	private static Logger logger = Logger.getLogger(ShaUtil.class);

	/**
	 * 禁止被实例化
	 */
	private ShaUtil(){}

	/**
	 * SHA加密 生成40位SHA码
	 * @param inStr
	 * @return
	 * @throws Exception
	 */
	public static String shaEncode(String inStr) throws Exception {
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance("SHA");
		} catch (Exception e) {
			logger.error(LOG + "：加密失败，信息为：" + e.getMessage());
			e.printStackTrace();
			return "";
		}
		byte[] byteArray = inStr.getBytes("UTF-8");
		byte[] md5Bytes = sha.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
}
