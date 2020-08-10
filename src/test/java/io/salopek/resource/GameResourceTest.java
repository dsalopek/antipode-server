package io.salopek.resource;

//import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
//import io.dropwizard.testing.junit5.ResourceExtension;
//import io.salopek.processor.GameProcessor;
//import org.junit.jupiter.api.extension.ExtendWith;

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import io.salopek.model.Point;
import io.salopek.model.request.FinishGameRequest;
import io.salopek.model.request.NewGameRequest;
import io.salopek.model.request.RoundSubmissionRequest;
import io.salopek.model.response.CompletedRoundData;
import io.salopek.model.response.GameResultsResponse;
import io.salopek.model.response.RoundResponse;
import io.salopek.processor.GameProcessorImpl;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import java.util.Collections;
import java.util.List;

import static io.salopek.constant.AntipodeConstants.FINISH_GAME;
import static io.salopek.constant.AntipodeConstants.GAME_ENDPOINT;
import static io.salopek.constant.AntipodeConstants.NEW_GAME;
import static io.salopek.constant.AntipodeConstants.SUBMIT_ROUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(DropwizardExtensionsSupport.class)
class GameResourceTest {
  private static final GameProcessorImpl gameProcessor = mock(GameProcessorImpl.class);
  private static final ResourceExtension ext = ResourceExtension.builder().addResource(new GameResource(gameProcessor))
    .build();

  @Test
  void newGame() {
    NewGameRequest newGameRequest = new NewGameRequest("Kary");
    RoundResponse roundResponse = new RoundResponse("asdf-1234", new Point());

    when(gameProcessor.newGame(any())).thenReturn(roundResponse);

    Response response = ext.target(GAME_ENDPOINT + NEW_GAME).request().post(Entity.json(newGameRequest));

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK_200);
    RoundResponse actual = response.readEntity(RoundResponse.class);
    assertThat(actual).usingRecursiveComparison().isEqualTo(roundResponse);
  }

  @Test
  void submitRound() {
    Point origin = new Point();
    Point submission = new Point();
    RoundSubmissionRequest roundSubmissionRequest = new RoundSubmissionRequest("asdf-1234", origin, submission);
    RoundResponse roundResponse = new RoundResponse("asdf-1234", new Point());

    when(gameProcessor.submitRound(any())).thenReturn(roundResponse);

    Response response = ext.target(GAME_ENDPOINT + SUBMIT_ROUND).request().post(Entity.json(roundSubmissionRequest));

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK_200);
    RoundResponse actual = response.readEntity(RoundResponse.class);
    assertThat(actual).usingRecursiveComparison().isEqualTo(roundResponse);
  }

  @Test
  void finishGame() {
    FinishGameRequest finishGameRequest = new FinishGameRequest("asdf-1234");
    GameResultsResponse gameResultsResponse = new GameResultsResponse("player_name", completedRoundData(), 0);

    when(gameProcessor.finishGame(any())).thenReturn(gameResultsResponse);

    Response response = ext.target(GAME_ENDPOINT + FINISH_GAME).request().post(Entity.json(finishGameRequest));

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK_200);
    GameResultsResponse actual = response.readEntity(GameResultsResponse.class);
    assertThat(actual).usingRecursiveComparison().isEqualTo(gameResultsResponse);
  }

  private List<CompletedRoundData> completedRoundData() {
    Point origin = new Point();
    Point antipode = new Point(180, 0);
    Point submission = new Point(180, 0);
    return Collections
      .singletonList(new CompletedRoundData(origin, antipode, submission, 0));
  }
}