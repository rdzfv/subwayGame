package com.xyy.subway.artree.aop;

import com.alibaba.fastjson.JSONArray;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


/**
 * @author xyy
 * @date 2020/1/22 19:11
 * @description 日志打印（以AOP形式）
*/
@Aspect // 使之成为切面类
@Component // 把切面类加入到IOC容器中
public class LogAspect {
    private Logger logger = LoggerFactory.getLogger(LogAspect.class);

    public static enum Level {
        TRACE, DEBUG, INFO, WARN, ERROR
    }


    /**
     * Log at the specified level. If the "logger" is null, nothing is logged.
     * If the "level" is null, nothing is logged. If the "txt" is null,
     * behaviour depends on the SLF4J implementation.
     */
    public static void log(Logger logger, Level level, String txt) {
        if (logger != null && level != null) {
            switch (level) {
                case TRACE:
                    logger.trace(txt);
                    break;
                case DEBUG:
                    logger.debug(txt);
                    break;
                case INFO:
                    logger.info(txt);
                    break;
                case WARN:
                    logger.warn(txt);
                    break;
                case ERROR:
                    logger.error(txt);
                    break;
            }
        }
    }


    @Pointcut("execution(public * com.xyy.subway.artree.controller.*.*(..))")
    public void log(){}


    @Before("log()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));


    }


    @AfterReturning(returning = "ret",pointcut = "log()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 将对象转成json
        Object obj = JSONArray.toJSON(ret);
        String json = obj.toString();
        System.out.println("将Person对象转成json:" + json);
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + json);
    }
}
