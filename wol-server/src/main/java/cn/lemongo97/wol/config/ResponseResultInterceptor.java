package cn.lemongo97.wol.config;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author LemonGo97
 */
@Component
public class ResponseResultInterceptor implements HandlerInterceptor {

    public static final String RESPONSE_RESULT_ANN = "RESPONSE-RESULT-ANN";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 请求的方法
        if (handler instanceof HandlerMethod){
            final HandlerMethod handlerMethod = (HandlerMethod)handler;
            final Class<?> clazz = handlerMethod.getBeanType();
            final Method method = handlerMethod.getMethod();

            // 判断是否在类对象上加了注解
            if (clazz.isAnnotationPresent(ResponseResult.class)){
                request.setAttribute(RESPONSE_RESULT_ANN,clazz.getAnnotation(ResponseResult.class));
            }else if (method.isAnnotationPresent(ResponseResult.class)){
                request.setAttribute(RESPONSE_RESULT_ANN,method.getAnnotation(ResponseResult.class));
            }
        }
        return true;
    }
}
