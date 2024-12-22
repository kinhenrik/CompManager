
insert into admins(admin_firstname, admin_lastname, admin_address, admin_zip, admin_city, admin_country, admin_email)values ('Brandon', 'Gutierrez', '4503 Margaret Street', '77587', 'Houston', 'Texas', 'brandon_gutierrez@teleworm.us');
insert into admins(admin_firstname, admin_lastname, admin_address, admin_zip, admin_city, admin_country, admin_email)values ('Sandra', 'Furrow', '4839 Yorkshire Circle', '27909', 'Elizabeth City', 'North Carolina', 'san_fur@gmail.com');
insert into admins(admin_firstname, admin_lastname, admin_address, admin_zip, admin_city, admin_country, admin_email)values ('Charles', 'White', '2080 Collins Avenue', '43123', 'Grove City', 'Ohio', 'cgwhite@gmail.com');
insert into admins(admin_firstname, admin_lastname, admin_address, admin_zip, admin_city, admin_country, admin_email)values ('Ludwig', 'Svensson', 'Lillesäter 10', '98316', 'Malmberget', 'Sweden', 'ludwig_s@hotmail.com');
insert into admins(admin_firstname, admin_lastname, admin_address, admin_zip, admin_city, admin_country, admin_email)values ('Henrik', 'Kinnunen', 'Gammelekevägen 31', '74961', 'Örsundsbro', 'Sweden', 'henrik.kinnunen.java24@edu.edugrade.se');

insert into Games (game_name) values ( 'StarCraft II');
insert into Games (game_name) values ( 'Counter-Strike: Global Offensive');
insert into Games (game_name) values ( 'Dota 2');
insert into Games (game_name) values ( 'League of Legends');
insert into Games (game_name) values ( 'Rocket League');

insert into Teams (team_name, game_id) values ('The Immortals',1);
insert into Teams (team_name, game_id) values ('The Invincibles',1);
insert into Teams (team_name, game_id) values ('The Warriors',2);
insert into Teams (team_name, game_id) values ('The Ninjas',2);
insert into Teams (team_name, game_id) values ('The Lions',3);
insert into Teams (team_name, game_id) values ('The Monkeys',3);
insert into Teams (team_name, game_id) values ('The Testers',5);

insert into players (player_name, player_surname, player_nickname, team_id)values ('Christoffer', 'Morales', 'BigPapi', 3);
insert into players (player_name, player_surname, player_nickname, team_id)values ('Martin', 'Andersson', 'ilMuro', 1);
insert into players (player_name, player_surname, player_nickname, team_id)values ('Michael', 'Dahl', 'Miklo', 3);
insert into players (player_name, player_surname, player_nickname, team_id)values ('Alexander', 'Rune', 'ChuPapi', 1);
insert into players (player_name, player_surname, player_nickname, team_id)values ('Thomas', 'Nilsson', 'Fantomen', 2);
insert into players (player_name, player_surname, player_nickname, team_id)values ('Erik', 'Svensson', 'Skillz', 2);
insert into players (player_name, player_surname, player_nickname, team_id)values ('Henrik', 'Kinnunen', 'Henkin', 5);
insert into players (player_name, player_surname, player_nickname, team_id)values ('Test', 'Testsson', 'Test', 6);

-- lagmatcher

insert into Matches (match_date, match_type, team1_id, team2_id, game_id)values ('2024-11-09', 'T vs T', 2, 1, 1);
insert into Matches (match_date, match_type, team1_id, team2_id, game_id)values ('2024-10-01', 'T vs T', 1, 2, 1);
insert into Matches (match_date, match_type, team1_id, team2_id, game_id)values ('2024-09-21', 'T vs T', 3, 4, 2);
insert into Matches (match_date, match_type, team1_id, team2_id, game_id)values ('2025-01-03', 'T vs T', 4, 3, 2);
insert into Matches (match_date, match_type, team1_id, team2_id, game_id)values ('2024-11-07', 'T vs T', 5, 6, 3);
insert into Matches (match_date, match_type, team1_id, team2_id, game_id)values ('2025-02-03', 'T vs T', 6, 5, 3);

-- spelarmatcher

insert into Matches (match_date, match_type,player1_id, player2_id, game_id)values ( '2024-01-21', 'P vs P', 1, 3, 2);
insert into Matches (match_date, match_type,player1_id, player2_id, game_id)values ( '2024-02-01', 'P vs P', 3, 1, 2);
insert into Matches (match_date, match_type,player1_id, player2_id, game_id)values ( '2024-07-11', 'P vs P', 2, 5, 1);
insert into Matches (match_date, match_type,player1_id, player2_id, game_id)values ( '2024-04-01', 'P vs P', 4, 6, 1);
insert into Matches (match_date, match_type,player1_id, player2_id, game_id)values ( '2024-02-02', 'P vs P', 7, 8, 3);
insert into Matches (match_date, match_type,player1_id, player2_id, game_id)values ( '2024-10-05', 'P vs P', 8, 7, 3);
