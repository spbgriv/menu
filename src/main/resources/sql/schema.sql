CREATE TABLE users (
  id INTEGER NOT NULL AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL,
  password VARCHAR(60) NOT NULL,
  enabled INTEGER NOT NULL DEFAULT 1,
  CONSTRAINT uni_username UNIQUE (username),
  PRIMARY KEY (id));

CREATE TABLE user_roles (
  id INTEGER NOT NULL AUTO_INCREMENT,
  username varchar(45) NOT NULL,
  role varchar(45) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT uni_username_role UNIQUE (role,username),
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username));

CREATE TABLE restaurants (
  id INTEGER NOT NULL AUTO_INCREMENT,
  name varchar(100) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT uni_username_role UNIQUE (name));

CREATE TABLE lunch_menus (
  restaurant_id INTEGER NOT NULL,
  name VARCHAR(100) NOT NULL,
  price DOUBLE NOT NULL DEFAULT 0,
  CONSTRAINT fk_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurants (id));

CREATE TABLE lunch_menus (
  user_id INTEGER NOT NULL,
  restaurant_id INTEGER NOT NULL,
  date DATETIME NOT NULL DEFAULT now(),
  rate DOUBLE NOT NULL DEFAULT 0,
  CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES restaurants (id),
  CONSTRAINT fk_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurants (id));
