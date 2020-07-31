package io.salopek.model.request;

import io.salopek.model.Point;

public class RoundSubmissionRequest {
  private String gameUUID;
  private Point origin;
  private Point submission;

  private RoundSubmissionRequest() {
  }

  public RoundSubmissionRequest(String gameUUID, Point origin, Point submission) {
    this.gameUUID = gameUUID;
    this.origin = origin;
    this.submission = submission;
  }

  public String getGameUUID() {
    return gameUUID;
  }

  public void setGameUUID(String gameUUID) {
    this.gameUUID = gameUUID;
  }

  public Point getOrigin() {
    return origin;
  }

  public void setOrigin(Point origin) {
    this.origin = origin;
  }

  public Point getSubmission() {
    return submission;
  }

  public void setSubmission(Point submission) {
    this.submission = submission;
  }
}
