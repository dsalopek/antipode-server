package io.salopek.resource;

import io.dropwizard.auth.Auth;
import io.salopek.logging.Loggable;
import io.salopek.model.UserData;
import io.salopek.model.request.FinishGameRequest;
import io.salopek.model.request.NewGameRequest;
import io.salopek.model.request.RoundSubmissionRequest;
import io.salopek.model.response.GameResultsResponse;
import io.salopek.model.response.RoundResponse;
import io.salopek.processor.GameProcessor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static io.salopek.constant.AntipodeConstants.FINISH_GAME;
import static io.salopek.constant.AntipodeConstants.GAME_ENDPOINT;
import static io.salopek.constant.AntipodeConstants.NEW_GAME;
import static io.salopek.constant.AntipodeConstants.SUBMIT_ROUND;

@Api(value = GAME_ENDPOINT, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
@Path(GAME_ENDPOINT)
@PermitAll
public class GameResource {
  private final GameProcessor gameProcessor;

  @Inject
  public GameResource(GameProcessor gameProcessor) {
    this.gameProcessor = gameProcessor;
  }

  @Loggable
  @POST
  @Path(NEW_GAME)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Submit a player's name to start a new game", response = RoundResponse.class)
  public Response newGame(@Auth UserData userData, @ApiParam(required = true) NewGameRequest newGameRequest) {
    RoundResponse roundResponse = gameProcessor.newGame(userData, newGameRequest);
    return Response.ok(roundResponse).build();
  }

  @Loggable
  @POST
  @Path(SUBMIT_ROUND)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Submit a round of data to be scored", response = RoundResponse.class)
  public Response submitRound(@Auth UserData userData,
    @ApiParam(required = true) RoundSubmissionRequest roundSubmission) {
    RoundResponse roundResponse = gameProcessor.submitRound(roundSubmission);
    return Response.ok(roundResponse).build();
  }

  @Loggable
  @POST
  @Path(FINISH_GAME)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Submit the game id to have a final score calculated", response = GameResultsResponse.class)
  public Response finishGame(@Auth UserData userData, @ApiParam(required = true) FinishGameRequest finishGameRequest) {
    GameResultsResponse gameResultsResponse = gameProcessor.finishGame(finishGameRequest);
    return Response.ok(gameResultsResponse).build();
  }
}
