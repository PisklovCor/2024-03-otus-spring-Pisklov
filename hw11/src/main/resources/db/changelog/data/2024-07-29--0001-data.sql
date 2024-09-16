--liquibase formatted sql

--changeset pisklov:2024-07-29-001-authors
insert into authors(full_name)
values ('Erich Maria Remarque'), ('Fyodor Dostoyevsky'), ('Ernest Miller Hemingway');

--changeset pisklov:2024-07-29-002-genres
insert into genres(name)
values ('Fiction'), ('Mystery'), ('Thriller'),
       ('Science fiction'), ('Fantasy'), ('Romance');

--changeset pisklov:2024-07-29-003-books
insert into books(title, author_id)
values ('Im Westen nichts Neues', 1), ('The idiot', 2), ('For Whom the Bell Tolls', 3);

--changeset pisklov:2024-05-17-004-books_genres
insert into books_genres(book_id, genre_id)
values (1, 1),   (1, 2),
       (2, 3),   (2, 4),
       (3, 5),   (3, 6);

--changeset pisklov:2024-07-29-004-comments
insert into comments(content, book_id)
values ('There is something about yourself that you don''t know. - Jason Statham', 1),
       ('People take chances every now and then, and you don''t want to disappoint them. - Jason Statham', 1),
       ('I love people who have a good sense of humor, tell a good story, tell a good joke. - Jason Statham', 2),
       ('When I''m getting ready for a movie, let''s just say my diet is ''The Antisocial Diet.- Jason Statham', 2),
       ('I''ve come from nowhere, and I''m not shy to go back. - Jason Statham', 3),
       ('You can''t be pretentious about what we do, because at the end of the day, movies are about entertainment,' ||
        ' and if people get 10 dollars'' worth, then that''s okay. - Jason Statham', 3);

--changeset pisklov:2024-09-01-006-users
insert into users(login, password)
values ('admin', '$2a$12$ie9un6RSAMTxoPdF0Y9wEO7Bttm7OnB5qzxBwuOrawMf99mWu1C2W'),
       ('user', '$2a$12$1j/SSj71XdzDh7X63bcMz.EcRRK/GZv3DjEc4LibTnKFpntMgAVby');

--changeset pisklov:2024-09-01-007-role
insert into roles(name, description)
values ('ADMIN', 'Administrator role'),
       ('USER', 'User role');

--changeset pisklov:2024-09-01-008-user_role
insert into users_roles(user_id, role_id)
values (1, 1), (1, 2), (2, 2);