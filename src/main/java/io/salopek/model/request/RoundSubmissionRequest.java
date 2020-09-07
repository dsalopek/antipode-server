package io.salopek.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.salopek.model.Point;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RoundSubmissionRequest {

  @NotEmpty(message = "gameUUID must not be empty!")
  private String gameUUID;

  @NotNull(message = "origin must not be empty!")
  private Point origin;

  @NotNull(message = "submission must not be empty!")
  private Point submission;

  private RoundSubmissionRequest() {
  }

  @JsonCreator
  public RoundSubmissionRequest(@JsonProperty("gameUUID") String gameUUID, @JsonProperty("origin") Point origin,
    @JsonProperty("submission") Point submission) {
    this.gameUUID = gameUUID;
    this.origin = origin;
    this.submission = submission;
  }

  @JsonProperty("gameUUID")
  public String getGameUUID() {
    return gameUUID;
  }

  @JsonProperty("origin")
  public Point getOrigin() {
    return origin;
  }

  @JsonProperty("submission")
  public Point getSubmission() {
    return submission;
  }
}
