CREATE TABLE word_root_map(
	id int NOT NULL AUTO_INCREMENT,
	word_id int NOT NULL,
	root_id int NOT NULL,
	description VARCHAR(20000),
	PRIMARY KEY(id),
	FOREIGN KEY (word_id) REFERENCES word(id),
	FOREIGN KEY (root_id) REFERENCES root(id)
) ENGINE INNODB
CHARACTER SET = utf8;



CREATE INDEX word_root_map_word_index
on word_root_map(word_id);

CREATE INDEX word_root_map_root_index
on word_root_map(root_id);
