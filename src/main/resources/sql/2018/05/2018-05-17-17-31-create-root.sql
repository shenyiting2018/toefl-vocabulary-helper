CREATE TABLE root(
	id int NOT NULL AUTO_INCREMENT,
	root_string CHAR(255) NOT NULL,
	meanings VARCHAR(20000) NOT NULL,
	UNIQUE KEY(root_string),
	PRIMARY KEY(id)
) ENGINE INNODB 
CHARACTER SET = utf8;

CREATE INDEX root_string_index
on root (root_string);