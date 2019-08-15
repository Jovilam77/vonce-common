package cn.vonce.common.uitls;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.ByteArrayOutputStream;
import java.util.*;

/**
 * Xml 工具类
 *
 * @author Jovi
 * @version 1.0
 * @email 766255988@qq.com
 * @date 2018年3月16日下午4:50:22
 */
public class XmlConverUtil {

    /**
     * Map转XML
     *
     * @param map
     * @return
     * @author Jovi
     * @date 2018年3月16日下午4:21:08
     */
    public static String map2Xml(Map<String, ?> map) {
        return map2Xml(map, map.getClass().getSimpleName());
    }

    /**
     * Map转XML
     *
     * @param map
     * @param elementName 根元素名称
     * @return
     * @author Jovi
     * @date 2018年3月16日下午4:17:53
     */
    public static String map2Xml(Map<String, ?> map, String elementName) {
        Document document = DocumentHelper.createDocument();
        if (elementName == null || elementName.trim().equals("")) {
            elementName = "xml";
        }
        Element nodeElement = document.addElement(elementName);
        for (String key : map.keySet()) {
            Element keyElement = nodeElement.addElement(key);
            keyElement.setText((String) map.get(key));
        }
        return doc2Xml(document);
    }

    /**
     * Map转XML map key 作为Element 名称 Attribute 为key 的实际值
     *
     * @param map
     * @return
     * @author Jovi
     * @date 2018年3月16日下午4:48:37
     */
    public static String map2XmlKey(Map<String, ?> map) {
        return map2XmlKey(map, map.getClass().getSimpleName());
    }

    /**
     * Map转XML map key 作为Element 名称 Attribute 为key 的实际值
     *
     * @param map
     * @param elementName 根元素名称
     * @return
     * @author Jovi
     * @date 2018年3月16日下午4:18:13
     */
    public static String map2XmlKey(Map<String, ?> map, String elementName) {
        Document document = DocumentHelper.createDocument();
        if (elementName == null || elementName.trim().equals("")) {
            elementName = "xml";
        }
        Element nodeElement = document.addElement(elementName);
        for (String key : map.keySet()) {
            Element keyElement = nodeElement.addElement("key");
            keyElement.addAttribute("label", String.valueOf(key));
            keyElement.setText(String.valueOf(map.get(key)));
        }
        return doc2Xml(document);
    }

    /**
     * List 转 Xml
     *
     * @param list
     * @return
     * @throws Exception
     * @author Jovi
     * @date 2018年3月16日下午4:46:52
     */
    public static String list2Xml(List<?> list) {
        return list2Xml(list, list.getClass().getSimpleName(), null);
    }

