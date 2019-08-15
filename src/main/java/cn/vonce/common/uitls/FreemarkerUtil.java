package cn.vonce.common.uitls;

import freemarker.core.ParseException;
import freemarker.template.*;

import java.io.*;
import java.util.Map;

/**
 * FreemarkerUtil 工具类
 * 
 * @author Jovi
 * @email 766255988@qq.com
 * @version 1.0
 * @date 2018年2月26日上午11:29:14
 */
public class FreemarkerUtil {

	private static FreemarkerUtil fk;
	private static Configuration cfg;

	/**
	 * 获取实例
	 * 
	 * @author Jovi
	 * @date 2018年2月26日上午11:26:35
	 * @param freemarkerVersionNo
	 * @param templatePath
	 * @return
	 */
	public static FreemarkerUtil getInstance(String freemarkerVersionNo, String templatePath) {
		if (null == fk) {
			cfg = new Configuration(new Version(freemarkerVersionNo));
			cfg.setClassForTemplateLoading(FreemarkerUtil.class, templatePath);
			cfg.setDefaultEncoding("UTF-8");
			fk = new FreemarkerUtil();
		}
		return fk;
	}

	/**
	 * 根据模版名称加载对应模版
	 * 
	 * @param templateName
	 * 
	 * @return
	 */
	private Template getTemplate(String templateName) {
		try {
			return cfg.getTemplate(templateName);
		} catch (TemplateNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedTemplateNameException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 打印到控制台(测试用)
	 * 
	 * @author Jovi
	 * @date 2018年2月26日上午11:27:53
	 * @param dataModel
	 * @param templateName
	 */
	public void print(Map<String, Object> dataModel, String templateName) {
		try {
			this.getTemplate(templateName).process(dataModel, new PrintWriter(System.out));
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 输出文件
	 * 
	 * @param dataModel
	 *            数据模型
	 * @param templateName
	 *            输出模版
	 * @param filePath
	 *            输出文件路径
	 */
	public void fprint(Map<String, Object> dataModel, String templateName, String filePath) {
		try {
			this.getTemplate(templateName).process(dataModel, new FileWriter(new File(filePath)));
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writer(Map<String, Object> dataModel, String templateName, Writer writer) {
		try {
			this.getTemplate(templateName).process(dataModel, writer);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
