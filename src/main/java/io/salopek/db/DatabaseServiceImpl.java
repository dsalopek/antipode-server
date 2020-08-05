package io.salopek.db;

import io.salopek.dao.GameDataDAO;
import io.salopek.dao.GameIdDAO;
import io.salopek.dao.PointDataDAO;
import io.salopek.dao.RoundDataDAO;
import io.salopek.entity.GameDataEntity;
import io.salopek.entity.PointEntity;
import io.salopek.entity.RoundDataEntity;
import io.salopek.logging.Loggable;

import javax.inject.Inject;
import java.util.List;

public class DatabaseServiceImpl implements DatabaseService {
  private final GameDataDAO gameDataDAO;
  private final RoundDataDAO roundDataDAO;
  private final PointDataDAO pointDataDAO;
  private final GameIdDAO gameIdDAO;

  @Inject
  public DatabaseServiceImpl(GameDataDAO gameDataDAO, RoundDataDAO roundDataDAO,
    PointDataDAO pointDataDAO, GameIdDAO gameIdDAO) {
    this.gameDataDAO = gameDataDAO;
    this.roundDataDAO = roundDataDAO;
    this.pointDataDAO = pointDataDAO;
    this.gameIdDAO = gameIdDAO;
  }

  @Loggable
  @Override
  public long saveNewGame(GameDataEntity gameDataEntity) {
    return gameDataDAO.insertGameData(gameDataEntity);
  }

  @Loggable
  @Override
  public long saveNewRound(RoundDataEntity roundDataEntity) {
    return roundDataDAO.insertRoundData(roundDataEntity);
  }

  @Loggable
  @Override
  public long saveNewPoint(PointEntity pointEntity) {
    return pointDataDAO.insertPointData(pointEntity);
  }

  @Loggable
  @Override
  public void saveNewGameId(long gameId, String gameUUID) {
    gameIdDAO.insertGameUUID(gameId, gameUUID);
  }

  @Loggable
  @Override
  public long getGameId(String gameUUID) {
    return gameIdDAO.getGameId(gameUUID);
  }

  @Loggable
  @Override
  public void saveGameData(GameDataEntity gameDataEntity) {
    gameDataDAO.saveGameData(gameDataEntity);
  }

  @Loggable
  @Override
  public GameDataEntity getGameDataByGameId(long gameId) {
    return gameDataDAO.getGameDataByGameId(gameId);
  }

  @Loggable
  @Override
  public List<RoundDataEntity> getRoundDataByGameId(long gameId) {
    return roundDataDAO.getRoundDataByGameId(gameId);
  }

  @Loggable
  @Override
  public List<PointEntity> getPointDataByRoundIds(List<Long> roundIds) {
    return pointDataDAO.getPointsByRoundIds(roundIds);
  }
}