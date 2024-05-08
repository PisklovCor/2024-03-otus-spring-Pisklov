--liquibase formatted sql

--changeset pisklov:2024-05-08-001-authors
insert into authors(full_name)
values ('Erich Maria Remarque'), ('Fyodor Dostoyevsky'), ('Ernest Miller Hemingway');

--changeset pisklov:2024-05-08-002-genres
insert into genres(name)
values ('Fiction'), ('Mystery'), ('Thriller'),
       ('Science fiction'), ('Fantasy'), ('Romance');

--changeset pisklov:2024-05-08-003-books
insert into books(title, author_id)
values ('Im Westen nichts Neues', 1), ('The idiot', 2), ('For Whom the Bell Tolls', 3);

--changeset pisklov:2024-05-08-004-books_genres
insert into books_genres(book_id, genre_id)
values (1, 1),   (1, 2),
       (2, 3),   (2, 4),
       (3, 5),   (3, 6);