package io.salopek.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.UnexpectedException;

@Aspect
public class LoggingAspect {
  private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

  @Pointcut("@annotation(io.salopek.logging.Loggable) && execution(* *(..))")
  private void loggingTargets() {
    //    empty
  }

  @Around("loggingTargets()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.currentTimeMillis();
    String method = joinPoint.getSignature().toShortString();
    LogBuilder lb = LogBuilder.get().log(LogUtils.methodEntry(method));
    LOGGER.info(lb.build());

    Object result = null;

    try {
      result = joinPoint.proceed();
    } catch (Exception e) {
      throw new UnexpectedException("Error proceeding at joinPoint" + e.getMessage());
    }
    long duration = System.currentTimeMillis() - start;

    lb.log(LogUtils.methodExit(method, duration));
    LOGGER.info(lb.build());
    return result;
  }
}