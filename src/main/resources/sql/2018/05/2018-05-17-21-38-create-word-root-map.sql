CREATE TABLE word_root_map(
	id int NOT NULL AUTO_INCREMENT,
	word_id int NOT NULL,
	root_id int NOT NULL,
	description VARCHAR(60000),
	PRIMARY KEY(id),
	FOREIGN KEY (word_id) REFERENCES word(id),
	FOREIGN KEY (root_id) REFERENCES root(id)
) ENGINE INNODB;
