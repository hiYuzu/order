package com.lly.config;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 解析XML系统配置
 */
@Component
public class SystemConfig implements ApplicationListener<ContextRefreshedEvent> {

	/**
	 * 声明日志对象
	 */
	private Logger logger = LogManager.getLogger(SystemConfig.class);

	private String rootUser;
	private String sysUrl;

	public String getRootUser() {
		return rootUser;
	}

	public void setRootUser(String rootUser) {
		this.rootUser = rootUser;
	}

	public String getSysUrl() {
		return sysUrl;
	}

	public void setSysUrl(String sysUrl) {
		this.sysUrl = sysUrl;
	}

	public void init() {
		InputStream fileURL = null;
		try {
			fileURL = getClass().getClassLoader().getResourceAsStream("config/system-config.xml");
			parseXML(fileURL);
			fileURL.close();
		} catch (IOException ex) {
			logger.error("解析system-config.xml配置信息失败，错误信息：" + ex.toString());
		} catch (Exception ex) {
			logger.error("解析system-config.xml配置信息失败，错误信息：" + ex.toString());
		} finally {
			if (fileURL != null) {
				try {
					fileURL.close();
				} catch (IOException ex) {
					logger.error("关闭dom4j-config.xml失败，错误信息：" + ex.toString());
				}
			}
		}

	}

	private void parseXML(InputStream file) throws Exception {
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(file);
		Element root = document.getRootElement();

		// 解析XML
		try {
			Element sysConfig = root.element("System-Config");
			setRootUser(sysConfig.elementText("RootUser"));
			setSysUrl(sysConfig.elementText("SysUrl"));

		} catch (Exception ex) {
			logger.error("解析XML系统配置失败，错误信息：" + ex.toString());
		}

	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			logger.info("开始解析xml系统配置");
			init();
		}
	}
}
