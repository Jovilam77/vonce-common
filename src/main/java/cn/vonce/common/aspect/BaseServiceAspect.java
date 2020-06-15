package cn.vonce.common.aspect;

import cn.vonce.common.annotation.LogContent;
import com.google.common.base.Stopwatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Service增强日志、异常处理
 *
 * @author jovi
 * @version 1.0
 * @email 766255988@qq.com
 * @date 2017年3月13日下午3:17:08
 */
public class BaseServiceAspect {

    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(BaseServiceAspect.class);

    public class AspectData {
        private Signature signature;
        private Object[] params;
        private Object result;

        public Signature getSignature() {
            return signature;
        }

        private void setSignature(Signature signature) {
            this.signature = signature;
        }

        public Object[] getParams() {
            return params;
        }

        private void setParams(Object[] params) {
            this.params = params;
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
    public AspectData handle(ProceedingJoinPoint pjp) throws Throwable {
        Stopwatch stopwatch = Stopwatch.createStarted();
        LogContent logContent = pjp.getThis().getClass().getAnnotation(LogContent.class);
        logger.info("执行开始：{}", (logContent != null ? "(" + logContent.value() + ")" : "") + pjp.getSignature());
        logger.info("方法参数：{}", pjp.getArgs());
        AspectData aspectData = new AspectData();
        aspectData.setSignature(pjp.getSignature());
        aspectData.setParams(pjp.getArgs());
        aspectData.setResult(pjp.proceed(pjp.getArgs()));
        logger.info("执行结束：{}，耗时：{}(毫秒).", (logContent != null ? "(" + logContent.value() + ")" : "") + pjp.getSignature(), stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
        return aspectData;
    }

}
