package io.salopek.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class UsernameAvailabilityRequest {
  @NotEmpty(message = "userName must not be empty!")
  private String userName;

  private UsernameAvailabilityRequest() {
  }

  @JsonCreator
  public UsernameAvailabilityRequest(@JsonProperty("userName") String userName) {
    this.userName = userName;
  }

  @JsonProperty("userName")
  public String getUserName() {
    return userName;
  }
}
