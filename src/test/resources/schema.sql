create table game_data
(
    game_id  bigint auto_increment not null,
    user_id  bigint                null,
    start_ts timestamp             null,
    end_ts   timestamp             null
);

create table user_data
(
    user_id      bigint auto_increment not null,
    user_name    varchar(255)          null,
    password     varchar(255)          null,
    access_token varchar(255)          null
);

create table game_id_map
(
    game_id   bigint       null,
    game_uuid varchar(255) null
);

create table point_data
(
    point_id  bigint auto_increment not null,
    round_id  bigint                null,
    type      varchar(255)          null,
    latitude  double                null,
    longitude double                null
);

create table round_data
(
    round_id bigint auto_increment not null,
    game_id  bigint                null,
    distance double                null
);

