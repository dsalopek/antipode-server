package io.salopek.model.response;

public class ValidateTokenResponse {

  private boolean valid;

  private ValidateTokenResponse() {
  }

  public ValidateTokenResponse(boolean valid) {
    this.valid = valid;
  }

  public boolean isValid() {
    return valid;
  }

  public void setValid(boolean valid) {
    this.valid = valid;
  }

  @Override
  public String toString() {
    return "ValidateTokenResponse{" +
      "valid=" + valid +
      '}';
  }
}
