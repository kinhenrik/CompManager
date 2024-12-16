insert into admins(admin_firstname, admin_lastname, admin_address, admin_zip, admin_city, admin_country, admin_email)values ('Brandon', 'Gutierrez', '4503 Margaret Street', '77587', 'Houston', 'Texas', 'brandon_gutierrez@teleworm.us');
insert into admins(admin_firstname, admin_lastname, admin_address, admin_zip, admin_city, admin_country, admin_email)values ('Sandra', 'Furrow', '4839 Yorkshire Circle', '27909', 'Elizabeth City', 'North Carolina', 'san_fur@gmail.com');
insert into admins(admin_firstname, admin_lastname, admin_address, admin_zip, admin_city, admin_country, admin_email)values ('Charles', 'White', '2080 Collins Avenue', '43123', 'Grove City', 'Ohio', 'cgwhite@gmail.com');
insert into admins(admin_firstname, admin_lastname, admin_address, admin_zip, admin_city, admin_country, admin_email)values ('Ludwig', 'Svensson', 'Lillesäter 10', '98316', 'Malmberget', 'Sweden', 'ludwig_s@hotmail.com');
insert into admins(admin_firstname, admin_lastname, admin_address, admin_zip, admin_city, admin_country, admin_email)values ('Henrik', 'Kinnunen', 'Gammelekevägen 31', '74961', 'Örsundsbro', 'Sweden', 'henrik.kinnunen.java24@edu.edugrade.se');

insert into Games (game_name) values ( 'Starcraft 2');
insert into Games (game_name) values ( 'Counterstrike Global Offensive');
insert into Games (game_name) values ( 'Dota 2');
insert into Games (game_name) values ( 'League of Legends');
insert into Games (game_name) values ( 'Rocket League');

insert into Teams (team_name, game_id) values ('The Immortals',4);
insert into Teams (team_name, game_id) values ('The Invincibles',2);
insert into Teams (team_name, game_id) values ('The Warriors',5);
insert into Teams (team_name, game_id) values ('The Ninjas',3);
insert into Teams (team_name, game_id) values ('Lions',1);


insert into players (player_name, player_surname, player_nickname, team_id)values ('Christoffer', 'Morales', 'BigPapi', 4);
insert into players (player_name, player_surname, player_nickname, team_id)values ('Martin', 'Andersson', 'ilMuro', 1);
insert into players (player_name, player_surname, player_nickname, team_id)values ('Michael', 'Dahl', 'Miklo', 3);
insert into players (player_name, player_surname, player_nickname, team_id)values ('Alexander', 'Rune', 'ChuPapi', 5);
insert into players (player_name, player_surname, player_nickname, team_id)values ('Thomas', 'Nilsson', 'Fantomen', 2);


-- insert into Matches (match_date, match_type, team1_id, team2_id, winner_id)
-- values ('2024-01-01', 'BO1', 1, 2, 1);
--
-- insert into Matches (match_date, match_type, team1_id, team2_id, winner_id)
-- values ('2024-01-02', 'BO3', 3, 4, 4);
--
-- insert into Matches (match_date, match_type, team1_id, team2_id, winner_id)
-- values ('2024-01-03', 'BO5', 2, 5, 5);
--
-- insert into Matches (match_date, match_type, team1_id, team2_id, winner_id)
-- values ('2024-01-04', 'BO1', 4, 1, 4);
--
-- insert into Matches (match_date, match_type, team1_id, team2_id, winner_id)
-- values ('2024-01-05', 'BO3', 5, 3, 3);
