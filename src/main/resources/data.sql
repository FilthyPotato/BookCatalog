INSERT INTO ROLE(ID, NAME) VALUES(1, 'ROLE_USER');

INSERT INTO USER_PROFILE(ID, EMAIL)
VALUES  (0, 'system'),
        (1, 'test1@test.com');

INSERT INTO USER(ID, USERNAME, EMAIL, PASSWORD, ENABLED, USER_PROFILE_ID)
VALUES  (0, 'system', 'system', '#G#$G#$G#$G#', true, 0),
        (1, 'test1', 'test1@test.com', '$2a$04$wgKdz3gUS/qmmEHL6eelLe0cqqcXzntRuAJZjeMapT0U0pK7FJsr.', true, 1);

INSERT INTO USERS_ROLES(USER_ID, ROLE_ID)
VALUES  (1, 1);

INSERT INTO AUTHOR(ID, NAME)
VALUES  (1, 'Author1'),
        (2, 'Author2');

INSERT INTO GENRE(ID, NAME)
VALUES  (1, 'Genre1'),
        (2, 'Genre2');

INSERT INTO SHELF(ID, NAME, USER_PROFILE_ID)
VALUES  (0, 'systemShelf', 0),
        (1, 'Shelf1', 1),
        (2, 'Shelf2', 1);

INSERT INTO BOOK(ID, TITLE, DESCRIPTION, PAGES, PUBLISHED_DATE, PUBLISHER)
VALUES  (1, 'Title1', 'Description1', 100, '2015-06-12', 'Publisher1'),
        (2, 'Title2', 'Description2', 200, '2015-06-12',  'Publisher2');

INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID)
VALUES  (1, 1),
        (2, 2);

INSERT INTO BOOKS_GENRES (BOOK_ID, GENRE_ID)
VALUES  (1, 1),
        (2, 2);

INSERT INTO SHELVES_BOOKS (SHELF_ID, BOOK_ID)
VALUES  (0, 1),
        (0, 2),
        (1, 2),
        (1, 2);


