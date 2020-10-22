package io.salopek.exception;

import io.dropwizard.jersey.validation.JerseyViolationException;
import io.salopek.model.response.ErrorMessageResponse;

import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.stream.Collectors;

public class JerseyViolationExceptionMapper implements ExceptionMapper<JerseyViolationException> {
  @Override
  public Response toResponse(JerseyViolationException exception) {
    Response.Status status = Response.Status.BAD_REQUEST;
    String message = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(
      Collectors.joining("\n"));
    return Response
      .status(status)
      .entity(new ErrorMessageResponse(status, message))
      .build();
  }
}
