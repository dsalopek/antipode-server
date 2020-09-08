package io.salopek.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

@Aspect
public class LoggingAspect {
  @Pointcut("@annotation(io.salopek.logging.Loggable) && execution(* *(..))")
  private void loggingTargets() {
    //    empty
  }

  @Around("loggingTargets()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    final Logger logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
    long start = System.currentTimeMillis();

    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();

    String methodName = joinPoint.getSignature().getName();
    LogBuilder lb = LogBuilder.get().log(LogUtils.methodEntry(methodName));

    Loggable loggable = method.getAnnotation(Loggable.class);

    if(loggable.params()) {
      String[] paramNames = signature.getParameterNames();
      Object[] paramValues = joinPoint.getArgs();
      for(int i = 0; i < paramNames.length; i++) {
        lb.kv(paramNames[i], paramValues[i].toString());
      }
    }

    logger.info(lb.build());

    Object result = joinPoint.proceed();
    long duration = System.currentTimeMillis() - start;

    lb.log(LogUtils.methodExit(methodName, duration));
    logger.info(lb.build());
    return result;
  }
}