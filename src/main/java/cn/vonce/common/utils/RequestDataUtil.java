package cn.vonce.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 * Controller增强日志、异常处理
 *
 * @author jovi
 * @version 1.0
 * @email 766255988@qq.com
 * @date 2019年6月12日下午18:59:48
 */
public class RequestDataUtil {

    /**
     * 获取请求头
     *
     * @param request
     * @return
     */
    public static String getHeaders(HttpServletRequest request) {
        Enumeration<String> enumeration = request.getHeaderNames();
        StringBuffer headers = new StringBuffer();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            headers.append("{" + name + "=[" + request.getHeader(name) + "]},");
        }
        if (headers.length() > 0) {
            headers.deleteCharAt(headers.length() - 1);
        }
        return headers.toString();
    }

    /**
     * 获取参数
     *
     * @param map
     * @return
     */
    public static String getParameters(Map<String, String[]> map) {
        StringBuffer parameters = new StringBuffer();
        if (map != null) {
            for (String key : map.keySet()) {
                StringBuffer values = new StringBuffer();
                values.append("[");
                for (String value : map.get(key)) {
                    values.append(value + ",");
                }
                if (values.length() > 1) {
                    values.deleteCharAt(values.length() - 1);
                }
                values.append("]");
                //过滤密码
                if (key.indexOf("password") > -1) {
                    parameters.append(key + ":********,");
                } else {
                    parameters.append(key + ":" + values.toString() + ",");
                }
            }
            if (parameters.length() > 0) {
                parameters.deleteCharAt(parameters.length() - 1);
            }
        }
        return parameters.toString();
    }

}
