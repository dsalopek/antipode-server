package io.salopek.mapper;

import io.salopek.entity.GameDataEntity;
import io.salopek.entity.PointEntity;
import io.salopek.model.GameData;
import io.salopek.model.Point;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class ModelMapperTest {

  private static final ModelMapper MAPPER = Mappers.getMapper(ModelMapper.class);

  @Test
  void toGameDataEntity() {

    Timestamp timestamp = Timestamp.from(Instant.now());

    GameData gameData = new GameData("Dylan", timestamp, timestamp);
    GameDataEntity expectedMap = new GameDataEntity(0L, "Dylan", timestamp, timestamp);

    assertThat(MAPPER.toGameDataEntity(gameData)).usingRecursiveComparison().isEqualTo(expectedMap);
  }

  @Test
  void toGameDataEntity_NullInput() {

    Timestamp timestamp = Timestamp.from(Instant.now());

    GameData gameData = null;

    assertThat(MAPPER.toGameDataEntity(gameData)).isNull();
  }

  @Test
  void toPointEntity() {

    Point point = new Point(100, 55);
    PointEntity expectedMap = new PointEntity(0L, 0L, null, 55, 100);

    assertThat(MAPPER.toPointEntity(point)).usingRecursiveComparison().isEqualTo(expectedMap);
  }

  @Test
  void toPointEntity_NullInput() {

    Point point = null;

    assertThat(MAPPER.toPointEntity(point)).isNull();
  }

  @Test
  void toPoint() {
    PointEntity pointEntity = new PointEntity(0L, 0L, null, 55, 100);
    Point expectedMap = new Point(100, 55);

    assertThat(MAPPER.toPoint(pointEntity)).usingRecursiveComparison().isEqualTo(expectedMap);
  }

  @Test
  void toPoint_NullInput() {
    PointEntity pointEntity = null;
    Point expectedMap = new Point(100, 55);

    assertThat(MAPPER.toPoint(pointEntity)).isNull();
  }

}