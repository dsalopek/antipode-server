package io.salopek.db;

import io.salopek.dao.GameDataDAO;
import io.salopek.dao.GameIdDAO;
import io.salopek.dao.PointDataDAO;
import io.salopek.dao.RoundDataDAO;
import io.salopek.dao.UserDataDAO;
import io.salopek.entity.GameDataEntity;
import io.salopek.entity.PointEntity;
import io.salopek.entity.RoundDataEntity;
import io.salopek.entity.UserDataEntity;
import io.salopek.logging.Loggable;

import javax.inject.Inject;
import java.util.List;

public class DatabaseServiceImpl implements DatabaseService {
  private final GameDataDAO gameDataDAO;
  private final RoundDataDAO roundDataDAO;
  private final PointDataDAO pointDataDAO;
  private final GameIdDAO gameIdDAO;
  private final UserDataDAO userDataDAO;

  @Inject
  public DatabaseServiceImpl(GameDataDAO gameDataDAO, RoundDataDAO roundDataDAO,
    PointDataDAO pointDataDAO, GameIdDAO gameIdDAO, UserDataDAO userDataDAO) {
    this.gameDataDAO = gameDataDAO;
    this.roundDataDAO = roundDataDAO;
    this.pointDataDAO = pointDataDAO;
    this.gameIdDAO = gameIdDAO;
    this.userDataDAO = userDataDAO;
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

  @Loggable
  @Override
  public UserDataEntity isUsernameAvailable(String userName) {
    return userDataDAO.isUsernameAvailable(userName);
  }

  @Loggable
  @Override
  public UserDataEntity getUserByUsername(String userName) {
    return userDataDAO.getUserByUserName(userName);
  }

  @Loggable
  @Override
  public boolean createNewUser(UserDataEntity userData) {
    return userDataDAO.saveUserData(userData);
  }

  @Loggable
  @Override
  public boolean updateAccessTokenByUserId(String accessToken, long userId) {
    return userDataDAO.updateAccessTokenByUserId(accessToken, userId);
  }

  @Loggable
  @Override
  public UserDataEntity getUserByAccessToken(String accessToken) {
    return userDataDAO.getUserByAccessToken(accessToken);
  }

  @Loggable
  @Override
  public UserDataEntity getUserByUserId(long userId) {
    return userDataDAO.getUserByUserId(userId);
  }
}