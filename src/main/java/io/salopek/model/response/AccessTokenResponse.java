package io.salopek.model.response;

public class AccessTokenResponse {
  private String accessToken;

  private AccessTokenResponse() {
  }

  public AccessTokenResponse(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  @Override
  public String toString() {
    return "AccessTokenResponse{" +
      "accessToken='" + accessToken + '\'' +
      '}';
  }
}
