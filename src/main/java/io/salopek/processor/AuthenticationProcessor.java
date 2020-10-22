package io.salopek.processor;

import io.salopek.model.request.LoginRequest;
import io.salopek.model.request.RegisterRequest;
import io.salopek.model.request.UsernameAvailabilityRequest;
import io.salopek.model.request.ValidateTokenRequest;
import io.salopek.model.response.AccessTokenResponse;
import io.salopek.model.response.UsernameAvailabilityResponse;
import io.salopek.model.response.ValidateTokenResponse;

public interface AuthenticationProcessor {

  AccessTokenResponse register(RegisterRequest registerRequest);

  AccessTokenResponse login(LoginRequest loginRequest);

  ValidateTokenResponse validateTokenRequest(ValidateTokenRequest validateTokenRequest);

  UsernameAvailabilityResponse availability(UsernameAvailabilityRequest usernameAvailabilityRequest);
}
