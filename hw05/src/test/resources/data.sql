insert into authors(full_name)
values ('Erich Maria Remarque'), ('Fyodor Dostoyevsky'), ('Ernest Miller Hemingway');

insert into genres(name)
values ('Genre_1'), ('Genre_2'), ('Genre_3'),
       ('Genre_4'), ('Genre_5'), ('Genre_6');

insert into books(title, author_id)
values ('Im Westen nichts Neues', 1), ('The idiot', 2), ('For Whom the Bell Tolls', 3);

insert into books_genres(book_id, genre_id)
values (1, 1),   (1, 2),
       (2, 3),   (2, 4),
       (3, 5),   (3, 6);
