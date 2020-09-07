package io.salopek.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class LoginRequest {

  @NotEmpty(message = "userName must not be empty!")
  private String userName;
  @NotEmpty(message = "password must not be empty!")
  private String password;

  private LoginRequest() {
  }

  @JsonCreator
  public LoginRequest(@JsonProperty("userName") String userName, @JsonProperty("password") String password) {
    this.userName = userName;
    this.password = password;
  }

  @JsonProperty("userName")
  public String getUserName() {
    return userName;
  }

  @JsonProperty("password")
  public String getPassword() {
    return password;
  }

  @Override
  public String toString() {
    return "LoginRequest{" +
      "userName='" + userName + '\'' +
      '}';
  }
}
