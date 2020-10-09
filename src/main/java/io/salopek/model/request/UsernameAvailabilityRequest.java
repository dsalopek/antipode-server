package io.salopek.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class UsernameAvailabilityRequest {
  @NotEmpty(message = "username must not be empty!")
  private String username;

  private UsernameAvailabilityRequest() {
  }

  @JsonCreator
  public UsernameAvailabilityRequest(@JsonProperty("username") String username) {
    this.username = username;
  }

  @JsonProperty("username")
  public String getUsername() {
    return username;
  }
}
