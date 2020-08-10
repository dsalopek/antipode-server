package io.salopek.filter;

import org.junit.jupiter.api.Test;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MultiReadHttpServletResponseWrapperTest {

  @Test
  void methodTestTwice() throws IOException {
    HttpServletResponse servletResponse = mock(HttpServletResponse.class);

    //    servletResponse.getOutputStream()
    //    when(servletResponse.getInputStream()).thenReturn(servletInputStream);

    MultiReadHttpServletResponseWrapper responseWrapper = new MultiReadHttpServletResponseWrapper(servletResponse);

    when(servletResponse.getCharacterEncoding()).thenReturn(String.valueOf(StandardCharsets.UTF_8));

    assertDoesNotThrow(responseWrapper::getOutputStream);
    assertDoesNotThrow(responseWrapper::getOutputStream);
    assertDoesNotThrow(responseWrapper::getOutputStream);
    assertDoesNotThrow(responseWrapper::getOutputStream);
    assertDoesNotThrow(responseWrapper::getWriter);
    assertDoesNotThrow(responseWrapper::flushBuffer);
    assertDoesNotThrow(responseWrapper::getCopy);
  }
}
