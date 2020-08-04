package io.salopek.processor;

import io.salopek.constant.PointType;
import io.salopek.db.DatabaseService;
import io.salopek.entity.GameDataEntity;
import io.salopek.entity.PointEntity;
import io.salopek.entity.RoundDataEntity;
import io.salopek.logging.Loggable;
import io.salopek.mapper.ModelMapper;
import io.salopek.model.response.CompletedRoundData;
import io.salopek.model.request.FinishGameRequest;
import io.salopek.model.GameData;
import io.salopek.model.response.GameResultsResponse;
import io.salopek.model.request.NewGameRequest;
import io.salopek.model.Point;
import io.salopek.model.response.RoundResponse;
import io.salopek.model.request.RoundSubmissionRequest;
import io.salopek.util.DistanceCalculator;
import io.salopek.util.LogUtils;
import io.salopek.util.PointUtils;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class GameProcessor {
  private static final Logger LOGGER = LoggerFactory.getLogger(GameProcessor.class);
  private final DistanceCalculator distanceCalculator;
  private final DatabaseService databaseService;
  private  static final ModelMapper MAPPER = Mappers.getMapper(ModelMapper.class);

  @Inject
  public GameProcessor(DistanceCalculator distanceCalculator, DatabaseService databaseService) {
    this.distanceCalculator = distanceCalculator;
    this.databaseService = databaseService;
  }

  @Loggable
  public RoundResponse newGame(NewGameRequest newGameRequest) {

    long gameId = saveNewGame(new GameData(newGameRequest.getPlayerName()));
    String gameUUID = UUID.randomUUID().toString();

    saveNewGameUUID(gameId, gameUUID);

    Point point = PointUtils.getRandomOrigin();

    RoundResponse roundResponse = new RoundResponse(gameUUID, point);
    return roundResponse;
  }

  @Loggable
  public RoundResponse submitRound(RoundSubmissionRequest roundSubmission) {

    processRoundSubmission(roundSubmission);

    Point point = PointUtils.getRandomOrigin();

    RoundResponse roundResponse = new RoundResponse(roundSubmission.getGameUUID(), point);

    return roundResponse;
  }

  @Loggable
  public GameResultsResponse finishGame(FinishGameRequest finishGameRequest) {
    long gameId = getGameId(finishGameRequest.getGameUUID());
    return buildGameResultResponse(gameId);
  }

  private void processRoundSubmission(RoundSubmissionRequest roundSubmission) {

    Point antipode = PointUtils.calculateAntipode(roundSubmission.getOrigin());
    Point submission = roundSubmission.getSubmission();

    double distance = distanceCalculator.getDistance(antipode, submission);

    long gameId = getGameId(roundSubmission.getGameUUID());
    long roundId = saveRound(gameId, distance);

    Map<PointType, Point> pointMap = new HashMap<>();
    pointMap.put(PointType.ORIGIN, roundSubmission.getOrigin());
    pointMap.put(PointType.ANTIPODE, antipode);
    pointMap.put(PointType.SUBMISSION, submission);

    savePointsFromRound(pointMap, roundId);
  }

  @Loggable
  private GameResultsResponse buildGameResultResponse(long gameId) {
    GameDataEntity gameDataEntity = updateGameData(gameId);
    String playerName = gameDataEntity.getPlayerName();

    List<RoundDataEntity> roundDataEntities = databaseService.getRoundDataByGameId(gameId);
    List<Long> roundIds = roundDataEntities.stream().map(RoundDataEntity::getRoundId).collect(Collectors.toList());
    List<PointEntity> pointEntities = databaseService.getPointDataByRoundIds(roundIds);

    double totalDistance = 0;
    List<CompletedRoundData> completedRoundData = new ArrayList<>();
    for (RoundDataEntity round : roundDataEntities) {

      Point origin = MAPPER.toPoint(pointEntities.stream().filter(p -> p.getRoundId() == round.getRoundId())
        .filter(p -> p.getType() == PointType.ORIGIN).collect(Collectors.toList()).get(0));
      Point antipode = MAPPER.toPoint(pointEntities.stream().filter(p -> p.getRoundId() == round.getRoundId())
        .filter(p -> p.getType() == PointType.ANTIPODE).collect(Collectors.toList()).get(0));
      Point submission = MAPPER.toPoint(pointEntities.stream().filter(p -> p.getRoundId() == round.getRoundId())
        .filter(p -> p.getType() == PointType.SUBMISSION).collect(Collectors.toList()).get(0));

      double distance = round.getDistance();
      totalDistance += distance;

      completedRoundData.add(new CompletedRoundData(origin, antipode, submission, distance));
    }

    return new GameResultsResponse(playerName, completedRoundData, totalDistance);
  }

  private GameDataEntity updateGameData(long gameId) {
    GameDataEntity gameDataEntity = databaseService.getGameDataByGameId(gameId);
    gameDataEntity.setEndTime(Timestamp.from(Instant.now()));
    saveGameData(gameDataEntity);
    return gameDataEntity;
  }

  private long saveNewGame(GameData gameData) {
    GameDataEntity gameDataEntity = MAPPER.toGameDataEntity(gameData);
    return databaseService.saveNewGame(gameDataEntity);
  }

  private void saveGameData(GameDataEntity gameDataEntity) {
    databaseService.saveGameData(gameDataEntity);
  }

  private long saveRound(long gameId, double distance) {
    RoundDataEntity roundDataEntity = new RoundDataEntity(gameId, distance);
    return databaseService.saveNewRound(roundDataEntity);
  }

  private void savePointsFromRound(Map<PointType, Point> pointMap, long roundId) {
    List<PointEntity> points = new ArrayList<>();
    for (Map.Entry<PointType, Point> entry : pointMap.entrySet()) {
      PointEntity pointEntity = MAPPER.toPointEntity(entry.getValue());
      pointEntity.setRoundId(roundId);
      pointEntity.setType(entry.getKey());
      points.add(pointEntity);
    }
    for (PointEntity pointEntity : points) {
      databaseService.saveNewPoint(pointEntity);
    }
  }

  private void saveNewGameUUID(long gameId, String gameUUID) {
    databaseService.saveNewGameId(gameId, gameUUID);
  }

  private long getGameId(String gameUUID) {
    return databaseService.getGameId(gameUUID);
  }
}
