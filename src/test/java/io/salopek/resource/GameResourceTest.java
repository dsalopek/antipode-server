package io.salopek.resource;

import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import io.salopek.db.DatabaseService;
import io.salopek.entity.UserDataEntity;
import io.salopek.model.Point;
import io.salopek.model.UserData;
import io.salopek.model.request.FinishGameRequest;
import io.salopek.model.request.RoundSubmissionRequest;
import io.salopek.model.response.CompletedRoundData;
import io.salopek.model.response.GameResultsResponse;
import io.salopek.model.response.RoundResponse;
import io.salopek.processor.GameProcessor;
import io.salopek.security.CoreAuthenticator;
import io.salopek.security.CoreAuthorizer;
import org.eclipse.jetty.http.HttpStatus;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static io.salopek.constant.AntipodeConstants.FINISH_GAME;
import static io.salopek.constant.AntipodeConstants.GAME_ENDPOINT;
import static io.salopek.constant.AntipodeConstants.NEW_GAME;
import static io.salopek.constant.AntipodeConstants.SUBMIT_ROUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(DropwizardExtensionsSupport.class)
class GameResourceTest {
  private static final GameProcessor gameProcessor = mock(GameProcessor.class);
  private static final DatabaseService databaseService = mock(DatabaseService.class);
  private static final ResourceExtension ext = ResourceExtension.builder()
    .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
    .addProvider(new AuthDynamicFeature(new OAuthCredentialAuthFilter.Builder<UserData>()
      .setAuthenticator(new CoreAuthenticator(databaseService))
      .setAuthorizer(new CoreAuthorizer())
      .setPrefix("Bearer")
      .buildAuthFilter()))
    .addProvider(new AuthValueFactoryProvider.Binder<>(UserData.class))
    .addResource(new GameResource(gameProcessor))
    .build();

  @Test
  void newGame() {
    RoundResponse roundResponse = new RoundResponse("asdf-1234", new Point());

    when(gameProcessor.newGame(any())).thenReturn(roundResponse);
    when(databaseService.getUserByAccessToken(anyString())).thenReturn(new UserDataEntity(1L, "Dylan", "", ""));

    Response response = ext.target(GAME_ENDPOINT + NEW_GAME).request().header("Authorization", "Bearer TOKEN1")
      .post(null);

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK_200);
    RoundResponse actual = response.readEntity(RoundResponse.class);
    assertThat(actual).usingRecursiveComparison().isEqualTo(roundResponse);
  }

  @Test
  void newGame_invalidParams() {
    Response response = ext.target(GAME_ENDPOINT + NEW_GAME).request().post(null);
    assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED_401);
  }

  @Test
  void submitRound() {
    Point origin = new Point();
    Point submission = new Point();
    RoundSubmissionRequest roundSubmissionRequest = new RoundSubmissionRequest("asdf-1234", origin, submission);
    RoundResponse roundResponse = new RoundResponse("asdf-1234", new Point());

    when(gameProcessor.submitRound(any())).thenReturn(roundResponse);

    when(databaseService.getUserByAccessToken(anyString())).thenReturn(new UserDataEntity(1L, "Dylan", "", ""));
    Response response = ext.target(GAME_ENDPOINT + SUBMIT_ROUND).request().header("Authorization", "Bearer TOKEN1")
      .post(Entity.json(roundSubmissionRequest));

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK_200);
    RoundResponse actual = response.readEntity(RoundResponse.class);
    assertThat(actual).usingRecursiveComparison().isEqualTo(roundResponse);
  }

  @Test
  void submitRound_invalidParams() {
    Point origin = new Point();
    Point submission = new Point();
    RoundSubmissionRequest request = new RoundSubmissionRequest("1234", origin, submission);
    Response response = ext.target(GAME_ENDPOINT + SUBMIT_ROUND).request().post(Entity.json(request));
    assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED_401);

    when(databaseService.getUserByAccessToken(anyString())).thenReturn(new UserDataEntity(1L, "Dylan", "", ""));

    request = new RoundSubmissionRequest("", origin, submission);
    when(databaseService.getUserByAccessToken(anyString())).thenReturn(new UserDataEntity(1L, "Dylan", "", ""));
    response = ext.target(GAME_ENDPOINT + SUBMIT_ROUND).request().header("Authorization", "Bearer TOKEN1")
      .post(Entity.json(request));
    assertThat(response.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY_422);

    request = new RoundSubmissionRequest("1234", null, submission);
    response = ext.target(GAME_ENDPOINT + SUBMIT_ROUND).request().header("Authorization", "Bearer TOKEN1")
      .post(Entity.json(request));
    assertThat(response.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY_422);

    request = new RoundSubmissionRequest("1234", origin, null);
    response = ext.target(GAME_ENDPOINT + SUBMIT_ROUND).request().header("Authorization", "Bearer TOKEN1")
      .post(Entity.json(request));
    assertThat(response.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY_422);
  }

  @Test
  void finishGame() {
    RoundSubmissionRequest roundSubmission = new RoundSubmissionRequest(UUID.randomUUID().toString(), new Point(), new Point());
    FinishGameRequest finishGameRequest = new FinishGameRequest("asdf-1234");
    GameResultsResponse gameResultsResponse = new GameResultsResponse("player_name", completedRoundData(), 0);

    when(gameProcessor.finishGame(any())).thenReturn(gameResultsResponse);
    when(databaseService.getUserByAccessToken(anyString())).thenReturn(new UserDataEntity(1L, "Dylan", "", ""));

    Response response = ext.target(GAME_ENDPOINT + FINISH_GAME).request().header("Authorization", "Bearer TOKEN1")
      .post(Entity.json(roundSubmission));

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK_200);
    GameResultsResponse actual = response.readEntity(GameResultsResponse.class);
    assertThat(actual).usingRecursiveComparison().isEqualTo(gameResultsResponse);
  }

  @Test
  void finishGame_invalidParams() {

    FinishGameRequest request = new FinishGameRequest("1234");
    when(databaseService.getUserByAccessToken(anyString())).thenReturn(new UserDataEntity(1L, "Dylan", "", ""));
    Response response = ext.target(GAME_ENDPOINT + FINISH_GAME).request().post(Entity.json(request));
    assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED_401);

    request = new FinishGameRequest("");
    response = ext.target(GAME_ENDPOINT + FINISH_GAME).request().header("Authorization", "Bearer TOKEN1")
      .post(Entity.json(request));
    assertThat(response.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY_422);
  }

  private List<CompletedRoundData> completedRoundData() {
    Point origin = new Point();
    Point antipode = new Point(180, 0);
    Point submission = new Point(180, 0);
    return Collections
      .singletonList(new CompletedRoundData(origin, antipode, submission, 0));
  }
}