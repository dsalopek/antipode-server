package io.salopek.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class FinishGameRequest {
  @NotEmpty(message = "gameUUID may not be empty!")
  private String gameUUID;

  private FinishGameRequest() {
  }

  @JsonCreator
  public FinishGameRequest(@JsonProperty("gameUUID") String gameUUID) {
    this.gameUUID = gameUUID;
  }

  @JsonProperty("gameUUID")
  public String getGameUUID() {
    return gameUUID;
  }
}
