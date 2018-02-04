INSERT INTO ROLE(ID, NAME) VALUES(1, 'ROLE_USER');

INSERT INTO USER(ID, USERNAME, EMAIL, PASSWORD, ENABLED)
VALUES  (1, 'test1', 'test1@test.com', 'test1', true),
        (2, 'test2', 'test2@test.com', 'test2', true);

INSERT INTO USERS_ROLES(USER_ID, ROLE_ID)
VALUES  (1, 1),
        (2, 1);