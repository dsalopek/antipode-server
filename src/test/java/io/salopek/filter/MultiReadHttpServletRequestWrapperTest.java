package io.salopek.filter;

import org.junit.jupiter.api.Test;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MultiReadHttpServletRequestWrapperTest {
  @Test
  void methodTestTwice() throws IOException {
    HttpServletRequest servletRequest = mock(HttpServletRequest.class);

    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
      "{\"gameUUID\":\"asdf-1234\"}".getBytes(StandardCharsets.UTF_8));
    ServletInputStream servletInputStream = getServletInputStream(byteArrayInputStream);

    when(servletRequest.getInputStream()).thenReturn(servletInputStream);

    MultiReadHttpServletRequestWrapper requestWrapper = new MultiReadHttpServletRequestWrapper(servletRequest);

    assertDoesNotThrow(requestWrapper::getInputStream);
    assertDoesNotThrow(requestWrapper::getInputStream);
    assertDoesNotThrow(requestWrapper::getInputStream);
    assertDoesNotThrow(requestWrapper::getInputStream);
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

