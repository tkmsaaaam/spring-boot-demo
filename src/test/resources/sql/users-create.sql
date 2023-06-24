DELETE FROM USER;
INSERT INTO USER (EMAIL, NAME, PASSWORD, AUTHORITY)
VALUES ("email@example.com", "name", "password", "ROLE_USER");
INSERT INTO USER (EMAIL, NAME, PASSWORD, AUTHORITY)
VALUES ("email1@example.com", "name1", "password1", "ROLE_USER");
