CREATE TABLE user
(
   id INT NOT NULL AUTO_INCREMENT,
   name VARCHAR(100),
   old INT,
   PRIMARY KEY(id)
);

INSERT INTO user(name, old)
VALUES('Taro', 30), ('Jiro', 25), ('Saburo', 22);