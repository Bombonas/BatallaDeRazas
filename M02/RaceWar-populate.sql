--- Insert data in raceWar

use raceWar;


--- Insert data into races

SET AUTOCOMMIT = 0;
INSERT INTO races (race) VALUES
	('human'),
	('elve'),
	('dwarf'),
    ('boss');
	COMMIT;
	

--- Insert data into weapons

SET AUTOCOMMIT = 0;
INSERT INTO weapons (name, image_path, hp, strength, defense, agility, speed, defeatPoints) VALUES 
	('dagger', 'BatallaDeRazas/src/weapons/dagger.png', 0, 0, 0, 0, 3, 10),
	('sword', 'BatallaDeRazas/src/weapons/sword.png', 0, 1, 0, 0, 1, 10),
	('axe', 'BatallaDeRazas/src/weapons/axe.png', 0, 3, 1, 0, 0, 10),
	('double sword', 'BatallaDeRazas/src/weapons/dualsword.png', 0, 4, 0, 0, 0, 14),
	('scimitar', 'BatallaDeRazas/src/weapons/scimitar.png', 0, 1, 0, 1, 2, 14),
	('bow', 'BatallaDeRazas/src/weapons/bow.png', 0, 2, 0, 0, 4, 15),
	('katana', 'BatallaDeRazas/src/weapons/katana.png', 0, 3, 0, 2, 0, 18),
	('snickersnee', 'BatallaDeRazas/src/weapons/stabby.png', 0, -1, 0, 0, 4, 12),
	('two handed axe', 'BatallaDeRazas/src/weapons/dualaxe.png', 0, 7, 0, -1, 0, 20);
	COMMIT;


--- Insert data into items

SET AUTOCOMMIT = 0;
INSERT INTO items (name, image_path, hp, strength, defense, agility, speed, quality) VALUES 
    ('Life charm', 'BatallaDeRazas/src/items/lifeCharm.png', 10, 0, 0, 0, 0, 1),
    ('Strength potion', 'BatallaDeRazas/src/items/strengthPotion.png', 0, 4, 0, 0, 0, 1),
    ('Havel armor', 'BatallaDeRazas/src/items/havelArmor.png', 0, 0, 2, 0, 0, 0),
    ('Dodge ring', 'BatallaDeRazas/src/items/dodgeRing.png', 0, 0, 0, 2, 1, 2),
    ('Hermes boots', 'BatallaDeRazas/src/items/hermesBoots.png', 0, 0, 1, 0, 3, 2),
    ('Terraria heart', 'BatallaDeRazas/src/items/terrariaHeart.png', 10, 0, 0, 0, 1, 2),
    ('JoJo Bro', 'BatallaDeRazas/src/weapons/jojoBro.png', 0, 7, 0, 0, 1, 3),
    ('Piedra torso dragon', 'BatallaDeRazas/src/items/dragonStone.png', 15, 3, 0, 0, 1, 3),
    ('Orbe Ojo Negro', 'BatallaDeRazas/src/items/blackOrb.png', 10, 4, 0, 0, 1, 3),
    ('Alma guardiana fuego', 'BatallaDeRazas/src/items/fireSoul.png', 0, 40, 0, -2, 0, 4),
    ('Alma de Artorias', 'BatallaDeRazas/src/items/artoriasSoul.png', 0, -1, 0, 3, 20, 4),
    ('ezMode', 'BatallaDeRazas/src/items/ezMode.png', 100, 5, 10, 10, 0, -2),
    ('CPU boost', 'BatallaDeRazas/src/items/cpuBoost.png', 10, 1, 1, 1, 1, -1);
    COMMIT;


--- Insert data into warriors

