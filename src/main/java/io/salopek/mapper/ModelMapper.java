package io.salopek.mapper;

import io.salopek.entity.GameDataEntity;
import io.salopek.entity.PointEntity;
import io.salopek.entity.UserDataEntity;
import io.salopek.model.GameData;
import io.salopek.model.Point;
import io.salopek.model.UserData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ModelMapper {

  @Mapping(target = "userId", source = "userData.userId")
  @Mapping(target = "startTime", source = "startTime")
  @Mapping(target = "endTime", source = "endTime")
  @Mapping(target = "gameId", ignore = true)
  GameDataEntity toGameDataEntity(GameData gameData);

  @Mapping(target = "latitude", source = "latitude")
  @Mapping(target = "longitude", source = "longitude")
  @Mapping(target = "pointId", ignore = true)
  @Mapping(target = "roundId", ignore = true)
  @Mapping(target = "type", ignore = true)
  PointEntity toPointEntity(Point point);

  Point toPoint(PointEntity pointEntity);

  @Mapping(target = "userId", source = "userId")
  @Mapping(target = "userName", source = "userName")
  UserData toUserData(UserDataEntity userDataEntity);
}
