CREATE TABLE root_alias_map(
	id int NOT NULL AUTO_INCREMENT,
	root_id int NOT NULL,
	alias_id int not NULL,
	description VARCHAR(60000),
	PRIMARY KEY(id),
	FOREIGN KEY (root_id) REFERENCES root(id),
	FOREIGN KEY (alias_id) REFERENCES alias(id)
) ENGINE INNODB;
