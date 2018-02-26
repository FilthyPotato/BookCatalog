INSERT INTO ROLE(ID, NAME) VALUES(1, 'ROLE_USER');

INSERT INTO USER_PROFILE(ID, EMAIL)
VALUES  (1, 'test1@test.com');

INSERT INTO USER(ID, USERNAME, EMAIL, PASSWORD, ENABLED, USER_PROFILE_ID)
VALUES  (1, 'test1', 'test1@test.com', '$2a$04$wgKdz3gUS/qmmEHL6eelLe0cqqcXzntRuAJZjeMapT0U0pK7FJsr.', true, 1);

INSERT INTO USERS_ROLES(USER_ID, ROLE_ID)
VALUES  (1, 1);

INSERT INTO AUTHOR(ID, NAME)
VALUES  (1, 'J. K. Rowling'),
        (2, 'J.R.R Tolkien');

INSERT INTO GENRE(ID, NAME)
VALUES  (1, 'Fantasy');

INSERT INTO SHELF(ID, NAME, USER_PROFILE_ID)
VALUES  (1, 'Reading', 1),
        (2, 'Favourite', 1);

INSERT INTO BOOK(ID, TITLE, DESCRIPTION, PAGES, PUBLISHED_DATE, PUBLISHER)
VALUES  (1, 'Harry Potter and the Sorcerer''s Stone', 'Nice book', 250, '1997/06/26', 'Scholastic'),
        (2, 'The Fellowship of the Ring (The Lord of the Rings, #1)', 'Awesome book', 461, '1954/07/29',  'Allen & Unwin');

INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID)
VALUES  (1, 1),
        (2, 2);

INSERT INTO BOOKS_GENRES (BOOK_ID, GENRE_ID)
VALUES  (1, 1),
        (2, 1);

INSERT INTO SHELVES_BOOKS (SHELF_ID, BOOK_ID)
VALUES  (1, 1),
        (1, 2),
        (2, 1);


