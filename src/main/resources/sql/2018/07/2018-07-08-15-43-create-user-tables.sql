CREATE TABLE role (
	id int NOT NULL AUTO_INCREMENT,
	role CHAR(255) NOT NULL,
	UNIQUE KEY(role),
	PRIMARY KEY(id)
) ENGINE INNODB 
CHARACTER SET = UTF8MB4;

CREATE TABLE user(
	id int NOT NULL AUTO_INCREMENT,
	email CHAR(255) NOT NULL,
	first_name CHAR(255) NOT NULL,
	last_name CHAR(255) NOT NULL,
	password CHAR(255) NOT NULL,
	status_code int NOT NULL default 1,
	UNIQUE KEY(email),
	PRIMARY KEY(id)
) ENGINE INNODB
CHARACTER SET = UTF8MB4;


CREATE TABLE user_role (
  id int NOT NULL AUTO_INCREMENT, 
  user_id int NOT NULL,
  role_id int NOT NULL,
  PRIMARY KEY (id)
) ENGINE INNODB
CHARACTER SET = UTF8MB4;
