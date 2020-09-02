package io.salopek.resource;

import io.salopek.model.request.LoginRequest;
import io.salopek.model.request.RegisterRequest;
import io.salopek.model.response.AccessTokenResponse;
import io.salopek.processor.AuthenticationProcessor;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth")
public class AuthenticationResource {

  private final AuthenticationProcessor authenticationProcessor;

  @Inject
  public AuthenticationResource(AuthenticationProcessor authenticationProcessor) {
    this.authenticationProcessor = authenticationProcessor;
  }

  @POST
  @Path("/register")
  @Produces(MediaType.APPLICATION_JSON)
  public Response register(RegisterRequest registerRequest) {
    AccessTokenResponse accessToken = authenticationProcessor.register(registerRequest);
    return Response.ok(accessToken).build();
  }

  @POST
  @Path("/login")
  @Produces(MediaType.APPLICATION_JSON)
  public Response login(LoginRequest loginRequest) {
    AccessTokenResponse accessToken = authenticationProcessor.login(loginRequest);
    return Response.ok(accessToken).build();
  }
}
