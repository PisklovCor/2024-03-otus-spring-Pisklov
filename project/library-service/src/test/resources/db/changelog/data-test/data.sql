--liquibase formatted sql

--changeset pisklov:2024-11-11-001-authors
insert into authors(full_name)
values ('Author_1'),
       ('Author_2'),
       ('Author_3');

--changeset pisklov:2024-11-11-002-genres
insert into genres(name)
values ('Genre_1'),
       ('Genre_2'),
       ('Genre_3'),
       ('Genre_4'),
       ('Genre_5'),
       ('Genre_6');

--changeset pisklov:2024-11-11-003-books
insert into books(title, author_id)
values ('BookTitle_1', 1),
       ('BookTitle_2', 2),
       ('BookTitle_3', 3);

--changeset pisklov:2024-11-11-004-books_genres
insert into books_genres(book_id, genre_id)
values (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (3, 6);

--changeset pisklov:2024-11-25-005-comment
insert into comment(login, content, book_id)
values ('user_test', 'Content_1', 1),
       ('user_test', 'Content_2', 1),
       ('user_test', 'Content_3', 2),
       ('user_test', 'Content_4', 2),
       ('user_test', 'Content_5', 3),
       ('user_test', 'Content_6', 3);