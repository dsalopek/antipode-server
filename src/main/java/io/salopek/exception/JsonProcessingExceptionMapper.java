package io.salopek.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.salopek.logging.LogUtils;
import io.salopek.model.response.ErrorMessageResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class JsonProcessingExceptionMapper implements ExceptionMapper<JsonProcessingException> {
  @Override
  public Response toResponse(JsonProcessingException exception) {
    Response.Status status = Response.Status.BAD_REQUEST;
    String message = exception.getOriginalMessage();

    LogUtils.logException(exception);

    return Response
      .status(status)
      .entity(new ErrorMessageResponse(status, message))
      .build();
  }
}
