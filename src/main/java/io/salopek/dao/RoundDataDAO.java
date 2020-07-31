package io.salopek.dao;

import io.salopek.entity.RoundDataEntity;
import io.salopek.mapper.rowmapper.RoundDataMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindMethods;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

@RegisterRowMapper(RoundDataMapper.class)
public interface RoundDataDAO {

  @SqlUpdate("insert into round_data (game_id, distance) values (:getGameId, :getDistance)")
  @GetGeneratedKeys
  long insertRoundData(@BindMethods RoundDataEntity roundDataEntity);

  @SqlQuery("select * from round_data where game_id = :gameId")
  List<RoundDataEntity> getRoundDataByGameId(@Bind("gameId") long gameId);
}