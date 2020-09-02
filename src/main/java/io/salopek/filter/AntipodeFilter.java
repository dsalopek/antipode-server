package io.salopek.filter;

import io.salopek.logging.LogBuilder;
import io.salopek.logging.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

public class AntipodeFilter implements Filter {

  private static final Logger LOGGER = LoggerFactory.getLogger(AntipodeFilter.class);

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
    if (request instanceof HttpServletRequest) {
      long start = System.currentTimeMillis();

      MultiReadHttpServletRequestWrapper wrappedRequest = new MultiReadHttpServletRequestWrapper(
        (HttpServletRequest) request);

      MultiReadHttpServletResponseWrapper wrappedResponse = new MultiReadHttpServletResponseWrapper(
        (HttpServletResponse) response);

      String method = wrappedRequest.getMethod();
      String uri = wrappedRequest.getRequestURI();

      LogBuilder lb = LogBuilder.get().log(LogUtils.methodEntry(uri)).kv("HTTPMethod", method);
      if (!method.equals(HttpMethod.GET)) {
        String requestString = wrappedRequest.getReader().lines().map(String::trim).collect(Collectors.joining());
        lb.kv("request", maskKeyValues(requestString));
      }
      LOGGER.info(lb.build());

      Thread.currentThread().setName(Thread.currentThread().getName() + " requestId=" + UUID.randomUUID().toString());

      chain.doFilter(wrappedRequest, wrappedResponse);

      long duration = System.currentTimeMillis() - start;

      lb.log(LogUtils.methodExit(uri, duration)).kv("response", new String(wrappedResponse.getCopy()));
      LOGGER.info(lb.build());
    }
  }

  private static String maskKeyValues(String s) {
    final String MASK = "***********";

    return s.replaceAll("\"password\":(\\s)*\"(.*)\"", "\"password\": \"" + MASK + "\"");
  }
}
