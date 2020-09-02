package io.salopek.processor;

import io.salopek.constant.PointType;
import io.salopek.db.DatabaseService;
import io.salopek.entity.GameDataEntity;
import io.salopek.entity.PointEntity;
import io.salopek.entity.RoundDataEntity;
import io.salopek.entity.UserDataEntity;
import io.salopek.logging.Loggable;
import io.salopek.mapper.ModelMapper;
import io.salopek.model.GameData;
import io.salopek.model.Point;
import io.salopek.model.UserData;
import io.salopek.model.request.FinishGameRequest;
import io.salopek.model.request.RoundSubmissionRequest;
import io.salopek.model.response.CompletedRoundData;
import io.salopek.model.response.GameResultsResponse;
import io.salopek.model.response.RoundResponse;
import io.salopek.util.DistanceCalculator;
import io.salopek.util.PointUtils;
import org.mapstruct.factory.Mappers;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class GameProcessorImpl implements GameProcessor {
  private final DistanceCalculator distanceCalculator;
  private final DatabaseService databaseService;
  private final ConcurrentMap<String, Long> gameIdCache;
  private static final ModelMapper MAPPER = Mappers.getMapper(ModelMapper.class);

  @Inject
  public GameProcessorImpl(DistanceCalculator distanceCalculator, DatabaseService databaseService) {
    this.distanceCalculator = distanceCalculator;
    this.databaseService = databaseService;
    this.gameIdCache = new ConcurrentHashMap<>();
  }

  @Loggable
  public RoundResponse newGame(UserData userData) {
    String gameUUID = saveNewGame(new GameData(userData));
    Point point = PointUtils.getRandomOrigin();
    return new RoundResponse(gameUUID, point);
  }

  @Loggable
  public RoundResponse submitRound(RoundSubmissionRequest roundSubmissionRequest) {

    processRoundSubmission(roundSubmissionRequest);
    Point point = PointUtils.getRandomOrigin();

    return new RoundResponse(roundSubmissionRequest.getGameUUID(), point);
  }

  @Loggable
  public GameResultsResponse finishGame(FinishGameRequest finishGameRequest) {
    String gameUUID = finishGameRequest.getGameUUID();
    long gameId = getGameId(gameUUID);

    gameIdCache.remove(gameUUID);

    return buildGameResultResponse(gameId);
  }

  private void processRoundSubmission(RoundSubmissionRequest roundSubmission) {

    Point antipode = PointUtils.calculateAntipode(roundSubmission.getOrigin());
    Point submission = roundSubmission.getSubmission();

    double distance = distanceCalculator.getDistance(antipode, submission);

    long gameId = getGameId(roundSubmission.getGameUUID());
    long roundId = saveRound(gameId, distance);

    Map<PointType, Point> pointMap = new EnumMap<>(PointType.class);
    pointMap.put(PointType.ORIGIN, roundSubmission.getOrigin());
    pointMap.put(PointType.ANTIPODE, antipode);
    pointMap.put(PointType.SUBMISSION, submission);

    savePointsFromRound(pointMap, roundId);
  }

  @Loggable
  private GameResultsResponse buildGameResultResponse(long gameId) {
    GameDataEntity gameDataEntity = updateGameData(gameId);
    long userId = gameDataEntity.getUserId();

    UserDataEntity userDataEntity = databaseService.getUserByUserId(userId);
    List<RoundDataEntity> roundDataEntities = databaseService.getRoundDataByGameId(gameId);
    List<Long> roundIds = roundDataEntities.stream().map(RoundDataEntity::getRoundId).collect(Collectors.toList());
    List<PointEntity> pointEntities = databaseService.getPointDataByRoundIds(roundIds);

    double totalDistance = 0;
    List<CompletedRoundData> completedRoundData = new ArrayList<>();
    Map<PointType, Point> pointMap;
    for (RoundDataEntity round : roundDataEntities) {

      pointMap = pointEntities.stream().filter(p -> p.getRoundId() == round.getRoundId())
        .collect(Collectors.toMap(PointEntity::getType,
          MAPPER::toPoint));
      Point origin = pointMap.get(PointType.ORIGIN);
      Point antipode = pointMap.get(PointType.ANTIPODE);
      Point submission = pointMap.get(PointType.SUBMISSION);

      double distance = round.getDistance();
      totalDistance += distance;

      completedRoundData.add(new CompletedRoundData(origin, antipode, submission, distance));
    }

    return new GameResultsResponse(userDataEntity.getUserName(), completedRoundData, totalDistance);
  }

  private GameDataEntity updateGameData(long gameId) {
    GameDataEntity gameDataEntity = databaseService.getGameDataByGameId(gameId);
    gameDataEntity.setEndTime(Timestamp.from(Instant.now()));
    saveGameData(gameDataEntity);
    return gameDataEntity;
  }

  private String saveNewGame(GameData gameData) {
    GameDataEntity gameDataEntity = MAPPER.toGameDataEntity(gameData);
    String gameUUID = UUID.randomUUID().toString();

    long gameId = databaseService.saveNewGame(gameDataEntity);
    saveNewGameUUID(gameId, gameUUID);

    return gameUUID;
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
    gameIdCache.put(gameUUID, gameId);
    databaseService.saveNewGameId(gameId, gameUUID);
  }

  private long getGameId(String gameUUID) {
    if (gameIdCache.containsKey(gameUUID)) {
      return gameIdCache.get(gameUUID);
    } else {
      return databaseService.getGameId(gameUUID);
    }
  }
}