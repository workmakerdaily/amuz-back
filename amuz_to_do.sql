CREATE DATABASE amuz;
USE amuz;

CREATE TABLE users (
  user_id varchar(20) NOT NULL,
  user_name varchar(20) NOT NULL,
  password varchar(255) NOT NULL,
  PRIMARY KEY (user_id)
);

CREATE TABLE to_do_lists (
  id int NOT NULL AUTO_INCREMENT,
  user_id varchar(20) NOT NULL,
  goal text NOT NULL,
  is_checked tinyint NOT NULL DEFAULT '0',
  priority int NOT NULL DEFAULT '0',
  checked_date varchar(10) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT to_do_user_id FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE calendar_data (
  id int NOT NULL AUTO_INCREMENT,
  user_id varchar(20) NOT NULL,
  goal text NOT NULL,
  completed_date varchar(10) NOT NULL,
  PRIMARY KEY (id)
);