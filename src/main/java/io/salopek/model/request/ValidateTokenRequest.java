package io.salopek.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class ValidateTokenRequest {
  @NotEmpty(message="Access token must not be empty")
  private String accessToken;

  private ValidateTokenRequest(){}

  @JsonCreator
  public ValidateTokenRequest(@JsonProperty("accessToken") String accessToken) {
    this.accessToken = accessToken;
  }

  @JsonProperty("accessToken")
  public String getAccessToken() {
    return accessToken;
  }
}
