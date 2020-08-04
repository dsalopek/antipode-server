package io.salopek.logging;

import io.salopek.util.LogUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggingAspect {
  Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

  @Pointcut("@annotation(io.salopek.logging.Loggable) && execution(* *(..))")
  private void loggingTargets() {
  }

  @Around("loggingTargets()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.currentTimeMillis();
    String method = joinPoint.getSignature().toShortString();
    LogBuilder lb = LogBuilder.get().log(LogUtils.methodEntry(method));
    LOGGER.info(lb.build());

    Object result = joinPoint.proceed();
    long duration = System.currentTimeMillis() - start;

    lb.log(LogUtils.methodExit(method)).kv("executionTime", duration + "ms");
    LOGGER.info(lb.build());
    return result;
  }
}