    /**
     * List 转 Xml
     *
     * @param list
     * @param rootElementName 根元素名称
     * @param elementName     子元素名称
     * @return
     * @throws Exception
     * @author Jovi
     * @date 2018年3月16日下午4:47:01
     */
    @SuppressWarnings("unchecked")
    public static String list2Xml(List<?> list, String rootElementName, String elementName) {
        Document document = DocumentHelper.createDocument();
        Element nodesElement = document.addElement(rootElementName);
        int i = 0;
        for (Object o : list) {
            if (elementName == null || elementName.equals("")) {
                elementName = o.getClass().getSimpleName();
            }
            Element nodeElement = nodesElement.addElement(elementName);
            if (o instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) o;
                for (String key : map.keySet()) {
                    Element keyElement = nodeElement.addElement(key);
                    keyElement.setText((String) map.get(key));
                }
            } else {
                Element keyElement = nodeElement.addElement(String.valueOf(i));
                keyElement.setText(String.valueOf(o));
            }
            i++;
        }
        return doc2Xml(document);
    }

    /**
     * List 转 Xml key 作为Element 名称 Attribute 为key 的实际值
     *
     * @param list
     * @return
     * @throws Exception
     * @author Jovi
     * @date 2018年3月16日下午4:46:15
     */
    public static String list2XmlKey(List<?> list) {
        return list2XmlKey(list, list.getClass().getSimpleName(), null);
    }

    /**
     * List 转 Xml key 作为Element 名称 Attribute 为key 的实际值
     *
     * @param list
     * @param rootElementName 根元素名称
     * @param elementName     子元素名称
     * @return
     * @throws Exception
     * @author Jovi
     * @date 2018年3月16日下午4:45:37
     */
    @SuppressWarnings("unchecked")
    public static String list2XmlKey(List<?> list, String rootElementName, String elementName) {
        Document document = DocumentHelper.createDocument();
        Element nodesElement = document.addElement(rootElementName);
        int i = 0;
        for (Object o : list) {
            if (elementName == null || elementName.equals("")) {
                elementName = o.getClass().getSimpleName();
            }
            Element nodeElement = nodesElement.addElement(elementName);
            if (o instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) o;
                for (String key : map.keySet()) {
                    Element keyElement = nodeElement.addElement("key");
                    keyElement.addAttribute("label", String.valueOf(key));
                    keyElement.setText((String) map.get(key));
                }
            } else {
                Element keyElement = nodeElement.addElement("key");
                keyElement.addAttribute("label", String.valueOf(i));
                keyElement.setText(String.valueOf(o));
            }
            i++;
        }
        return doc2Xml(document);
    }

    /**
     * Xml 转 List
     *
     * @param xml
     * @return
     * @author Jovi
     * @date 2018年3月16日下午2:58:17
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> xml2List(String xml) {
        try {
            List<Map<String, Object>> list = new ArrayList<>();
            Document document = DocumentHelper.parseText(xml);
            Element nodesElement = document.getRootElement();
            List<Element> nodes = nodesElement.elements();
            for (Iterator<?> its = nodes.iterator(); its.hasNext(); ) {
                Element nodeElement = (Element) its.next();
                Map<String, Object> map = xml2Map(nodeElement.asXML());
                if (map != null && map.size() > 0) {
                    list.add(map);
                    map = null;
                }
            }
            nodes = null;
            nodesElement = null;
            document = null;
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Document 转 xml
     *
     * @param document
     * @return
     * @author Jovi
     * @date 2018年3月16日下午2:58:04
     */
    public static String doc2Xml(Document document) {
        String s = "";
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            OutputFormat format = new OutputFormat(" ", true, "UTF-8");
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
            s = out.toString("UTF-8");
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return s;
    }

    /**
     * xml 字符串 转 json 字符串
     *
     * @param xml
     * @return
     * @throws DocumentException
     * @author Jovi
     * @date 2018年3月16日下午3:53:15
     */
    public static String xml2Json(String xml) throws DocumentException {
        Document document = DocumentHelper.parseText(xml);
        return FastJsonUtil.obj2Json(element2Map(document.getRootElement()));
    }

    /**
     * xml 字符串 转 Map
     *
     * @param xml
     * @return
     * @throws DocumentException
     * @author Jovi
     * @date 2018年3月16日下午3:53:44
     */
    public static Map<String, Object> xml2Map(String xml) throws DocumentException {
        Document document = DocumentHelper.parseText(xml);
        return element2Map(document.getRootElement());
    }

    /**
     * org.dom4j.Element 转 Map
     *
     * @param element
     * @return
     * @throws DocumentException
     * @author Jovi
     * @date 2018年3月16日下午3:53:55
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> element2Map(Element element) {
        Map<String, Object> map = new HashMap<>();
        // 当前节点的名称、文本内容和属性
        List<Attribute> listAttr = element.attributes();// 当前节点的所有属性的list
        for (Attribute attr : listAttr) {// 遍历当前节点的所有属性
            map.put(attr.getName(), attr.getValue());
        }
        // 递归遍历当前节点所有的子节点
        List<Element> listElement = element.elements();// 所有一级子节点的list
        if (!listElement.isEmpty()) {
            for (Element e : listElement) {// 遍历所有一级子节点
                if (e.attributes().isEmpty() && e.elements().isEmpty()) // 判断一级节点是否有属性和子节点
                    map.put(e.getName(), e.getTextTrim());// 沒有则将当前节点作为上级节点的属性对待
                else {
                    if (!map.containsKey(e.getName())) // 判断父节点是否存在该一级节点名称的属性
                        map.put(e.getName(), new ArrayList<Map<String, Object>>());// 没有则创建
                    ((ArrayList<Map<String, Object>>) map.get(e.getName())).add(element2Map(e));// 将该一级节点放入该节点名称的属性对应的值中
                }
            }
        }
        return map;
    }

    /**
     * xml 转 json 风格map对象
     *
     * @param xml xml字符串
     * @return
     * @author Jovi
     * @date 2019年7月10日下午15:54:22
     */
    public static Map<String, Object> xml2JsonStyleMap(String xml) throws DocumentException {
        Document document = DocumentHelper.parseText(xml);
        return xml2JsonStyleMap(document.getRootElement());
    }

    /**
     * xml 转 json 风格map对象
     *
     * @param element 节点
     * @return
     * @author Jovi
     * @date 2019年7月10日下午15:54:22
     */
    public static Map<String, Object> xml2JsonStyleMap(Element element) {
        Map<String, Object> rootMap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        if (!element.attributes().isEmpty()) {
            List<Attribute> attributeList = element.attributes();
            for (Attribute attribute : attributeList) {
                map.put(attribute.getQualifiedName(), attribute.getValue());
            }
        }
        List<Element> listElement = element.elements();
        Map<String, Integer> countMap = getCountMap(listElement);
        List<Map<String, Object>> mapList;
        for (Element childElement : listElement) {
            Map<String, Object> objectMap = xml2JsonStyleMap(childElement);
            if (countMap.containsKey(childElement.getQualifiedName()) && countMap.get(childElement.getQualifiedName()) > 1) {
                Object object = map.get(childElement.getQualifiedName());
                if (object instanceof List) {
                    mapList = (List<Map<String, Object>>) object;
                } else {
                    mapList = new ArrayList<>();
                }
                mapList.add(objectMap);
                map.put(childElement.getQualifiedName(), mapList);
            } else {
                map.put(childElement.getQualifiedName(), objectMap);
            }
        }
        if (element.isRootElement()) {
            rootMap.put(element.getQualifiedName(), map);
        } else {
            rootMap.putAll(map);
        }
        return rootMap;
    }

    /**
     * 统计是否节点名称是否存在多个
     *
     * @param listElement
     * @return
     * @author Jovi
     * @date 2019年7月10日下午15:54:22
     */
    private static Map<String, Integer> getCountMap(List<Element> listElement) {
        Map<String, Integer> countMap = new HashMap<>();
        for (Element childElement : listElement) {
            if (countMap.containsKey(childElement.getQualifiedName())) {
                int count = countMap.get(childElement.getQualifiedName());
                countMap.put(childElement.getQualifiedName(), ++count);
            } else {
                countMap.put(childElement.getQualifiedName(), 1);
            }
        }
        return countMap;
    }

    public static void main(String[] args) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("aa", "aa0001");
