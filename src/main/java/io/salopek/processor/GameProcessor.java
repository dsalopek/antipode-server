package io.salopek.processor;

import io.salopek.model.request.FinishGameRequest;
import io.salopek.model.request.NewGameRequest;
import io.salopek.model.request.RoundSubmissionRequest;
import io.salopek.model.response.GameResultsResponse;
import io.salopek.model.response.RoundResponse;

public interface GameProcessor {
  RoundResponse newGame(NewGameRequest newGameRequest);
  RoundResponse submitRound(RoundSubmissionRequest roundSubmissionRequest);
  GameResultsResponse finishGame(FinishGameRequest finishGameRequest);
}