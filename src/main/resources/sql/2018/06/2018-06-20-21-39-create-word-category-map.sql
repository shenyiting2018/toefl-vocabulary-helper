CREATE TABLE word_category_map(
	id int NOT NULL AUTO_INCREMENT,
	word_id int NOT NULL,
	category_id int NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (word_id) REFERENCES word(id),
	FOREIGN KEY (category_id) REFERENCES category(id)
) ENGINE INNODB
CHARACTER SET = utf8;


CREATE INDEX word_category_map_category_index
on word_category_map(category_id);

CREATE INDEX word_category_map_word_index
on word_category_map(word_id);
