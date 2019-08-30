package cn.vonce.common.aspect;

import cn.vonce.common.annotation.LogContent;
import cn.vonce.common.base.BaseController;
import cn.vonce.common.bean.RS;
import cn.vonce.common.enumerate.ResultCode;
import cn.vonce.common.utils.RequestDataUtil;
import com.google.common.base.Stopwatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * Controller增强日志、异常处理
 *
 * @author jovi
 * @version 1.0
 * @email 766255988@qq.com
 * @date 2017年3月13日下午3:17:08
 */
public class BaseControllerAspect {

    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(BaseControllerAspect.class);

    public class AspectData {
        private Signature signature;
        private String url;
        private String headers;
        private String param;
        private Object result;

        public String getUrl() {
            return url;
        }

        private void setUrl(String url) {
            this.url = url;
        }

        public Signature getSignature() {
            return signature;
        }

        private void setSignature(Signature signature) {
            this.signature = signature;
        }

        public String getHeaders() {
            return headers;
        }

        private void setHeaders(String headers) {
            this.headers = headers;
        }

        public String getParam() {
            return param;
        }

        private void setParam(String param) {
            this.param = param;
        }

        public Object getResult() {
            return result;
        }

        private void setResult(Object result) {
            this.result = result;
        }

    }

    /**
     * 环绕处理
     *
     * @param pjp
     * @return
     */
    public AspectData handle(ProceedingJoinPoint pjp) {
        LogContent logContent = pjp.getThis().getClass().getAnnotation(LogContent.class);
        logger.info("C执行开始：" + (logContent != null ? "(" + logContent.value() + ")" : "") + pjp.getSignature());
        Stopwatch stopwatch = Stopwatch.createStarted();
        AspectData aspectData = new AspectData();
        try {
            Object objects[] = pjp.getArgs();
            HttpServletRequest request = null;
            for (Object object : objects) {
                if (object instanceof HttpServletRequest) {
                    request = (HttpServletRequest) object;
                }
            }
            aspectData.setSignature(pjp.getSignature());
            if (request == null && pjp.getTarget() instanceof BaseController) {
                request = ((BaseController) pjp.getTarget()).getRequest();
            }
            if (request != null) {
                aspectData.setUrl(request.getRequestURL().toString());
                aspectData.setHeaders(RequestDataUtil.getHeaders(request));
                aspectData.setParam(RequestDataUtil.getParameters(request.getParameterMap()));
                logger.info("请求地址：" + aspectData.getUrl());
                logger.info("请求头部：" + aspectData.getHeaders());
                logger.info("请求参数：" + aspectData.getParam());
            }
            aspectData.setResult(pjp.proceed(pjp.getArgs()));
            logger.info("C执行结束：" + (logContent != null ? "(" + logContent.value() + ")" : "") + aspectData.getSignature());
            logger.info("C响应内容：" + aspectData.getResult());
            logger.info("C执行耗时：" + stopwatch.stop().elapsed(TimeUnit.MILLISECONDS) + "(毫秒).");
        } catch (Throwable throwable) {
            String msg = "系统异常：" + throwable.getMessage();
            RS rs = new RS();
            rs.setCode(ResultCode.ERROR.getCode());
            rs.setMsg(msg);
            aspectData.setResult(rs);
            logger.error(msg);
            throwable.printStackTrace();
        }
        return aspectData;
    }

}
