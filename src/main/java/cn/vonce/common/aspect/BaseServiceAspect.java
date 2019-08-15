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

    /**
     * 环绕处理
     *
     * @param pjp
     * @return
     */
    public Object handle(ProceedingJoinPoint pjp) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Object result = null;
        try {
            Object objects[] = pjp.getArgs();
            Signature signature = pjp.getSignature();
            logger.info("执行开始：" + signature);
            for (Object object : objects) {
                logger.debug("param> {}：{}", object.getClass().getName(), object.toString());
            }
            result = pjp.proceed(pjp.getArgs());
            logger.info("执行结束：" + signature);
            logger.info("执行耗时：" + stopwatch.stop().elapsed(TimeUnit.MILLISECONDS) + "(毫秒).");
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage());
            throwable.printStackTrace();
        }
        return result;
    }

}
