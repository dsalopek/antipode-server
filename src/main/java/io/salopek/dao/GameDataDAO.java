package io.salopek.dao;

import io.salopek.entity.GameDataEntity;
import io.salopek.mapper.rowmapper.GameDataMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindMethods;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@RegisterRowMapper(GameDataMapper.class)
public interface GameDataDAO {

  @SqlUpdate("insert into game_data (game_id, user_id, start_ts) values (:getGameId, :getUserId, :getStartTime)")
  @GetGeneratedKeys
  long insertGameData(@BindMethods GameDataEntity gameDataEntity);

  @SqlQuery("select * from game_data where game_id = :gameId")
  GameDataEntity getGameDataByGameId(@Bind("gameId") long gameId);

  @SqlUpdate("update game_data set game_id = :getGameId, user_id = :getUserId, start_ts = :getStartTime, end_ts = :getEndTime where game_id = :getGameId")
  void saveGameData(@BindMethods GameDataEntity gameDataEntity);

}