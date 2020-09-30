package io.salopek.processor;

import io.salopek.model.UserData;
import io.salopek.model.request.RoundSubmissionRequest;
import io.salopek.model.response.GameResultsResponse;
import io.salopek.model.response.RoundResponse;

public interface GameProcessor {
  RoundResponse newGame(UserData userData);

  RoundResponse submitRound(RoundSubmissionRequest roundSubmissionRequest);

  GameResultsResponse finishGame(RoundSubmissionRequest roundSubmissionRequest);
}