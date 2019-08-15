package cn.vonce.common.intercept;

import cn.vonce.common.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求拦截增强, 异常处理
 * 
 * @author jovi
 * @email 766255988@qq.com
 * @version 1.0
 * @date 2017年3月16日上午9:23:43
 */
public class CommonIntercept extends BaseController implements HandlerInterceptor {

	private Logger logger = LoggerFactory.getLogger(CommonIntercept.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		if (ex != null) {
			logger.error("系统异常：{}",ex.getMessage());
		}
		// Bad Request坏请求
		if (response.getStatus() == 400) {
			String classPath = "";
			String methodName = "";
			if (handler instanceof HandlerMethod) {
				HandlerMethod handlerMethod = (HandlerMethod) handler;
				classPath = handlerMethod.getBean().getClass().getName();
				if (classPath != null && !"".equals(classPath)) {
					int index = classPath.indexOf("$$");
					if (index > -1) {
						classPath = classPath.substring(0, classPath.indexOf("$$"));
					}
				}
				methodName = handlerMethod.getMethod().getName();
			}
			logger.warn("Hey,Bad Request! 出现这种情况, 请检查传递的参数类型是否与:{}.{}()方法的model一致,或参数顺序是否一致.",classPath,methodName);
		}
	}

}
