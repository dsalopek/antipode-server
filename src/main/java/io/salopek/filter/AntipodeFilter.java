package io.salopek.filter;

import io.salopek.logging.LogBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.HttpMethod;
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

      LogBuilder lb = LogBuilder.get().t("Incoming Request").kv("method", method).kv("uri", uri);
      if(!method.equals(HttpMethod.GET)) {
//        todo: implement wrapper to read request body that can be read multiple time
//        see: https://stackoverflow.com/questions/10210645/http-servlet-request-lose-params-from-post-body-after-read-it-once

//        lb.kv("request", request.getReader().lines().map(String::trim).collect(Collectors.joining()));
      }
      LOGGER.info(lb.build());

      chain.doFilter(request, response);
    }
  }
}
