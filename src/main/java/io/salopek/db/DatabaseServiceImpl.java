package io.salopek.db;

import io.salopek.dao.DBDao;
import io.salopek.dao.GameDataDAO;
import io.salopek.dao.GameIdDAO;
import io.salopek.dao.HighScoreDAO;
import io.salopek.dao.PointDataDAO;
import io.salopek.dao.RoundDataDAO;
import io.salopek.dao.UserDataDAO;
import io.salopek.entity.GameDataEntity;
import io.salopek.entity.PointEntity;
import io.salopek.entity.RoundDataEntity;
import io.salopek.entity.UserDataEntity;
import io.salopek.model.HighScoreItem;

import javax.inject.Inject;
import java.util.List;

public class DatabaseServiceImpl implements DatabaseService {
  private final GameDataDAO gameDataDAO;
  private final RoundDataDAO roundDataDAO;
  private final PointDataDAO pointDataDAO;
  private final GameIdDAO gameIdDAO;
  private final UserDataDAO userDataDAO;
  private final DBDao dbDao;
  private final HighScoreDAO highScoreDAO;

  @Inject
  public DatabaseServiceImpl(GameDataDAO gameDataDAO, RoundDataDAO roundDataDAO, PointDataDAO pointDataDAO,
    GameIdDAO gameIdDAO, UserDataDAO userDataDAO, DBDao dbDao, HighScoreDAO highScoreDAO) {
    this.gameDataDAO = gameDataDAO;
    this.roundDataDAO = roundDataDAO;
    this.pointDataDAO = pointDataDAO;
    this.gameIdDAO = gameIdDAO;
    this.userDataDAO = userDataDAO;
    this.dbDao = dbDao;
    this.highScoreDAO = highScoreDAO;
  }

  @Override
  public boolean isConnected() {
    return dbDao.isConnected();
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

  @Override
  public UserDataEntity getUserByUsername(String userName) {
    return userDataDAO.getUserByUserName(userName);
  }

  @Override
  public boolean createNewUser(UserDataEntity userData) {
    return userDataDAO.saveUserData(userData);
  }

  @Override
  public boolean updateAccessTokenByUserId(String accessToken, long userId) {
    return userDataDAO.updateAccessTokenByUserId(accessToken, userId);
  }

  @Override
  public UserDataEntity getUserByAccessToken(String accessToken) {
    return userDataDAO.getUserByAccessToken(accessToken);
  }

  @Override
  public UserDataEntity getUserByUserId(long userId) {
    return userDataDAO.getUserByUserId(userId);
  }

  @Override
  public boolean doesAccessTokenExist(String accessToken) {
    UserDataEntity data = userDataDAO.getUserByAccessToken(accessToken);
    return null != data;
  }

  @Override
  public boolean isUsernameAvailable(String username) {
    UserDataEntity data = userDataDAO.getUserByUserName(username);
    return null == data;
  }

  @Override
  public HighScoreItem getPersonalBestByUserName(String userName) {
    return highScoreDAO.getPersonalBestByUserName(userName);
  }

  @Override
  public List<HighScoreItem> getTopTen() {
    return highScoreDAO.getTopTen();
  }
}