package io.salopek.filter;

import org.junit.jupiter.api.Test;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
  void doFilter_NonHttpServletRequest() throws IOException, ServletException {
    ServletRequest servletRequest = mock(ServletRequest.class);
    ServletResponse servletResponse = mock(ServletResponse.class);
    FilterChain filterChain = mock(FilterChain.class);

    assertDoesNotThrow(() -> antipodeFilter.doFilter(servletRequest, servletResponse, filterChain));
  }
}