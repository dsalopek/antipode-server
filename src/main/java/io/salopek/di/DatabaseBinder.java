package io.salopek.di;

import io.salopek.dao.DBDao;
import io.salopek.dao.GameDataDAO;
import io.salopek.dao.GameIdDAO;
import io.salopek.dao.HighScoreDAO;
import io.salopek.dao.PointDataDAO;
import io.salopek.dao.RoundDataDAO;
import io.salopek.dao.UserDataDAO;
import io.salopek.db.DatabaseService;
import io.salopek.db.DatabaseServiceImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.jdbi.v3.core.Jdbi;

import javax.inject.Singleton;

public class DatabaseBinder extends AbstractBinder {

  private final Jdbi jdbi;

  public DatabaseBinder(Jdbi jdbi) {
    this.jdbi = jdbi;
  }

  @Override
  protected void configure() {
    bind(jdbi.onDemand(GameDataDAO.class)).to(GameDataDAO.class);
    bind(jdbi.onDemand(RoundDataDAO.class)).to(RoundDataDAO.class);
    bind(jdbi.onDemand(PointDataDAO.class)).to(PointDataDAO.class);
    bind(jdbi.onDemand(GameIdDAO.class)).to(GameIdDAO.class);
    bind(jdbi.onDemand(UserDataDAO.class)).to(UserDataDAO.class);
    bind(jdbi.onDemand(DBDao.class)).to(DBDao.class);
    bind(jdbi.onDemand(HighScoreDAO.class)).to(HighScoreDAO.class);
    bind(DatabaseServiceImpl.class).to(DatabaseService.class).in(Singleton.class);
  }
}
