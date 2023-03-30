package com.frankmoley.lil.fid.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
@Aspect
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("@annotation(Loggable)")
    public void executeLogging() {
    }

//    @Before("executeLogging()")
//    public void logMethodCall(JoinPoint joinPoint) {
//        StringBuilder message = new StringBuilder("Method: ");
//        message.append(joinPoint.getSignature().getName());
//        Object[] args = joinPoint.getArgs(); //this can be sensitive info, so careful with it!
//
//        if (null != args && args.length > 0) {
//            message.append(" args=[ | ");
//            Arrays.asList(args).forEach( arg -> {
//                message.append(arg).append(" | ");
//            });
//            message.append("]");
//        }
//
//        LOGGER.info(message.toString());
//    }

//    @AfterReturning(value = "executeLogging()", returning = "returnValue")
//    public void logMethodCall(JoinPoint joinPoint, Object returnValue) {
//        StringBuilder message = new StringBuilder("Method: ");
//        message.append(joinPoint.getSignature().getName());
//        Object[] args = joinPoint.getArgs(); //this can be sensitive info, so careful with it!
//
//        if (null != args && args.length > 0) {
//            message.append(" args=[ | ");
//            Arrays.asList(args).forEach(arg -> {
//                message.append(arg).append(" | ");
//            });
//            message.append("]");
//        }
//
//        message.append(", returning: ");
//
//        if (returnValue instanceof Collection) {
//            message.append(((Collection<?>) returnValue).size()).append(" instance(s)");
//        } else {
//            message.append(returnValue.toString());
//        }
//
//        LOGGER.info(message.toString());
//    }

    @Around(value = "executeLogging()")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object returnValue = joinPoint.proceed();
        long totalTime = System.currentTimeMillis() - startTime;

        StringBuilder message = new StringBuilder("Method: ");
        message.append(joinPoint.getSignature().getName());
        message.append(" totaltime: ").append(totalTime).append(" ms");

        Object[] args = joinPoint.getArgs(); //this can be sensitive info, so careful with it!

        if (null != args && args.length > 0) {
            message.append(" args=[ | ");
            Arrays.asList(args).forEach(arg -> {
                message.append(arg).append(" | ");
            });
            message.append("]");
        }

        message.append(", returning: ");

        if (returnValue instanceof Collection) {
            message.append(((Collection<?>) returnValue).size()).append(" instance(s)");
        } else {
            message.append(returnValue.toString());
        }

        LOGGER.info(message.toString());

        return returnValue;
    }
}
