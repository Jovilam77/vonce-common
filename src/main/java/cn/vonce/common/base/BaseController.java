package cn.vonce.common.base;

import cn.vonce.common.bean.RS;
import cn.vonce.common.enumerate.ResultCode;
import cn.vonce.common.uitls.FastJsonUtil;
import cn.vonce.common.uitls.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 所有controller的父类
 *
 * @author jovi
 * @version 1.0
 * @email 766255988@qq.com
 * @date 2017年3月17日下午5:59:28
 */
public class BaseController {

    private final Logger logger = LoggerFactory.getLogger(BaseController.class);

    public ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<>();
    public ThreadLocal<HttpServletResponse> responseThreadLocal = new ThreadLocal<>();

    public BaseController() {

    }

    public BaseController(HttpServletRequest request, HttpServletResponse response) {
        this.requestThreadLocal.set(request);
        this.responseThreadLocal.set(response);
    }

    /**
     * 服务器内部错误
     *
     * @param msg
     * @return
     * @author jovi
     * @date 2017年3月13日上午11:23:04
     */
    public RS errorHint(String msg) {
        return getRS(ResultCode.ERROR.getCode(), msg, null);
    }

    /**
     * 服务器内部错误(可返回JSONP)
     *
     * @param msg
     * @author jovi
     * @date 2017年3月13日上午11:23:40
     */
    public void errorHintJSONP(String msg) {
        this.writer(this.toJsonOrJsonp(getRS(ResultCode.ERROR.getCode(), msg, null)));
    }

    /**
     * 坏的请求
     *
     * @param msg
     * @return
     * @author jovi
     * @date 2017年3月13日上午11:24:53
     */
    public RS badHint(String msg) {
        return getRS(ResultCode.BAD.getCode(), msg, null);
    }

    /**
     * 坏的请求(可返回JSONP)
     *
     * @param msg
     * @author jovi
     * @date 2017年3月13日上午11:25:03
     */
    public void badHintJSONP(String msg) {
        this.writer(this.toJsonOrJsonp(getRS(ResultCode.BAD.getCode(), msg, null)));
    }

    /**
     * 非法请求
     *
     * @param msg
     * @return
     * @author jovi
     * @date 2017年3月13日上午11:24:53
     */
    public RS unAuthorizedHint(String msg) {
        return getRS(ResultCode.UNAUTHORIZED.getCode(), msg, null);
    }

    /**
     * 非法请求(可返回JSONP)
     *
     * @param msg
     * @author jovi
     * @date 2017年3月13日上午11:25:03
     */
    public void unAuthorizedHintJSONP(String msg) {
        this.writer(this.toJsonOrJsonp(getRS(ResultCode.UNAUTHORIZED.getCode(), msg, null)));
    }

    /**
     * 返回成功状态
     *
     * @param msg
     * @author jovi
     * @date 2017年3月13日上午11:25:38
     */
    public RS successHint(String msg) {
        return getRS(ResultCode.SUCCESS.getCode(), msg, null);
    }

    /**
     * 返回成功状态(可返回JSONP)
     *
     * @param msg
     * @author jovi
     * @date 2017年3月13日上午11:27:27
     */
    public void successHintJSONP(String msg) {
        this.writer(this.toJsonOrJsonp(getRS(ResultCode.SUCCESS.getCode(), msg, null)));
    }

    /**
     * 返回成功状态及数据
     *
     * @param msg
     * @param data
     * @return
     * @author jovi
     * @date 2017年3月13日上午11:28:42
     */
    public RS successHint(String msg, Object data) {
        return getRS(ResultCode.SUCCESS.getCode(), msg, data);
    }

    /**
     * 返回成功状态及数据(可返回JSONP)
     *
     * @param msg
     * @param data
     * @author jovi
     * @date 2017年3月13日上午11:29:47
     */
    public void successHintJSONP(String msg, Object data) {
        this.writer(this.toJsonOrJsonp(getRS(ResultCode.SUCCESS.getCode(), msg, data)));
    }

    /**
     * 返回自定义状态及数据
     *
     * @param rs
     * @author jovi
     * @date 2017年3月13日上午11:31:35
     */
    public RS customHint(RS rs) {
        return rs;
    }

