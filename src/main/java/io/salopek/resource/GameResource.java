package io.salopek.resource;

import io.salopek.logging.Loggable;
import io.salopek.model.request.FinishGameRequest;
import io.salopek.model.response.GameResultsResponse;
import io.salopek.model.request.NewGameRequest;
import io.salopek.model.response.RoundResponse;
import io.salopek.model.request.RoundSubmissionRequest;
import io.salopek.processor.GameProcessor;
import io.salopek.util.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/game")
public class GameResource {
  private static final Logger LOGGER = LoggerFactory.getLogger(GameResource.class);
  private final GameProcessor gameProcessor;
  //  private final GameDataDAO testDAO;

  @Inject
  public GameResource(GameProcessor gameProcessor) {
    this.gameProcessor = gameProcessor;
  }

  @POST
  @Path("/newGame")
  @Produces(MediaType.APPLICATION_JSON)
  @Loggable
  public Response newGame(@Valid NewGameRequest newGameRequest) {
    RoundResponse roundResponse = gameProcessor.newGame(newGameRequest);
    return Response.ok(roundResponse).build();
  }

  @POST
  @Path("/submitRound")
  @Produces(MediaType.APPLICATION_JSON)
  @Loggable
  public Response submitRound(RoundSubmissionRequest roundSubmission) {
    RoundResponse roundResponse = gameProcessor.submitRound(roundSubmission);
    return Response.ok(roundResponse).build();

  }

  @POST
  @Path("/finishGame")
  @Produces(MediaType.APPLICATION_JSON)
  @Loggable
  public Response finishGame(FinishGameRequest finishGameRequest) {
    GameResultsResponse gameResultsResponse = gameProcessor.finishGame(finishGameRequest);

    return Response.ok(gameResultsResponse).build();
  }
}
