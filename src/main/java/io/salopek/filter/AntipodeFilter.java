package io.salopek.filter;

import io.salopek.util.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

public class AntipodeFilter implements Filter {

  private static final Logger LOGGER = LoggerFactory.getLogger(AntipodeFilter.class);

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
    if (request instanceof HttpServletRequest) {
      MDC.clear();
      MDC.put("requestId", UUID.randomUUID().toString());

      String method = ((HttpServletRequest) request).getMethod();
      String uri = ((HttpServletRequest) request).getRequestURI();

      LOGGER.info(LogUtils.logObject("method", method));
      LOGGER.info(LogUtils.logObject("uri", uri));

      chain.doFilter(request, response);
    }
  }
}
