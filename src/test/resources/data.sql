insert into game_data
(game_id, user_id, start_ts, end_ts)
values
(1, 1, '2020-08-07 15:51:38.053', '2020-08-07 15:51:38.053'),
(2, 2, '2020-08-07 15:51:38.053', '2020-08-07 15:51:38.053'),
(3, 3, '2020-08-07 15:51:38.053', '2020-08-07 15:51:38.053');

insert into user_data
(user_id, user_name, password, access_token)
values
(1, 'Dylan', '', 'TOKEN1'),
(2, 'Jennifer', '', 'TOKEN2'),
(3, 'Alex', '', 'TOKEN3');

insert into game_id_map
(game_id, game_uuid)
values
(1, 'abcd-1234'),
(2, 'efgh-5678'),
(3, 'ijkl-9012');

insert into point_data
(point_id, round_id, type, latitude, longitude)
values
(1, 1, 'ORIGIN', 45, 150),
(2, 1, 'ANTIPODE', -45, -30),
(3, 1, 'SUBMISSION', -46, -33),
(4, 2, 'ORIGIN', 55, 112),
(5, 2, 'ANTIPODE', -55, -68),
(6, 2, 'SUBMISSION', -54, -70),
(7, 3, 'ORIGIN', -36, -13),
(8, 3, 'ANTIPODE', 36, 167),
(9, 3, 'SUBMISSION', 34, 168);

insert into round_data
(round_id, game_id, distance)
values
(1, 1, 23479.00),
(2, 2, 82321.00),
(3, 3, 34523.00);