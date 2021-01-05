package io.salopek.model.response;

import javax.ws.rs.core.Response;

public class ErrorMessageResponse {
  private int code;
  private String message;

  private ErrorMessageResponse(){}

  public ErrorMessageResponse(Response.Status status, String message) {
    this.code = status.getStatusCode();
    this.message = message;
  }

  public ErrorMessageResponse(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "ErrorMessageResponse{" +
      "code=" + code +
      ", message='" + message + '\'' +
      '}';
  }
}
