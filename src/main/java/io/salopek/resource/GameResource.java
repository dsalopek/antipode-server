package io.salopek.resource;

import io.dropwizard.auth.Auth;
import io.salopek.model.UserData;
import io.salopek.model.request.FinishGameRequest;
import io.salopek.model.request.RoundSubmissionRequest;
import io.salopek.model.response.GameResultsResponse;
import io.salopek.model.response.HighScoreResponse;
import io.salopek.model.response.RoundResponse;
import io.salopek.processor.GameProcessor;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static io.salopek.constant.AntipodeConstants.FINISH_GAME;
import static io.salopek.constant.AntipodeConstants.GAME_ENDPOINT;
import static io.salopek.constant.AntipodeConstants.HIGH_SCORES;
import static io.salopek.constant.AntipodeConstants.NEW_GAME;
import static io.salopek.constant.AntipodeConstants.SUBMIT_ROUND;

@Path(GAME_ENDPOINT)
@PermitAll
@Produces(MediaType.APPLICATION_JSON)
public class GameResource {
  private final GameProcessor gameProcessor;

  @Inject
  public GameResource(GameProcessor gameProcessor) {
    this.gameProcessor = gameProcessor;
  }

  @POST
  @Path(NEW_GAME)
  public Response newGame(@Auth UserData userData) {
    RoundResponse roundResponse = gameProcessor.newGame(userData);
    return Response.ok(roundResponse).build();
  }

  @POST
  @Path(SUBMIT_ROUND)
  public Response submitRound(@NotNull @Valid RoundSubmissionRequest roundSubmission) {
    RoundResponse roundResponse = gameProcessor.submitRound(roundSubmission);

    return Response.ok(roundResponse).build();
  }

  @POST
  @Path(FINISH_GAME)
  public Response finishGame(@NotNull @Valid RoundSubmissionRequest roundSubmissionRequest) {
    GameResultsResponse gameResultsResponse = gameProcessor.finishGame(roundSubmissionRequest);
    return Response.ok(gameResultsResponse).build();
  }

  @GET
  @Path(HIGH_SCORES)
  public Response highScores(@Auth UserData userData) {
    HighScoreResponse highScoreResponse = gameProcessor.highScores(userData);
    return Response.ok(highScoreResponse).build();
  }
}
