package io.salopek.model.response;

public class UsernameAvailabilityResponse {

  private boolean isAvailable;

  private UsernameAvailabilityResponse() {
  }

  public UsernameAvailabilityResponse(boolean isAvailable) {
    this.isAvailable = isAvailable;
  }

  public boolean isAvailable() {
    return isAvailable;
  }

  public void setAvailable(boolean available) {
    isAvailable = available;
  }

  @Override
  public String toString() {
    return "UsernameAvailabilityResponse{" +
      "isAvailable=" + isAvailable +
      '}';
  }
}
