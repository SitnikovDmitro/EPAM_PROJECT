DROP ALL OBJECTS ;
CREATE SCHEMA library ;
USE library ;






CREATE TABLE IF NOT EXISTS `library`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role` INT NOT NULL,
  `fine` INT NOT NULL,
  `isBlocked` TINYINT NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `firstname` VARCHAR(50) NOT NULL,
  `lastname` VARCHAR(50) NOT NULL,
  `passwordHash` VARCHAR(44) NOT NULL,
  `lastFineRecalculationDate` DATE NOT NULL,
  PRIMARY KEY (`id`));


CREATE TABLE IF NOT EXISTS `library`.`publishers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`));


CREATE TABLE IF NOT EXISTS `library`.`books` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `publisherId` INT NOT NULL,
  `isbn` INT NOT NULL,
  `totalCopiesNumber` INT NOT NULL,
  `freeCopiesNumber` INT NOT NULL,
  `gradesSum` INT NOT NULL,
  `gradesNumber` INT NOT NULL,
  `genre` INT NOT NULL,
  `language` INT NOT NULL,
  `hasElectronicFormat` TINYINT NOT NULL,
  `isValuable` TINYINT NOT NULL,
  `isDeleted` TINYINT NOT NULL,
  `title` VARCHAR(50) NOT NULL,
  `author` VARCHAR(50) NOT NULL,
  `description` VARCHAR(500) NOT NULL,
  `publicationDate` DATE NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_books_publishers1`
    FOREIGN KEY (`publisherId`)
    REFERENCES `library`.`publishers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE IF NOT EXISTS `library`.`bookmarks` (
  `userId` INT NOT NULL,
  `bookId` INT NOT NULL,
  PRIMARY KEY (`userId`, `bookId`),
  CONSTRAINT `fk_bookmarks_users`
    FOREIGN KEY (`userId`)
    REFERENCES `library`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_bookmarks_books1`
    FOREIGN KEY (`bookId`)
    REFERENCES `library`.`books` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE IF NOT EXISTS `library`.`feedbacks` (
  `userId` INT NOT NULL,
  `bookId` INT NOT NULL,
  `grade` INT NOT NULL,
  `text` VARCHAR(500) NOT NULL,
  `creationDate` DATE NOT NULL,
  PRIMARY KEY (`userId`, `bookId`),
  CONSTRAINT `fk_feedbacks_books1`
    FOREIGN KEY (`bookId`)
    REFERENCES `library`.`books` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_feedbacks_users1`
    FOREIGN KEY (`userId`)
    REFERENCES `library`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE IF NOT EXISTS `library`.`orders` (
  `code` INT NOT NULL AUTO_INCREMENT,
  `userId` INT NOT NULL,
  `bookId` INT NOT NULL,
  `state` INT NOT NULL,
  `creationDate` DATE NULL,
  `deadlineDate` DATE NULL,
  PRIMARY KEY (`code`),
  CONSTRAINT `fk_table4_books1`
    FOREIGN KEY (`bookId`)
    REFERENCES `library`.`books` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_table4_users1`
    FOREIGN KEY (`userId`)
    REFERENCES `library`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE IF NOT EXISTS `library`.`booksCover` (
  `bookId` INT NOT NULL,
  `data` MEDIUMBLOB NOT NULL,
  PRIMARY KEY (`bookId`),
  CONSTRAINT `fk_booksCover_books1`
    FOREIGN KEY (`bookId`)
    REFERENCES `library`.`books` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE IF NOT EXISTS `library`.`booksContent` (
  `bookId` INT NOT NULL,
  `data` LONGBLOB NOT NULL,
  PRIMARY KEY (`bookId`),
  CONSTRAINT `fk_booksContent_books1`
    FOREIGN KEY (`bookId`)
    REFERENCES `library`.`books` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE UNIQUE INDEX `email_UNIQUE` ON `library`.`users` (`email` ASC);
CREATE UNIQUE INDEX `title_UNIQUE` ON `library`.`publishers` (`title` ASC);
CREATE UNIQUE INDEX `isbn_UNIQUE` ON `library`.`books` (`isbn` ASC);
CREATE INDEX `fk_books_publishers1_idx` ON `library`.`books` (`publisherId` ASC);
CREATE INDEX `fk_bookmarks_users_idx` ON `library`.`bookmarks` (`userId` ASC);
CREATE INDEX `fk_bookmarks_books1_idx` ON `library`.`bookmarks` (`bookId` ASC);
CREATE INDEX `fk_feedbacks_users1_idx` ON `library`.`feedbacks` (`userId` ASC);
CREATE INDEX `fk_table4_users1_idx` ON `library`.`orders` (`userId` ASC);
CREATE INDEX `fk_booksCover_books1_idx` ON `library`.`booksCover` (`bookId` ASC);
CREATE INDEX `fk_booksContent_books1_idx` ON `library`.`booksContent` (`bookId` ASC);
