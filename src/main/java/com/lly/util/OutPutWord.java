package com.lly.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class OutPutWord {

    private Map<String,Template> getTemplateByType(String type){
    	String templateName = null;
		if(type != null && type.equals(DefaultParam.REPORT_DELIVER)){
			templateName = "deliverWord.ftl";
		}else if(type != null && type.equals(DefaultParam.REPORT_BUSINESS)){
			templateName = "businessWord.ftl";
		}else if(type != null && type.equals(DefaultParam.REPORT_INSTALL)){
			templateName = "installWord.ftl";
		}else if(type != null && type.equals(DefaultParam.REPORT_REPAIR)){
			templateName = "repairWord.ftl";
		}
    	Configuration configuration = new Configuration(Configuration.VERSION_2_3_25);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(OutPutWord.class, "/com/lly/eoms/templates");
//	        try {
//	            configuration.setDirectoryForTemplateLoading(new File("E:\\"));
//	        } catch (IOException e1) {      
//	            e1.printStackTrace();
//	        }
        Map<String,Template> allTemplate = new HashMap<String,Template>();
        try{
            allTemplate.put(type, configuration.getTemplate(templateName));
        }catch(IOException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return allTemplate;
    }

	public File createDoc(String type,Map<?,?> dataMap){
	    String name = "report"+(int)(Math.random()*100000)+".doc";
	    File f = new File(name);
	    Template t = getTemplateByType(type).get(type);
	    try{
	        //这个地方不能使用FileWriter因为需要指定编码类型否则声场的word文档会因为有无法识别的编码而无法打开
	        Writer w = new OutputStreamWriter(new FileOutputStream(f),"utf-8");
	        t.process(dataMap,w);
	        w.close();
	    }catch(Exception e){
	        e.printStackTrace();
	        throw new RuntimeException(e);
	    }
	    return f;
	}

}
