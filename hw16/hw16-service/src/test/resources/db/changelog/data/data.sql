insert into authors(full_name)
values ('Author_1'), ('Author_2'), ('Author_3');

insert into genres(name)
values ('Genre_1'), ('Genre_2'), ('Genre_3'),
       ('Genre_4'), ('Genre_5'), ('Genre_6');

insert into books(title, author_id)
values ('BookTitle_1', 1), ('BookTitle_2', 2), ('BookTitle_3', 3);

insert into books_genres(book_id, genre_id)
values (1, 1),   (1, 2),
       (2, 3),   (2, 4),
       (3, 5),   (3, 6);

insert into comments(content, book_id)
values ('Content_1', 1),   ('Content_2', 1),
       ('Content_3', 2),   ('Content_4', 2),
       ('Content_5', 3),   ('Content_6', 3);

insert into users(login, password)
values ('admin_test', '$2a$12$B5qBGF0j6kNC61rd1r1Z4.T/FfDZz.MKdxf1QHfmFDGKklpoRG8i2'),
       ('user_test', '$2a$12$2tcN8kkCE7dcxyXNB9nTV.hKgNPBXIcHfplo0nynixi2BqVsseX1q');

insert into roles(name, description)
values ('ADMIN', 'Administrator role'),
       ('USER', 'User role');

insert into users_roles(user_id, role_id)
values (1, 1), (1, 2), (2, 2);