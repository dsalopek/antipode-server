package io.salopek.db;

import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Environment;
import io.dropwizard.util.Resources;
import io.salopek.constant.PointType;
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
import org.eclipse.jetty.util.component.LifeCycle;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DatabaseServiceImplTest {

  private DatabaseService databaseService;
  private Environment environment;
  private Jdbi jdbi;
  private GameDataDAO gameDataDAO;
  private RoundDataDAO roundDataDAO;
  private PointDataDAO pointDataDAO;
  private GameIdDAO gameIdDAO;
  private UserDataDAO userDataDAO;
  private DBDao dbDao;
  private HighScoreDAO highScoreDao;

  @BeforeEach
  void setUp() throws Exception {
    environment = new Environment("test");

    DataSourceFactory dataSourceFactory = new DataSourceFactory();
    dataSourceFactory.setUrl("jdbc:h2:mem:jdbi3-test");
    dataSourceFactory.setUser("sa");
    dataSourceFactory.setDriverClass("org.h2.Driver");
    dataSourceFactory.asSingleConnectionPool();

    jdbi = new JdbiFactory().build(environment, dataSourceFactory, "h2");
    jdbi.useTransaction(h -> {
      h.createScript(Resources.toString(Resources.getResource("schema.sql"), StandardCharsets.UTF_8)).execute();
      h.createScript(Resources.toString(Resources.getResource("data.sql"), StandardCharsets.UTF_8)).execute();
    });

    gameDataDAO = jdbi.onDemand(GameDataDAO.class);
    roundDataDAO = jdbi.onDemand(RoundDataDAO.class);
    pointDataDAO = jdbi.onDemand(PointDataDAO.class);
    gameIdDAO = jdbi.onDemand(GameIdDAO.class);
    userDataDAO = jdbi.onDemand(UserDataDAO.class);
    dbDao = jdbi.onDemand(DBDao.class);
    highScoreDao = jdbi.onDemand(HighScoreDAO.class);

    for (LifeCycle lc : environment.lifecycle().getManagedObjects()) {
      lc.start();
    }
    databaseService = new DatabaseServiceImpl(gameDataDAO, roundDataDAO, pointDataDAO, gameIdDAO, userDataDAO, dbDao, highScoreDao);
  }

  @AfterEach
  void tearDown() throws Exception {
    for (LifeCycle lc : environment.lifecycle().getManagedObjects()) {
      lc.stop();
    }
  }

  @Test
  void saveNewGame() {
    GameDataEntity expectedData = new GameDataEntity(1L, Timestamp.from(Instant.now()), null);
    long gameId = databaseService.saveNewGame(expectedData);
    expectedData.setGameId(gameId);
    GameDataEntity actualData = databaseService.getGameDataByGameId(gameId);
    assertThat(actualData).usingRecursiveComparison().isEqualTo(expectedData);
  }

  @Test
  void saveNewRound() {
    long gameId = 5L;
    RoundDataEntity expectedData = new RoundDataEntity(gameId, 500);
    long roundId = databaseService.saveNewRound(expectedData);
    expectedData.setRoundId(roundId);
    RoundDataEntity actualData = databaseService.getRoundDataByGameId(gameId).get(0);
    assertThat(actualData).usingRecursiveComparison().isEqualTo(expectedData);
  }

  @Test
  void saveNewPoint() {
    long roundId = 100L;
    PointType type = PointType.ORIGIN;
    PointEntity expectedData = new PointEntity(roundId, type, 45, 49);
    long pointId = databaseService.saveNewPoint(expectedData);
    expectedData.setPointId(pointId);
    PointEntity actualData = databaseService.getPointDataByRoundIds(Collections.singletonList(roundId)).get(0);
    assertThat(actualData).usingRecursiveComparison().isEqualTo(expectedData);
  }

  @Test
  void saveNewGameId() {
    long gameId = 4L;
    String gameUUID = "8372-dhska";
    databaseService.saveNewGameId(gameId, gameUUID);
    assertThat(databaseService.getGameId(gameUUID)).isEqualTo(gameId);
  }

  @Test
  void getGameId() {
    String gameUUID = "abcd-1234";
    assertThat(databaseService.getGameId(gameUUID)).isEqualTo(1L);
  }

  @Test
  void saveGameData() {
    long gameId = 1L;
    Timestamp now = Timestamp.from(Instant.now());
    GameDataEntity expectedData = new GameDataEntity(gameId, 1L, now, now);
    databaseService.saveGameData(expectedData);
    assertThat(databaseService.getGameDataByGameId(gameId)).usingRecursiveComparison().isEqualTo(expectedData);
  }

  @Test
  void getGameDataByGameId() {
    long gameId = 1L;
    Timestamp ts = Timestamp.valueOf("2020-08-07 15:51:38.053");
    GameDataEntity expectedData = new GameDataEntity(gameId, 1L, ts, ts);
    assertThat(databaseService.getGameDataByGameId(gameId)).usingRecursiveComparison().isEqualTo(expectedData);
  }

  @Test
  void getRoundDataByGameId() {
    long gameId = 1L;
    List<RoundDataEntity> expectedData = Collections.singletonList(new RoundDataEntity(1L, 1L, 23479.00));
    assertThat(databaseService.getRoundDataByGameId(gameId)).usingRecursiveComparison().isEqualTo(expectedData);
  }

  @Test
  void getPointDataByRoundIds() {
    List<Long> roundIds = Arrays.asList(1L);
    List<PointEntity> expectedData = Arrays
      .asList(
        new PointEntity(1, 1, PointType.ORIGIN, 45, 150),
        new PointEntity(2, 1, PointType.ANTIPODE, -45, -30),
        new PointEntity(3, 1, PointType.SUBMISSION, -46, -33));
    assertThat(databaseService.getPointDataByRoundIds(roundIds)).usingRecursiveComparison().isEqualTo(expectedData);
  }

  @Test
  void getUserByUsername() {
    String userName = "Dylan";
    assertThat(databaseService.getUserByUsername(userName)).isNotNull();

    String userName2 = "Carol";
    assertThat(databaseService.getUserByUsername(userName2)).isNull();
  }

  @Test
  void createNewUser() {
    UserDataEntity userData = new UserDataEntity(0L, "Rourke", "asdfg", "asdfg");
    assertThat(databaseService.createNewUser(userData)).isTrue();
  }

  @Test
  void updateAccessTokenByUserId() {
    String accessToken = "TOKEN1111";
    long userId = 1L;
    assertThat(databaseService.updateAccessTokenByUserId(accessToken, userId)).isTrue();
  }

  @Test
  void updateAccessTokenByUserId_InvalidId() {
    String accessToken = "TOKEN1111";
    long userId = 15L;
    assertThat(databaseService.updateAccessTokenByUserId(accessToken, userId)).isFalse();
  }

  @Test
  void getUserByAccessToken() {
    String accessToken = "TOKEN1";
    assertThat(databaseService.getUserByAccessToken(accessToken)).isNotNull();
  }

  @Test
  void getUserByAccessToken_InvalidToken() {
    String accessToken = "abcd";
    assertThat(databaseService.getUserByAccessToken(accessToken)).isNull();
  }

  @Test
  void getUserByUserId() {
    long userId = 1L;
    assertThat(databaseService.getUserByUserId(userId)).isNotNull();
  }

  @Test
  void getUserByUserId_InvalidId() {
    long userId = 420L;
    assertThat(databaseService.getUserByUserId(userId)).isNull();
  }
}