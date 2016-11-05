-- MySQL Script generated by MySQL Workbench
-- Сб 05 ноя 2016 22:23:56
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema ask
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ask
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ask` DEFAULT CHARACTER SET latin1 ;
USE `ask` ;

-- -----------------------------------------------------
-- Table `ask`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ask`.`users` (
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  `enabled` TINYINT(1) NULL DEFAULT '1',
  `email` VARCHAR(50) NOT NULL,
  `fname` VARCHAR(45) NOT NULL,
  `lname` VARCHAR(45) NOT NULL,
  `location` VARCHAR(60) NULL DEFAULT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `ask`.`questions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ask`.`questions` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `question` TEXT NOT NULL,
  `answer` TEXT NULL DEFAULT NULL,
  `asker` VARCHAR(40) NULL DEFAULT NULL,
  `date` DATE NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id` (`id` ASC),
  INDEX `asker` (`asker` ASC),
  CONSTRAINT `questions_ibfk_1`
    FOREIGN KEY (`asker`)
    REFERENCES `ask`.`users` (`username`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `ask`.`asks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ask`.`asks` (
  `questionId` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`questionId`, `username`),
  UNIQUE INDEX `questionId` (`questionId` ASC),
  INDEX `username` (`username` ASC),
  CONSTRAINT `asks_ibfk_1`
    FOREIGN KEY (`questionId`)
    REFERENCES `ask`.`questions` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `asks_ibfk_2`
    FOREIGN KEY (`username`)
    REFERENCES `ask`.`users` (`username`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `ask`.`authorities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ask`.`authorities` (
  `username` VARCHAR(50) NOT NULL,
  `authority` VARCHAR(50) NOT NULL,
  UNIQUE INDEX `ix_auth_username` (`username` ASC, `authority` ASC),
  CONSTRAINT `fk_authorities_users`
    FOREIGN KEY (`username`)
    REFERENCES `ask`.`users` (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
