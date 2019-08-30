package cn.vonce.common.aspect;

import com.google.common.base.Stopwatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Controller增强日志、异常处理
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
        logger.info("S执行开始：" + pjp.getSignature());
        Stopwatch stopwatch = Stopwatch.createStarted();
        AspectData aspectData = new AspectData();
        aspectData.setSignature(pjp.getSignature());
        aspectData.setParams(pjp.getArgs());
        aspectData.setResult(pjp.proceed(pjp.getArgs()));
        logger.info("S执行结束：" + pjp.getSignature());
        logger.info("S执行耗时：" + stopwatch.stop().elapsed(TimeUnit.MILLISECONDS) + "(毫秒).");
        return aspectData;
    }

}
