package io.salopek.filter;

import org.junit.jupiter.api.Test;

import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AntipodeFilterTest {

  private final AntipodeFilter antipodeFilter = new AntipodeFilter();

  @Test
  void doFilter_HttpServletRequestGET() throws IOException, ServletException {
    HttpServletRequest servletRequest = mock(HttpServletRequest.class);
    HttpServletResponse servletResponse = mock(HttpServletResponse.class);
    FilterChain filterChain = mock(FilterChain.class);

    when(servletRequest.getMethod()).thenReturn("GET");
    when(servletRequest.getRequestURI()).thenReturn("/game/newGame");

    assertDoesNotThrow(() -> antipodeFilter.doFilter(servletRequest, servletResponse, filterChain));
  }

  @Test
  void doFilter_HttpServletRequestPOST() throws IOException, ServletException {
    HttpServletRequest servletRequest = mock(HttpServletRequest.class);
    HttpServletResponse servletResponse = mock(HttpServletResponse.class);
    FilterChain filterChain = mock(FilterChain.class);

    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
      "{\"gameUUID\":\"asdf-1234\"}".getBytes(StandardCharsets.UTF_8));
    ServletInputStream servletInputStream = getServletInputStream(byteArrayInputStream);

    when(servletRequest.getInputStream()).thenReturn(servletInputStream);
    when(servletRequest.getMethod()).thenReturn("POST");
    when(servletRequest.getRequestURI()).thenReturn("/game/newGame");

    assertDoesNotThrow(() -> antipodeFilter.doFilter(servletRequest, servletResponse, filterChain));
  }

  @Test
  void doFilter_NonHttpServletRequest() throws IOException, ServletException {
    ServletRequest servletRequest = mock(ServletRequest.class);
    ServletResponse servletResponse = mock(ServletResponse.class);
    FilterChain filterChain = mock(FilterChain.class);

    assertDoesNotThrow(() -> antipodeFilter.doFilter(servletRequest, servletResponse, filterChain));
  }

  private ServletInputStream getServletInputStream(ByteArrayInputStream byteArrayInputStream) {
    return new ServletInputStream() {
      @Override
      public boolean isFinished() {
        return false;
      }

      @Override
      public boolean isReady() {
        return true;
      }

      @Override
      public void setReadListener(ReadListener readListener) {

      }

      public int read() throws IOException {
        return byteArrayInputStream.read();
      }
    };
  }
}