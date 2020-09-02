package io.salopek.dao;

import io.salopek.entity.UserDataEntity;
import io.salopek.mapper.rowmapper.UserDataMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindMethods;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@RegisterRowMapper(UserDataMapper.class)
public interface UserDataDAO {

  @SqlQuery("select * from user_data where user_name = :userName")
  UserDataEntity isUsernameAvailable(@Bind("userName") String userName);

  @SqlQuery("select * from user_data where user_name = :userName")
  UserDataEntity getUserByUserName(@Bind("userName") String userName);

  @SqlQuery("select * from user_data where user_id = :userId")
  UserDataEntity getUserByUserId(@Bind("userId") long userId);

  @SqlUpdate("insert into user_data (user_id, user_name, password, access_token) values (:getUserId, :getUserName, :getPassword, :getAccessToken)")
  boolean saveUserData(@BindMethods UserDataEntity userDataEntity);

  @SqlUpdate("update user_data set access_token = :accessToken where user_id = :userId")
  boolean updateAccessTokenByUserId(@Bind("accessToken") String accessToken, @Bind("userId") long userId);

  @SqlQuery("select * from user_data where access_token = :accessToken")
  UserDataEntity getUserByAccessToken(@Bind("accessToken") String accessToken);
}
