package io.salopek.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    String method = joinPoint.getSignature().toShortString();
    LogBuilder lb = LogBuilder.get().log(LogUtils.methodEntry(method));
    logger.info(lb.build());

    Object result = joinPoint.proceed();
    long duration = System.currentTimeMillis() - start;

    lb.log(LogUtils.methodExit(method, duration));
    logger.info(lb.build());
    return result;
  }
}