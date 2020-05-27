CREATE DATABASE IF NOT EXISTS cab302;

USE cab302;

DROP TABLE IF EXISTS `cab302`.`names`;

CREATE TABLE  IF NOT EXISTS `cab302`.`names` (
  `name` varchar(45) NOT NULL default '',
  `age` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

DELIMITER $$

DROP PROCEDURE IF EXISTS `cab302`.`display` $$
CREATE PROCEDURE `cab302`.`display` ()
BEGIN
  SELECT * FROM names;
END $$

DROP PROCEDURE IF EXISTS `cab302`.`addName` $$
CREATE PROCEDURE `cab302`.`addName` (IN name VARCHAR(45), IN age INT)
BEGIN
  INSERT INTO names VALUES(name, age);
END $$

DROP PROCEDURE IF EXISTS `cab302`.`deletePerson` $$
CREATE PROCEDURE `cab302`.`deletePerson` (IN name VARCHAR(45))
BEGIN
  DELETE FROM names WHERE names.name=name;
END $$

DROP PROCEDURE IF EXISTS `cab302`.`updateAge` $$
CREATE PROCEDURE `cab302`.`updateAge` (IN age INT, IN name VARCHAR(45))
BEGIN
  UPDATE names SET names.age=age WHERE names.name=name;
END $$

DELIMITER ;

call addName('John Howard', 75);
call addName('Peter Beattie', 62);
call addName('Tony Abbott', 57);
call addName('Kevin Rudd', 57);