//        map.put("bb", "bb0002");
//        System.out.println(map2Xml(map, null));
//        System.out.println(map2XmlKey(map));
//        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><MoBaoAccount MessageType=\"UserMobilePay\" PlatformID=\"b2ctest\"><OrderNo>M20150521084825</OrderNo><TradeAmt>5000.00</TradeAmt><Commission>0.5</Commission><UserID>xiaolong</UserID><MerchID>xiaolong1</MerchID><tradeType>0</tradeType><CustParam>123</CustParam><NotifyUrl>http://mobaopay.com/callback.do</NotifyUrl><TradeSummary>订单</TradeSummary></MoBaoAccount>";
//        String xml2 = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><node><MoBaoAccount MessageType=\"UserMobilePay\" PlatformID=\"b2ctest\"><OrderNo>M20150521084825</OrderNo><TradeAmt>5000.00</TradeAmt><Commission>0.5</Commission><UserID>xiaolong</UserID><MerchID>xiaolong1</MerchID><tradeType>0</tradeType><CustParam>123</CustParam><NotifyUrl>http://mobaopay.com/callback.do</NotifyUrl><TradeSummary>订单</TradeSummary></MoBaoAccount><MoBaoAccount MessageType=\"UserMobilePay2\" PlatformID=\"b2ctest2\"><OrderNo>M201505210848252</OrderNo><TradeAmt>5000.002</TradeAmt><Commission>0.52</Commission><UserID>xiaolong2</UserID><MerchID>xiaolong12</MerchID><tradeType>02</tradeType><CustParam>1232</CustParam><NotifyUrl>http://mobaopay.com/callback.do2</NotifyUrl><TradeSummary>订单2</TradeSummary></MoBaoAccount></node>";
//        try {
//            List<Map<String, Object>> list = xml2List(xml2);
//            List<String> sList = new ArrayList<>();
//            sList.add("哈哈");
//            sList.add("啦啦啦");
//            System.out.println(list2Xml(sList, "Lists", "List"));
//            System.out.println("---------");
//            System.out.println(list2XmlKey(list, "Lists", "List"));
//            for (int i = 0; i < args.length; i++) {
//                for (String key : list.get(i).keySet()) {
//                    System.out.println("key：" + key + ",value:" + key);
//                }
//            }
//            Map<String, Object> map2 = xml2Map(xml);
//            System.out.println(map2);
//            System.out.println(map2.get("MerchID"));
//            System.out.println(xml2Json(xml));
//        } catch (DocumentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        String xmlapp = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><beans xmlns=\"http://www.springframework.org/schema/beans\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:context=\"http://www.springframework.org/schema/context\" xmlns:aop=\"http://www.springframework.org/schema/aop\" xmlns:tx=\"http://www.springframework.org/schema/tx\" xsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd\"><context:component-scan base-package=\"com.hk.ssm.*\" ><context:exclude-filter type=\"annotation\" expression=\"org.springframework.stereotype.Controller\" /></context:component-scan><bean id=\"mappingJacksonHttpMessageConverter\" class=\"org.springframework.http.converter.json.MappingJackson2HttpMessageConverter\"><property name=\"supportedMediaTypes\"><list><value>text/html;charset=UTF-8</value></list></property></bean><bean class=\"org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter\"><property name=\"messageConverters\"><list><ref bean=\"mappingJacksonHttpMessageConverter\" /></list></property></bean><context:property-placeholder location=\"classpath*:config/*.properties\" /><bean id=\"dataSource\" class=\"com.alibaba.druid.pool.DruidDataSource\" destroy-method=\"close\"><property name=\"url\" value=\"${jdbc.url}\" /><property name=\"username\" value=\"${jdbc.username}\" /><property name=\"password\" value=\"${jdbc.password}\" /><property name=\"driverClassName\" value=\"${jdbc.driverClassName}\" /><property name=\"maxActive\" value=\"${jdbc.maxActive}\" /><property name=\"minIdle\" value=\"${jdbc.minIdle}\" /></bean><bean id=\"sqlSessionFactory\" class=\"org.mybatis.spring.SqlSessionFactoryBean\"><property name=\"dataSource\" ref=\"dataSource\" /><property name=\"configLocation\" value=\"classpath:SqlMapConfig.xml\" /><property name=\"typeAliasesPackage\" value=\"com.hk.ssm.pojo\" /></bean><bean id=\"mapperScanner\" class=\"org.mybatis.spring.mapper.MapperScannerConfigurer\"><property name=\"basePackage\" value=\"com.hk.ssm.mapper\" /><property name=\"sqlSessionFactoryBeanName\" value=\"sqlSessionFactory\" /></bean><bean id=\"transactionManager\" class=\"org.springframework.jdbc.datasource.DataSourceTransactionManager\"><property name=\"dataSource\" ref=\"dataSource\" /></bean><tx:annotation-driven transaction-manager=\"transactionManager\" /><tx:advice id=\"txAdvice\" transaction-manager=\"transactionManager\"><tx:attributes><tx:method name=\"find*\" read-only=\"true\"/><tx:method name=\"*\" isolation=\"DEFAULT\"/></tx:attributes></tx:advice><aop:config><aop:advisor advice-ref=\"txAdvice\" pointcut=\"execution(* com.hk.ssm.service.impl.*ServiceImpl.*(..))\"/></aop:config><tx:annotation-driven transaction-manager=\"transactionManager\" /></beans>";
        try {
            System.out.println(FastJsonUtil.obj2Json(XmlConverUtil.xml2JsonStyleMap(xmlapp)));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}
