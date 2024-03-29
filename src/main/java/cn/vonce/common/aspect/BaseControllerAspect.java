package cn.vonce.common.aspect;

import cn.vonce.common.annotation.LogContent;
import cn.vonce.common.bean.RS;
import cn.vonce.common.enumerate.ResultCode;
import cn.vonce.common.utils.RequestDataUtil;
import com.google.common.base.Stopwatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
        Stopwatch stopwatch = Stopwatch.createStarted();
        LogContent logContent = pjp.getThis().getClass().getAnnotation(LogContent.class);
        logger.info("请求开始: {}", (logContent != null ? "(" + logContent.value() + ")" : "") + pjp.getSignature());
        AspectData aspectData = new AspectData();
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            if (request != null) {
                aspectData.setUrl(request.getRequestURL().toString());
                aspectData.setHeaders(RequestDataUtil.getHeaders(request));
                aspectData.setParam(RequestDataUtil.getParameters(request.getParameterMap()));
                logger.info("请求地址: {}, 请求头部{}, 请求参数: {}", aspectData.getUrl(), aspectData.getHeaders(), aspectData.getParam());
            }
            aspectData.setResult(pjp.proceed(pjp.getArgs()));
            logger.debug("请求响应: " + aspectData.getResult());
            logger.info("请求结束: {}, 耗时: {}(毫秒).", (logContent != null ? "(" + logContent.value() + ")" : "") + aspectData.getSignature(), stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
        } catch (Throwable throwable) {
            String msg = "系统异常: " + throwable.getMessage();
            if (RS.class.isAssignableFrom(((MethodSignature) pjp.getSignature()).getReturnType())) {
                RS rs = new RS();
                rs.setCode(ResultCode.ERROR.getCode());
                rs.setMsg(msg);
                aspectData.setResult(rs);
            }
            logger.error(msg, throwable);
            throwable.printStackTrace();
        }
        return aspectData;
    }

}
