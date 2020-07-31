package io.salopek.db;

import io.salopek.dao.GameDataDAO;
import io.salopek.dao.GameIdDAO;
import io.salopek.dao.PointDataDAO;
import io.salopek.dao.RoundDataDAO;
import io.salopek.entity.GameDataEntity;
import io.salopek.entity.PointEntity;
import io.salopek.entity.RoundDataEntity;

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

  @Override
  public long saveNewGame(GameDataEntity gameDataEntity) {
    return gameDataDAO.insertGameData(gameDataEntity);
  }

  @Override
  public long saveNewRound(RoundDataEntity roundDataEntity) {
    return roundDataDAO.insertRoundData(roundDataEntity);
  }

  @Override
  public long saveNewPoint(PointEntity pointEntity) {
    return pointDataDAO.insertPointData(pointEntity);
  }

  @Override
  public void saveNewGameId(long gameId, String gameUUID) {
    gameIdDAO.insertGameUUID(gameId, gameUUID);
  }

  @Override
  public long getGameId(String gameUUID) {
    return gameIdDAO.getGameId(gameUUID);
  }

  @Override
  public void saveGameData(GameDataEntity gameDataEntity) {
    gameDataDAO.saveGameData(gameDataEntity);
  }

  @Override
  public GameDataEntity getGameDataByGameId(long gameId) {
    return gameDataDAO.getGameDataByGameId(gameId);
  }

  @Override
  public List<RoundDataEntity> getRoundDataByGameId(long gameId) {
    return roundDataDAO.getRoundDataByGameId(gameId);
  }

  @Override
  public List<PointEntity> getPointDataByRoundIds(List<Long> roundIds) {
    return pointDataDAO.getPointsByRoundIds(roundIds);
  }
}