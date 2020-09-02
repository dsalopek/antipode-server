package io.salopek.resource;

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import io.salopek.model.request.LoginRequest;
import io.salopek.model.request.RegisterRequest;
import io.salopek.model.response.AccessTokenResponse;
import io.salopek.processor.AuthenticationProcessor;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import static io.salopek.constant.AntipodeConstants.AUTH_ENDPOINT;
import static io.salopek.constant.AntipodeConstants.LOGIN;
import static io.salopek.constant.AntipodeConstants.REGISTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(DropwizardExtensionsSupport.class)
class AuthenticationResourceTest {

  private static final AuthenticationProcessor authenticationProcessor = mock(AuthenticationProcessor.class);
  private static final ResourceExtension ext = ResourceExtension.builder()
    .addResource(new AuthenticationResource(authenticationProcessor))
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
  void login() {
    LoginRequest request = new LoginRequest("Tony", "asdfg");
    AccessTokenResponse accessTokenResponse = new AccessTokenResponse("asdfghjkl123456789");
    when(authenticationProcessor.login(any())).thenReturn(accessTokenResponse);

    Response response = ext.target(AUTH_ENDPOINT + LOGIN).request().post(Entity.json(request));

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK_200);
    AccessTokenResponse actual = response.readEntity(AccessTokenResponse.class);
    assertThat(actual).usingRecursiveComparison().isEqualTo(accessTokenResponse);
  }
}