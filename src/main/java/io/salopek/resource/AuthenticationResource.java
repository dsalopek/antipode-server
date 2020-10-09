package io.salopek.resource;

import io.salopek.logging.Loggable;
import io.salopek.model.request.LoginRequest;
import io.salopek.model.request.RegisterRequest;
import io.salopek.model.request.UsernameAvailabilityRequest;
import io.salopek.model.request.ValidateTokenRequest;
import io.salopek.model.response.AccessTokenResponse;
import io.salopek.processor.AuthenticationProcessor;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static io.salopek.constant.AntipodeConstants.AUTH_ENDPOINT;
import static io.salopek.constant.AntipodeConstants.AVAILABILITY;
import static io.salopek.constant.AntipodeConstants.LOGIN;
import static io.salopek.constant.AntipodeConstants.REGISTER;
import static io.salopek.constant.AntipodeConstants.VALIDATE_TOKEN;

@Path(AUTH_ENDPOINT)
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationResource {

  private final AuthenticationProcessor authenticationProcessor;

  @Inject
  public AuthenticationResource(AuthenticationProcessor authenticationProcessor) {
    this.authenticationProcessor = authenticationProcessor;
  }

  @Loggable
  @POST
  @Path(REGISTER)
  public Response register(@NotNull @Valid RegisterRequest registerRequest) {
    AccessTokenResponse accessToken = authenticationProcessor.register(registerRequest);
    return Response.ok(accessToken).build();
  }

  @Loggable
  @POST
  @Path(LOGIN)
  public Response login(@NotNull @Valid LoginRequest loginRequest) {
    AccessTokenResponse accessToken = authenticationProcessor.login(loginRequest);
    return Response.ok(accessToken).build();
  }

  @Loggable
  @POST
  @Path(VALIDATE_TOKEN)
  public Response validateToken(@NotNull @Valid ValidateTokenRequest validateTokenRequest) {
    authenticationProcessor.validateTokenRequest(validateTokenRequest);
    return Response.ok().build();
  }

  @Loggable
  @POST
  @Path(AVAILABILITY)
  public Response availability(@NotNull @Valid UsernameAvailabilityRequest usernameAvailabilityRequest) {
    authenticationProcessor.availability(usernameAvailabilityRequest);
    return Response.ok().build();
  }
}
