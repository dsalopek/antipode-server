package io.salopek.resource;

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import io.salopek.db.DatabaseService;
import io.salopek.model.request.LoginRequest;
import io.salopek.model.request.RegisterRequest;
import io.salopek.model.request.ValidateTokenRequest;
import io.salopek.model.response.AccessTokenResponse;
import io.salopek.model.response.ValidateTokenResponse;
import io.salopek.processor.AuthenticationProcessor;
import io.salopek.processor.AuthenticationProcessorImpl;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import static io.salopek.constant.AntipodeConstants.AUTH_ENDPOINT;
import static io.salopek.constant.AntipodeConstants.LOGIN;
import static io.salopek.constant.AntipodeConstants.REGISTER;
import static io.salopek.constant.AntipodeConstants.VALIDATE_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(DropwizardExtensionsSupport.class)
class AuthenticationResourceTest {

  private static AuthenticationProcessor authenticationProcessor = mock(AuthenticationProcessor.class);
  private static ResourceExtension ext = ResourceExtension.builder()
    .addResource(new AuthenticationResource(authenticationProcessor))
    .build();

  private static DatabaseService databaseServiceMock = mock(DatabaseService.class);
  private static AuthenticationProcessor authenticationProcessorReal = new AuthenticationProcessorImpl(databaseServiceMock);
  private static ResourceExtension extRealResource = ResourceExtension.builder()
    .addResource(new AuthenticationResource(authenticationProcessorReal))
    .build();

  @Test
  void register() {
    RegisterRequest request = new RegisterRequest("Tony", "asdfg");
    AccessTokenResponse accessTokenResponse = new AccessTokenResponse("asdfghjkl123456789");
    when(authenticationProcessor.register(any())).thenReturn(accessTokenResponse);

    Response response = ext.target(AUTH_ENDPOINT + REGISTER).request().post(Entity.json(request));

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK_200);
    AccessTokenResponse actual = response.readEntity(AccessTokenResponse.class);
    assertThat(actual).usingRecursiveComparison().isEqualTo(accessTokenResponse);
  }

  @Test
  void register_invalidParams() {
    RegisterRequest request = new RegisterRequest("", "abcd");
    Response response = ext.target(AUTH_ENDPOINT + REGISTER).request().post(Entity.json(request));
    assertThat(response.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY_422);

    request = new RegisterRequest("Dylan", "");
    response = ext.target(AUTH_ENDPOINT + REGISTER).request().post(Entity.json(request));
    assertThat(response.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY_422);
  }

  @Test
  void login() {
    LoginRequest request = new LoginRequest("Tony", "asdfg");
    AccessTokenResponse accessTokenResponse = new AccessTokenResponse("asdfghjkl123456789");
    when(authenticationProcessor.login(any())).thenReturn(accessTokenResponse);

    Response response = ext.target(AUTH_ENDPOINT + LOGIN).request().post(Entity.json(request));

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK_200);
    AccessTokenResponse actual = response.readEntity(AccessTokenResponse.class);
    assertThat(actual).usingRecursiveComparison().isEqualTo(accessTokenResponse);
  }

  @Test
  void login_invalidParams() {
    LoginRequest request = new LoginRequest("", "abcd");
    Response response = ext.target(AUTH_ENDPOINT + LOGIN).request().post(Entity.json(request));
    assertThat(response.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY_422);

    request = new LoginRequest("Dylan", "");
    response = ext.target(AUTH_ENDPOINT + LOGIN).request().post(Entity.json(request));
    assertThat(response.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY_422);
  }

  @Test
  void validate() {
    ValidateTokenRequest tokenRequest = new ValidateTokenRequest("abc123");
    when(authenticationProcessor.validateTokenRequest(any())).thenReturn(new ValidateTokenResponse(true));

    Response response = ext.target(AUTH_ENDPOINT + VALIDATE_TOKEN).request().post(Entity.json(tokenRequest));

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK_200);
    assertThat(response.readEntity(ValidateTokenResponse.class).isValid()).isEqualTo(true);
  }

  @Test
  void validate_invalid() {

    ValidateTokenRequest tokenRequest = new ValidateTokenRequest("abc123");
    when(databaseServiceMock.doesAccessTokenExist(anyString())).thenReturn(false);

    Response response = extRealResource.target(AUTH_ENDPOINT + VALIDATE_TOKEN).request().post(Entity.json(tokenRequest));

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK_200);
    assertThat(response.readEntity(ValidateTokenResponse.class).isValid()).isEqualTo(false);
  }
}