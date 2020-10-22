package io.salopek.security;

import io.salopek.model.response.ErrorMessageResponse;

import javax.ws.rs.core.Response;

public class UnauthorizedHandler implements io.dropwizard.auth.UnauthorizedHandler {
  @Override
  public Response buildResponse(String s, String s1) {
    Response.Status status = Response.Status.UNAUTHORIZED;
    return Response.status(status)
      .entity(new ErrorMessageResponse(status, "Credentials are required to access this resource.")).build();
  }
}