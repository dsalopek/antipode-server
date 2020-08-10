package io.salopek.processor;

import io.salopek.constant.PointType;
import io.salopek.db.DatabaseService;
import io.salopek.entity.GameDataEntity;
import io.salopek.entity.PointEntity;
import io.salopek.entity.RoundDataEntity;
import io.salopek.model.Point;
import io.salopek.model.request.FinishGameRequest;
import io.salopek.model.request.NewGameRequest;
import io.salopek.model.request.RoundSubmissionRequest;
import io.salopek.model.response.CompletedRoundData;
import io.salopek.model.response.GameResultsResponse;
import io.salopek.model.response.RoundResponse;
import io.salopek.util.DistanceCalculator;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameProcessorImplTest {

  private final DistanceCalculator distanceCalculator = mock(DistanceCalculator.class);
  private final DatabaseService databaseService = mock(DatabaseService.class);

  private final GameProcessorImpl gameProcessor = new GameProcessorImpl(distanceCalculator, databaseService);

  @Test
  void newGame() {
    NewGameRequest newGameRequest = new NewGameRequest("Dylan");

    when(databaseService.saveNewGame(any())).thenReturn(1L);
    RoundResponse actualResponse = gameProcessor.newGame(newGameRequest);

    assertThat(actualResponse.getGameUUID()).isNotNull();
    assertThat(actualResponse.getOrigin()).isNotNull();
  }

  @Test
  void submitRound() {
    Point antipode = antipode();
    Point origin = antipode();

    RoundSubmissionRequest roundSubmission = new RoundSubmissionRequest(UUID.randomUUID().toString(), origin, antipode);

    when(distanceCalculator.getDistance(any(), any())).thenReturn(10000d);
    when(databaseService.saveNewRound(any())).thenReturn(1L);
    when(databaseService.getGameId(anyString())).thenReturn(1L);
    when(databaseService.saveNewPoint(any())).thenReturn(1L);

    RoundResponse actualResponse = gameProcessor.submitRound(roundSubmission);

    assertThat(actualResponse.getGameUUID()).isNotNull();
    assertThat(actualResponse.getOrigin()).isNotNull();
  }

  @Test
  void utilizeGameIdCache() {
    NewGameRequest newGameRequest = new NewGameRequest("Dylan");
    when(databaseService.saveNewGame(any())).thenReturn(1L);

    Point antipode = antipode();
    Point origin = antipode();

    when(distanceCalculator.getDistance(any(), any())).thenReturn(10000d);
    when(databaseService.saveNewRound(any())).thenReturn(1L);
    when(databaseService.getGameId(anyString())).thenReturn(1L);
    when(databaseService.saveNewPoint(any())).thenReturn(1L);
    RoundResponse newGameResponse = gameProcessor.newGame(newGameRequest);
    RoundSubmissionRequest roundSubmission = new RoundSubmissionRequest(newGameResponse.getGameUUID(), origin,
      antipode);
    assertDoesNotThrow(() -> gameProcessor.submitRound(roundSubmission));
  }

  @Test
  void finishGame() {
    FinishGameRequest finishGameRequest = new FinishGameRequest("asdf-asdf");
    GameDataEntity gameDataEntity = new GameDataEntity(1L, "Dylan", Timestamp.from(Instant.now()), null);
    List<RoundDataEntity> roundDataEntities = Arrays.asList(
      new RoundDataEntity(1L, 1L, 15000),
      new RoundDataEntity(2L, 1L, 892340),
      new RoundDataEntity(3L, 1L, 34543)
    );
    List<PointEntity> pointEntities = Arrays.asList(
      new PointEntity(1L, 1L, PointType.ORIGIN, 45, 160),
      new PointEntity(2L, 1L, PointType.ANTIPODE, -45, -20),
      new PointEntity(3L, 1L, PointType.SUBMISSION, -43, -21),

      new PointEntity(4L, 2L, PointType.ORIGIN, -40, -112),
      new PointEntity(5L, 2L, PointType.ANTIPODE, 40, -68),
      new PointEntity(6L, 2L, PointType.SUBMISSION, 39, -70),

      new PointEntity(7L, 3L, PointType.ORIGIN, 88, 43),
      new PointEntity(8L, 3L, PointType.ANTIPODE, -88, -137),
      new PointEntity(9L, 3L, PointType.SUBMISSION, -80, -135)
    );

    String expectedPlayerName = gameDataEntity.getPlayerName();
    long expectedTotalDistance = 941883L;

    when(databaseService.getGameId(anyString())).thenReturn(1L);
    when(databaseService.getGameDataByGameId(anyLong())).thenReturn(gameDataEntity);
    when(databaseService.getRoundDataByGameId(anyLong())).thenReturn(roundDataEntities);
    when(databaseService.getPointDataByRoundIds(anyList())).thenReturn(pointEntities);

    GameResultsResponse actualResponse = gameProcessor.finishGame(finishGameRequest);

    assertThat(actualResponse.getPlayerName()).isEqualTo(expectedPlayerName);
    assertThat(actualResponse.getTotalDistance()).isEqualTo(expectedTotalDistance);
    assertThat(actualResponse.getCompletedRoundData().size()).isEqualTo(3);

    for (CompletedRoundData roundData : actualResponse.getCompletedRoundData()) {
      assertThat(roundData.getOrigin()).isNotNull();
      assertThat(roundData.getAntipode()).isNotNull();
      assertThat(roundData.getSubmission()).isNotNull();
    }
  }

  private Point randomPoint() {
    return new Point(153.31738938156604, 40.5233599542849);
  }

  private Point antipode() {
    return new Point(-26.6826106184, 40.5233599542849);
  }

  private Point submission() {
    return new Point(-25.6826106184, 42.5233599542849);
  }

}