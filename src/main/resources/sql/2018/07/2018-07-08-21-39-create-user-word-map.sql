CREATE TABLE user_word_map(
	id int NOT NULL AUTO_INCREMENT,
	word_id int NOT NULL,
	user_id int NOT NULL,
	list CHAR(255),
	proficiency int default 0,
	PRIMARY KEY(id),
	FOREIGN KEY (word_id) REFERENCES word(id),
	FOREIGN KEY (user_id) REFERENCES user(id)
) ENGINE INNODB
CHARACTER SET = UTF8MB4;


CREATE INDEX user_word_map_user_index
on user_word_map(user_id);

CREATE INDEX word_category_map_word_index
on user_word_map(word_id);
