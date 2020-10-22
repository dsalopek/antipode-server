package io.salopek.model.response;

public class ValidateTokenResponse {

  private boolean isValid;

  private ValidateTokenResponse() {
  }

  public ValidateTokenResponse(boolean isValid) {
    this.isValid = isValid;
  }

  public boolean isValid() {
    return isValid;
  }

  public void setValid(boolean valid) {
    isValid = valid;
  }

  @Override
  public String toString() {
    return "ValidateTokenResponse{" +
      "isValid=" + isValid +
      '}';
  }
}
