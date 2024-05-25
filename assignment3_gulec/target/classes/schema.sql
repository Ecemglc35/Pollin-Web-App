CREATE TABLE poll (
	pollID Long PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR(50),
	question VARCHAR(255),
	answer1 VARCHAR(255),
	answer2 VARCHAR(255),
	answer3 VARCHAR(255),
	votes1 INT,
	votes2 INT,
	votes3 INT,
	date DATE
);

CREATE TABLE sec_user (
  userId BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  email VARCHAR(75) NOT NULL UNIQUE,
  fullName VARCHAR(100) NOT NULL,
  encryptedPassword VARCHAR(128) NOT NULL,
  enabled           BIT NOT NULL 
);

CREATE TABLE sec_role(
  roleId   BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  roleName VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE user_role
(
  id     BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  userId BIGINT NOT NULL,
  roleId BIGINT NOT NULL
);

ALTER TABLE user_role
  ADD CONSTRAINT user_role_uk UNIQUE (userId, roleId);

ALTER TABLE user_role
  ADD CONSTRAINT user_role_fk1 FOREIGN KEY (userId)
  REFERENCES sec_user (userId);
 
ALTER TABLE user_role
  ADD CONSTRAINT user_role_fk2 FOREIGN KEY (roleId)
  REFERENCES sec_role (roleId);
