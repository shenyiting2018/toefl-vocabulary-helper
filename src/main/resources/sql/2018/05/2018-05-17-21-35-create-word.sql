CREATE TABLE word(
	id int NOT NULL AUTO_INCREMENT,
	word_string CHAR(255) NOT NULL,
	meaning VARCHAR(60000),
	UNIQUE KEY(word_string),
	PRIMARY KEY(id)
) ENGINE INNODB;