    /**
     * 返回自定义状态及数据(可返回JSONP)
     *
     * @param rs
     * @author jovi
     * @date 2017年3月13日上午11:31:35
     */
    public void customHintJSONP(RS rs) {
        this.writer(this.toJsonOrJsonp(rs));
    }

    /**
     * 返回参数验证未通过或其他验证未通过状态
     *
     * @param msg
     * @return
     * @author jovi
     * @date 2017年3月13日上午11:33:25
     */
    public RS parameterHint(String msg) {
        return getRS(ResultCode.PARAMETER.getCode(), msg, null);
    }

    /**
     * 返回参数验证未通过或其他验证未通过状态
     *
     * @param msg
     * @return
     * @author jovi
     * @date 2017年3月13日上午11:33:25
     */
    public RS parameterHint(String msg, Object data) {
        return getRS(ResultCode.PARAMETER.getCode(), msg, data);
    }

    /**
     * 返回参数验证未通过或其他验证未通过状态(可返回JSONP)
     *
     * @param msg
     * @author jovi
     * @date 2017年3月13日上午11:35:35
     */
    public void parameterHintJSONP(String msg) {
        this.writer(this.toJsonOrJsonp(getRS(ResultCode.PARAMETER.getCode(), msg, null)));
    }

    /**
     * 返回参数验证未通过或其他验证未通过状态及数据(可返回JSONP)
     *
     * @param msg
     * @param data
     * @author Jovi
     * @date 2017年8月2日上午11:42:45
     */
    public void parameterHintJSONP(String msg, Object data) {
        this.writer(this.toJsonOrJsonp(getRS(ResultCode.PARAMETER.getCode(), msg, data)));
    }

    /**
     * 返回逻辑错误或非异常的错误状态
     *
     * @param msg
     * @return
     * @author jovi
     * @date 2017年3月13日上午11:36:06
     */
    public RS othersHint(String msg) {
        return getRS(ResultCode.OTHERS.getCode(), msg, null);
    }

    /**
     * 返回逻辑错误或非异常的错误状态(可返回JSONP)
     *
     * @param msg
     * @author jovi
     * @date 2017年3月13日上午11:57:00
     */
    public void othersHintJSONP(String msg) {
        this.writer(this.toJsonOrJsonp(getRS(ResultCode.OTHERS.getCode(), msg, null)));
    }

    /**
     * 获取rs
     *
     * @param code
     * @param msg
     * @param data
     * @return
     */
    private RS getRS(int code, String msg, Object data) {
        RS rs = new RS();
        rs.setCode(code);
        rs.setMsg(msg);
        if (data != null) {
            rs.setData(data);
        }
        return rs;
    }

    /**
     * 如果请求参数中有callback参数，返回jsonp格式
     *
     * @param object
     * @return
     */
    public String toJsonOrJsonp(Object object) {
        StringBuffer sb = new StringBuffer();
        String callback = getRequest().getParameter("callback");
        if (StringUtil.isNotBlank(callback)) {
            sb.append(callback + "(");
            sb.append(FastJsonUtil.obj2Json(object));
            sb.append(");");
        } else {
            sb.append(FastJsonUtil.obj2Json(object));
        }
        return sb.toString();
    }

    /**
     * 如果有callback参数且不为空设置文档类型为 application/javascript;否则为application/json;
     *
     * @param data
     */
    public void writer(String data) {
        HttpServletResponse response = getResponse();
        PrintWriter printWriter = null;
        try {
            response.setCharacterEncoding("UTF-8");
            if (StringUtil.isNotEmpty(getRequest().getParameter("callback"))) {
                response.setContentType("application/javascript; charset=utf-8");
            } else {
                response.setContentType("application/json; charset=utf-8");
            }
            printWriter = response.getWriter();
            printWriter.write(data);
            // 请求日志
            logger.info(getRequest().getServletPath() + " 的响应内容：" + data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            printWriter.close();
            remove();
        }
    }

    public HttpServletRequest getRequest() {
        return requestThreadLocal.get();
    }

    @ModelAttribute
    public void setRequest(HttpServletRequest request) {
        this.requestThreadLocal.set(request);
    }

    public HttpServletResponse getResponse() {
        return responseThreadLocal.get();
    }

    @ModelAttribute
    public void setResponse(HttpServletResponse response) {
        this.responseThreadLocal.set(response);
    }

    private void remove() {
        requestThreadLocal.remove();
        responseThreadLocal.remove();
    }

}
