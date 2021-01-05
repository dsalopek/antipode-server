package io.salopek.mapper.rowmapper;

import io.salopek.model.HighScoreItem;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HighScoreMapper implements RowMapper<HighScoreItem> {

  @Override
  public HighScoreItem map(ResultSet rs, StatementContext ctx) throws SQLException {
    return new HighScoreItem(rs.getLong("rank"), rs.getString("user_name"), rs.getDouble("total_distance"));
  }
}
