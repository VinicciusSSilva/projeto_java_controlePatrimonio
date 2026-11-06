-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `laboratorio_db` DEFAULT CHARACTER SET utf8 ;
USE `laboratorio_db` ;

-- -----------------------------------------------------
-- Table `laboratorio_db`.`equipamento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`equipamento` (
  `patrimonio` INT NOT NULL CHECK (`patrimonio` > 0) ,
  `descricao` VARCHAR(500) NOT NULL,
  `laboratorio` VARCHAR(45) NOT NULL,
  `situacao` ENUM("Disponível", "Em Uso", "Em Manutenção") NOT NULL,
  PRIMARY KEY (`patrimonio`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`computador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`computador` (
  `memoriaRam` INT NOT NULL CHECK (`memoriaRam` > 0)  ,
  `equipamento_patrimonio` INT NOT NULL,
  PRIMARY KEY (`equipamento_patrimonio`),
  CONSTRAINT `fk_computador_equipamento`
    FOREIGN KEY (`equipamento_patrimonio`)
    REFERENCES `mydb`.`equipamento` (`patrimonio`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`projetor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`projetor` (
  `luminosidade` INT NOT NULL CHECK (`luminosidade` > 0) ,
  `equipamento_patrimonio` INT NOT NULL,
  PRIMARY KEY (`equipamento_patrimonio`),
  CONSTRAINT `fk_projetor_equipamento1`
    FOREIGN KEY (`equipamento_patrimonio`)
    REFERENCES `mydb`.`equipamento` (`patrimonio`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