SET AUTOCOMMIT = 0;
INSERT INTO warriors (race_id, name, idle_animation, attack_animation, death_animation, hp, strength, defense, agility, speed, defeatPoints) VALUES
	(1, 'Zafast', 'human1/idle.png', 'human1/attack.png', 'human1/death.png', 50, 10, 4, 8, 5, 20),
	(1, 'Nana', 'human2/idle.png', 'human2/attack.png', 'human2/death.png', 50, 10, 4, 8, 5, 20),
	(1, 'Geles', 'human3/idle.png', 'human3/attack.png', 'human3/death.png', 50, 10, 4, 8, 5, 20),
	(2, 'Ethdar', 'elf1/idle.png', 'elf1/attack.png', 'elf1/death.png', 40, 8, 3, 10, 7, 19),
	(2, 'Thurin', 'elf2/idle.png', 'elf2/attack.png', 'elf2/death.png', 40, 8, 3, 10, 7, 19),
	(2, 'Lawe', 'elf3/idle.png', 'elf3/attack.png', 'elf3/death.png', 40, 8, 3, 10, 7, 19),
	(3, 'Geirgo', 'dwarf1/idle.png', 'dwarf1/attack.png', 'dwarf1/death.png', 60, 12, 5, 6, 4, 21),
	(3, 'Batyb', 'dwarf2/idle.png', 'dwarf2/attack.png', 'dwarf2/death.png', 60, 12, 5, 6, 4, 21),
	(3, 'Rinbrand', 'dwarf3/idle.png', 'dwarf3/attack.png', 'dwarf3/death.png', 60, 12, 5, 6, 4, 21);
	COMMIT;


--- Insert bosses into warriors
INSERT INTO warriors (race_id, name, idle_animation, attack_animation, death_animation, hp, strength, defense, agility, speed, defeatPoints, playable) VALUES
	(4, 'samurai', 'boss1/idle.png', 'boss1/attack.png', 'boss1/death.png', 80, 12, 4, 6, 6, 100, false),
    (4, 'necromancer', 'boss3/idle.png', 'boss3/attack.png', 'boss3/death.png', 80, 12, 4, 6, 6, 100, false),
    (4, 'fireWorm', 'boss2/idle.png', 'boss2/attack.png', 'boss2/death.png', 80, 12, 4, 6, 6, 100, false),
    (4, 'finalBoss', 'boss4/idle.png', 'boss4/attack.png', 'boss4/death.png', 80, 12, 4, 6, 6, 500, false);
	COMMIT;


--- Insert data into weapon_available

SET AUTOCOMMIT = 0;
INSERT INTO weapon_available (weapon_id, race_id) VALUES
	(1, 1),
	(1, 2),
    (1, 4),
	(2, 1),
	(2, 2),
	(2, 3),
    (2, 4),
	(3, 1),
	(3, 3),
    (3, 4),
	(4, 1),
	(4, 2),
    (4, 4),
	(5, 1),
	(5, 2),
    (5, 4),
	(6, 2),
	(7, 1),
    (7, 4),
	(8, 1),
	(8, 2),
	(8, 3),
    (8, 4),
	(9, 3);
	COMMIT;
	
	
--- Insert data into players

SET AUTOCOMMIT = 0;
INSERT INTO players (name, warrior_id, weapon_id) VALUES
	('God', 9, 9),
	('Human', 1, 7),
	('Elve', 5, 6);
	COMMIT;
	
	
--- Insert data into battles

SET AUTOCOMMIT = 0;
INSERT INTO battles (player_id) VALUES
	(1),
	(2),
	(3);
	COMMIT;
	

--- Insert data into rounds

SET AUTOCOMMIT = 0;
INSERT INTO rounds (id, battle_id, opponent_id, opponent_weapon_id, injuries_caused, injuries_suffered, battle_points) VALUES
	(1, 1, 9, 9, 60, 59, 41),
	(2, 1, 8, 2, 60, 59, 35),
	(3, 1, 1, 2, 50, 59, 31),
	(1, 2, 9, 9, 59, 50, 0),
	(1, 3, 9, 9, 59, 40, 0);
	COMMIT;