package io.salopek.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import static io.salopek.constant.AntipodeConstants.EXC_GET_OUTPUTSTREAM;
import static io.salopek.constant.AntipodeConstants.EXC_GET_WRITER;

public class MultiReadHttpServletResponseWrapper extends HttpServletResponseWrapper {

  private ServletOutputStream outputStream;
  private PrintWriter writer;
  private CachedServletOutputStream copier;

  public MultiReadHttpServletResponseWrapper(HttpServletResponse response) throws IOException {
    super(response);
  }

  @Override
  public ServletOutputStream getOutputStream() throws IOException {
    if (writer != null) {
      throw new IllegalStateException(EXC_GET_WRITER);
    }

    if (outputStream == null) {
      outputStream = getResponse().getOutputStream();
      copier = new CachedServletOutputStream(outputStream);
    }

    return copier;
  }

  @Override
  public PrintWriter getWriter() throws IOException {
    if (outputStream != null) {
      throw new IllegalStateException(EXC_GET_OUTPUTSTREAM);
    }

    if (writer == null) {
      copier = new CachedServletOutputStream(getResponse().getOutputStream());
      writer = new PrintWriter(new OutputStreamWriter(copier, getResponse().getCharacterEncoding()), true);
    }

    return writer;
  }

  @Override
  public void flushBuffer() throws IOException {
    if (writer != null) {
      writer.flush();
    } else if (outputStream != null) {
      copier.flush();
    }
  }

  public byte[] getCopy() {
    if (copier != null) {
      return copier.getCopy();
    } else {
      return new byte[0];
    }
  }

  public class CachedServletOutputStream extends ServletOutputStream {

    private final OutputStream outputStream;
    private final ByteArrayOutputStream copy;

    public CachedServletOutputStream(OutputStream outputStream) {
      this.outputStream = outputStream;
      this.copy = new ByteArrayOutputStream(1024);
    }

    @Override
    public void write(int b) throws IOException {
      outputStream.write(b);
      copy.write(b);
    }

    public byte[] getCopy() {
      return copy.toByteArray();
    }

    @Override
    public boolean isReady() {
      return true;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
      //      nop
    }
  }

}