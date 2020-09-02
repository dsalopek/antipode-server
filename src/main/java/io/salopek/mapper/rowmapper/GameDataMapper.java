package io.salopek.mapper.rowmapper;

import io.salopek.entity.GameDataEntity;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameDataMapper implements RowMapper<GameDataEntity> {

  @Override
  public GameDataEntity map(ResultSet rs, StatementContext ctx) throws SQLException {
    return new GameDataEntity(rs.getLong("game_id"), rs.getLong("user_id"), rs.getTimestamp("start_ts"),
      rs.getTimestamp("end_ts"));
  }
}