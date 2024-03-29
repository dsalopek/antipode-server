package io.salopek.filter;

import io.salopek.logging.LogBuilder;
import io.salopek.logging.LogUtils;
import org.apache.commons.lang3.time.StopWatch;
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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.salopek.constant.AntipodeConstants.ACCESS_TOKEN;
import static io.salopek.constant.AntipodeConstants.HTTP_METHOD;
import static io.salopek.constant.AntipodeConstants.MASK;
import static io.salopek.constant.AntipodeConstants.PASSWORD;
import static io.salopek.constant.AntipodeConstants.REQUEST;
import static io.salopek.constant.AntipodeConstants.REQUEST_ID;
import static io.salopek.constant.AntipodeConstants.RESPONSE;
import static io.salopek.constant.AntipodeConstants.STATUS;
import static io.salopek.constant.AntipodeConstants.THREAD_PREFIX;

public class AntipodeFilter implements Filter {

  private static final Logger LOGGER = LoggerFactory.getLogger(AntipodeFilter.class);

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
    if (request instanceof HttpServletRequest) {
      StopWatch sw = LogUtils.stopWatch();

      MultiReadHttpServletRequestWrapper wrappedRequest = new MultiReadHttpServletRequestWrapper(
        (HttpServletRequest) request);

      MultiReadHttpServletResponseWrapper wrappedResponse = new MultiReadHttpServletResponseWrapper(
        (HttpServletResponse) response);

      String method = wrappedRequest.getMethod();
      String uri = wrappedRequest.getRequestURI();

      Thread.currentThread()
        .setName(THREAD_PREFIX + Thread.currentThread().getId() + " " + REQUEST_ID + UUID.randomUUID());

      LogBuilder lb = LogBuilder.get().log(LogUtils.methodEntry(uri)).kv(HTTP_METHOD, method);
      if (!method.equals(HttpMethod.GET)) {
        String requestString = wrappedRequest.getReader().lines().map(String::trim).collect(Collectors.joining());
        lb.kv(REQUEST, maskKeyValues(requestString));
      }
      LOGGER.info(lb.build());

      chain.doFilter(wrappedRequest, wrappedResponse);

      lb.log(LogUtils.methodExit(uri, sw)).kv(RESPONSE, maskKeyValues(new String(wrappedResponse.getCopy())))
        .kv(STATUS, wrappedResponse.getStatus());
      LOGGER.info(lb.build());
    }
  }

  private static String maskKeyValues(String s) {

    if (null == s || s.isEmpty()) {
      s = "{}";
    }

    List<String> maskedKeys = Arrays.asList(PASSWORD, ACCESS_TOKEN);

    for (String key : maskedKeys) {
      s = s.replaceAll("\"" + key + "\":(\\s)*\"(.*)\"", "\"" + key + "\": \"" + MASK + "\"");
    }

    return s;
  }
}
