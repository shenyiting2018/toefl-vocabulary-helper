CREATE TABLE alias(
	id int NOT NULL AUTO_INCREMENT,
	alias_string CHAR(255) NOT NULL,
	UNIQUE KEY(alias_string),
	PRIMARY KEY(id)
) ENGINE INNODB
CHARACTER SET = utf8;

CREATE INDEX alias_string_index
on alias(alias_string);
