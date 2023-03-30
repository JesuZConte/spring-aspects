package com.frankmoley.lil.fid.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class CounterAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(CounterAspect.class);

    private static final Map<String, Integer> countingMap = new HashMap();

    @Pointcut("@annotation(Countable)")
    public void executeCounter() {

    }

    @Before(value = "executeCounter()")
    public void countMethodCall(JoinPoint joinPoint) {
        StringBuilder message = new StringBuilder("Method: ");
        String methodName = joinPoint.getSignature().getName();
        message.append(methodName);

        if (countingMap.containsKey(methodName)) {
            int counter = countingMap.get(methodName);
            counter++;
            countingMap.put(methodName, counter);
        } else {
            countingMap.put(methodName, 1);
        }

        message.append(" ").append(countingMap.get(methodName)).append(" times called");

        LOGGER.info(message.toString());
    }

}
