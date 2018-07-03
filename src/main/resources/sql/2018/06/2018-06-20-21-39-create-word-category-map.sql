CREATE TABLE word_category_map(
	id int NOT NULL AUTO_INCREMENT,
	word_id int NOT NULL,
	category_id int NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (word_id) REFERENCES word(id),
	FOREIGN KEY (category_id) REFERENCES category(id)
) ENGINE INNODB
CHARACTER SET = utf8;
