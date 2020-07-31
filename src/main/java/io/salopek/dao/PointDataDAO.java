package io.salopek.dao;

import io.salopek.entity.PointEntity;
import io.salopek.mapper.rowmapper.PointDataMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.BindList;
import org.jdbi.v3.sqlobject.customizer.BindMethods;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

@RegisterRowMapper(PointDataMapper.class)
public interface PointDataDAO {

  @SqlUpdate("insert into point_data (round_id, type, latitude, longitude) values (:getRoundId, :getType, :getLatitude, :getLongitude)")
  @GetGeneratedKeys
  long insertPointData(@BindMethods PointEntity pointEntity);

  @SqlQuery("select * from point_data where round_id in (<roundIds>) order by round_id")
  List<PointEntity> getPointsByRoundIds(@BindList("roundIds") List<Long> roundIds);

}
