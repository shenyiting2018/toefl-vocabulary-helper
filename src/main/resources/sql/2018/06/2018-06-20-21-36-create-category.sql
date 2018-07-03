CREATE TABLE category (
	id int NOT NULL AUTO_INCREMENT,
	category_name CHAR(255) NOT NULL,
	UNIQUE KEY(category_name),
	PRIMARY KEY(id)
) ENGINE INNODB 
CHARACTER SET = utf8;

