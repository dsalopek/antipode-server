package io.salopek.dao;

import io.salopek.mapper.rowmapper.GameIdMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@RegisterRowMapper(GameIdMapper.class)
public interface GameIdDAO {

  @SqlUpdate("insert into game_id_map (game_id, game_uuid) values (:gameId, :gameUUID)")
  void insertGameUUID(@Bind(value = "gameId") long gameId, @Bind(value = "gameUUID") String gameUUID);

  @SqlQuery("select game_id from game_id_map where game_uuid = :gameUUID")
  long getGameId(@Bind(value = "gameUUID") String gameUUID);
}