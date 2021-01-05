package io.salopek.model.response;

public class UsernameAvailabilityResponse {

  private boolean available;

  private UsernameAvailabilityResponse() {
  }

  public UsernameAvailabilityResponse(boolean available) {
    this.available = available;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  @Override
  public String toString() {
    return "UsernameAvailabilityResponse{" +
      "available=" + available +
      '}';
  }
}
