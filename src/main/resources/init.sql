CREATE DATABASE IF NOT EXISTS antipode;
USE antipode;

create table if not exists user_data
(
    user_id      bigint auto_increment not null,
    user_name    varchar(255) null,
    password     varchar(255) null,
    access_token varchar(255) null,
    primary key (user_id)
    );

create table if not exists game_data
(
    game_id  bigint auto_increment not null,
    user_id  bigint null,
    start_ts timestamp null,
    end_ts   timestamp null,
    primary key (game_id),
    constraint fk_user foreign key (user_id)
    references user_data (user_id)
    );

create table if not exists game_id_map
(
    game_id   bigint null,
    game_uuid varchar(255) null,
    constraint fk_game foreign key (game_id)
    references game_data (game_id)
    );

create table if not exists round_data
(
    round_id bigint auto_increment not null,
    game_id  bigint null,
    distance double null,
    primary key (round_id),
    constraint fk_game_round foreign key (game_id)
    references game_data (game_id)
    );

create table if not exists point_data
(
    point_id  bigint auto_increment not null,
    round_id  bigint null,
    type      varchar(255) null,
    latitude  double null,
    longitude double null,
    primary key (point_id),
    constraint fk_round foreign key (round_id)
    references round_data (round_id)
    );

create
or replace view player_highscore as
select row_number()            OVER (ORDER BY `f`.`total_distance` ) AS `rank`,
       `f`.`user_name`      AS `user_name`,
       `f`.`total_distance` AS `total_distance`
from (select `e`.`user_name` AS `user_name`, min(`e`.`total_distance`) AS `total_distance`
      from (select `ud`.`user_name` AS `user_name`, sum(`rd`.`distance`) AS `total_distance`
            from ((`antipode`.`game_data` `gd` left join `antipode`.`round_data` `rd` on ((`gd`.`game_id` = `rd`.`game_id`)))
                     left join `antipode`.`user_data` `ud` on ((`gd`.`user_id` = `ud`.`user_id`)))
            where (`gd`.`end_ts` is not null)
            group by `ud`.`user_id`, `ud`.`user_name`, `gd`.`start_ts`, `gd`.`end_ts`) `e`
      group by `e`.`user_name`
      order by `total_distance`) `f`;
