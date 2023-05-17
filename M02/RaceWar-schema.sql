DROP SCHEMA IF EXISTS raceWar;
CREATE SCHEMA raceWar;
USE raceWar;


--- Structure of table 'races'

CREATE TABLE races (
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    	race VARCHAR(25),
    	PRIMARY KEY (id)
);


--- Structure of table 'weapons'

CREATE TABLE weapons (	
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	name VARCHAR(25) NOT NULL,
	image_path VARCHAR(50) NOT NULL,
	hp INT(11) NOT NULL,
	strength INT(11) NOT NULL,
	defense INT(11) NOT NULL,
	agility INT(11) NOT NULL,
	speed INT(11) NOT NULL,
	defeatPoints INT(11) NOT NULL,
	PRIMARY KEY (id)
	);


--- Structure of table 'items'

CREATE TABLE items (	
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	name VARCHAR(25) NOT NULL,
	image_path VARCHAR(50) NOT NULL,
	hp INT(11) NOT NULL,
	strength INT(11) NOT NULL,
	defense INT(11) NOT NULL,
	agility INT(11) NOT NULL,
	speed INT(11) NOT NULL,
	quality INT(11) NOT NULL,
	PRIMARY KEY (id)
	);
	

--- Structure of table 'warriors'

CREATE TABLE warriors (	
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	race_id BIGINT UNSIGNED NOT NULL,
	name VARCHAR(25) NOT NULL,
	idle_animation VARCHAR(50) NOT NULL,
	attack_animation VARCHAR(50) NOT NULL,
	death_animation VARCHAR(50) NOT NULL,
	hp INT(11) NOT NULL,
	strength INT(11) NOT NULL,
	defense INT(11) NOT NULL,
	agility INT(11) NOT NULL,
	speed INT(11) NOT NULL,
    playable BOOLEAN NOT NULL default TRUE,
	defeatPoints INT(11) NOT NULL,
    FOREIGN KEY (race_id) REFERENCES races(id),
	PRIMARY KEY (id)
	);


--- Structure of table 'players'

CREATE TABLE players (
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	name VARCHAR(25) NOT NULL,
	warrior_id BIGINT UNSIGNED NOT NULL,
	weapon_id BIGINT UNSIGNED NOT NULL,
	CONSTRAINT `fk_warrior_id` FOREIGN KEY (warrior_id) REFERENCES warriors(id) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_weapon_id` FOREIGN KEY (weapon_id) REFERENCES weapons(id) ON DELETE RESTRICT ON UPDATE CASCADE,	
	PRIMARY KEY (id)
	);


--- Structure of table 'weapon_available'

CREATE TABLE weapon_available (
	weapon_id BIGINT UNSIGNED NOT NULL,
	race_id BIGINT UNSIGNED NOT NULL,
	FOREIGN KEY (weapon_id) REFERENCES weapons(id),
	FOREIGN KEY (race_id) REFERENCES races(id),
	PRIMARY KEY (weapon_id, race_id)
	);


--- Structure of table 'battles'

CREATE TABLE battles (
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	player_id BIGINT UNSIGNED NOT NULL,
	FOREIGN KEY (player_id) REFERENCES players(id),
	PRIMARY KEY (id)
	);


--- Structure of table 'rounds'

CREATE TABLE rounds (
	id INT(11) NOT NULL,
	battle_id BIGINT UNSIGNED NOT NULL,
	opponent_id BIGINT UNSIGNED NOT NULL,
	opponent_weapon_id BIGINT UNSIGNED NOT NULL,
	injuries_caused INT(11) NOT NULL,
	injuries_suffered INT(11) NOT NULL,
	battle_points INT(11) NOT NULL,
	FOREIGN KEY (battle_id) REFERENCES battles(id),
	FOREIGN KEY (opponent_id) REFERENCES warriors(id),
	FOREIGN KEY (opponent_weapon_id) REFERENCES weapons(id),
	PRIMARY KEY (id, battle_id)
	);