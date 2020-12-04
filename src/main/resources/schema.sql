create table user_data
(
    user_id      bigint auto_increment not null,
    user_name    varchar(255)          null,
    password     varchar(255)          null,
    access_token varchar(255)          null,
    primary key (user_id)
);

create table game_data
(
    game_id  bigint auto_increment not null,
    user_id  bigint                null,
    start_ts timestamp             null,
    end_ts   timestamp             null,
    primary key (game_id),
    constraint fk_user foreign key (user_id)
        references user_data(user_id)
);

create table game_id_map
(
    game_id   bigint       null,
    game_uuid varchar(255) null,
    constraint fk_game foreign key (game_id)
        references game_data(game_id)
);

create table round_data
(
    round_id bigint auto_increment not null,
    game_id  bigint                null,
    distance double                null,
    primary key (round_id),
    constraint fk_game foreign key (game_id)
        references game_data(game_id)
);

create table point_data
(
    point_id  bigint auto_increment not null,
    round_id  bigint                null,
    type      varchar(255)          null,
    latitude  double                null,
    longitude double                null,
    primary key (point_id),
    constraint fk_round foreign key (round_id)
        references round_data(round_id)
);

