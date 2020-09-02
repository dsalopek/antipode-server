package io.salopek.processor;

import io.salopek.model.request.LoginRequest;
import io.salopek.model.request.RegisterRequest;
import io.salopek.model.response.AccessTokenResponse;

public interface AuthenticationProcessor {

  AccessTokenResponse register(RegisterRequest registerRequest);

  AccessTokenResponse login(LoginRequest loginRequest);

}
