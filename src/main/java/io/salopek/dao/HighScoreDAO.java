package io.salopek.dao;

import io.salopek.mapper.rowmapper.GameIdMapper;
import io.salopek.mapper.rowmapper.HighScoreMapper;
import io.salopek.model.HighScoreItem;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

@RegisterRowMapper(HighScoreMapper.class)
public interface HighScoreDAO {
  @SqlQuery("select * from player_highscore limit 10")
  List<HighScoreItem> getTopTen();

  @SqlQuery("select * from player_highscore where user_name = :userName")
  HighScoreItem getPersonalBestByUserName(@Bind(value = "userName") String userName);
}
