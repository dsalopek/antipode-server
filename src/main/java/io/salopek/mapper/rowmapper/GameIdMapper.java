package io.salopek.mapper.rowmapper;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameIdMapper implements RowMapper<Long> {

  @Override
  public Long map(ResultSet rs, StatementContext ctx) throws SQLException {
    return rs.getLong("game_id");
  }
}