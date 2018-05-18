CREATE TABLE root(
	id int NOT NULL AUTO_INCREMENT,
	root_string CHAR(255) NOT NULL,
	meaning VARCHAR(60000) NOT NULL,
	UNIQUE KEY(root_string),
	PRIMARY KEY(id)
);

