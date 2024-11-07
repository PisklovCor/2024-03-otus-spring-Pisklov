--liquibase formatted sql

--changeset pisklov:2024-11-07-001-authors
insert into authors(full_name)
values ('Erich Maria Remarque'),
       ('Fyodor Dostoyevsky'),
       ('Ernest Miller Hemingway');

--changeset pisklov:2024-11-07-002-genres
insert into genres(name)
values ('Fiction'),
       ('Mystery'),
       ('Thriller'),
       ('Science fiction'),
       ('Fantasy'),
       ('Romance');

--changeset pisklov:2024-11-07-003-books
insert into books(title, author_id)
values ('Im Westen nichts Neues', 1),
       ('The idiot', 2),
       ('For Whom the Bell Tolls', 3);

--changeset pisklov:2024-11-07-004-books_genres
insert into books_genres(book_id, genre_id)
values (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (3, 6);

--changeset pisklov:2024-11-07-004-comments
insert into comments(content, book_id)
values ('There is something about yourself that you don''t know. - Jason Statham', 1),
       ('People take chances every now and then, and you don''t want to disappoint them. - Jason Statham', 1),
       ('I love people who have a good sense of humor, tell a good story, tell a good joke. - Jason Statham', 2),
       ('When I''m getting ready for a movie, let''s just say my diet is ''The Antisocial Diet.- Jason Statham', 2),
       ('I''ve come from nowhere, and I''m not shy to go back. - Jason Statham', 3),
       ('You can''t be pretentious about what we do, because at the end of the day, movies are about entertainment,' ||
        ' and if people get 10 dollars'' worth, then that''s okay. - Jason Statham', 3);

--changeset pisklov:2024-11-07-006-users
insert into users(login, password)
values ('admin', '$2a$12$ie9un6RSAMTxoPdF0Y9wEO7Bttm7OnB5qzxBwuOrawMf99mWu1C2W'),
       ('user', '$2a$12$1j/SSj71XdzDh7X63bcMz.EcRRK/GZv3DjEc4LibTnKFpntMgAVby');

--changeset pisklov:2024-11-07-007-role
insert into roles(name, description)
values ('ADMIN', 'Administrator role'),
       ('USER', 'User role'),
       ('LIBRARIAN', 'Head of the library role');

--changeset pisklov:2024-11-07-008-user_role
insert into users_roles(user_id, role_id)
values (1, 1),
       (1, 2),
       (2, 2),
       (2, 3);

--changeset pisklov:2024-11-07-009-acl_sid
-- позволяет идентифицировать security identity (роль или пользователь)
insert into acl_sid (principal, sid)
-- principal – определяет тип security identity (0/1 – роль/имя пользователя)
-- sid – содержит security identity
values (1, 'admin'),
       (1, 'user'),
       (0, 'ROLE_EDITOR');

--changeset pisklov:2024-11-07-010-acl_class
-- идентифицирует тип сущности
insert into acl_class (class, class_id_type)
values ('ru.otus.hw.models.Book', 'java.lang.Long');

--changeset pisklov:2024-11-07-011-acl_object_identity
-- содержит информацию о всех сущностях системы
insert into acl_object_identity (object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting)
-- object_id_class – ссылка на acl_class
-- object_id_identity – идентификатор бизнес сущности
-- parent_object – ссылка на родительский acl_object_identity
-- owner_sid – ссылка на acl_sid определяющая владельца объекта
values (1, 1, null, 3, 0),
       (1, 2, null, 3, 0),
       (1, 3, null, 3, 0);

--changeset pisklov:2024-11-07-012-acl_entry
-- содержит права, назначенные для security identity на domain object
insert into acl_entry (acl_object_identity, ace_order, sid, mask,
                       granting, audit_success, audit_failure)
-- acl_object_identity – ссылка на таблицу ACL_OBJECT_IDENTITY (что)
-- ace_order – порядок применения записи
-- sid – ссылка на ACL_SID (кому)
-- mask – маска, определяющая права read/write/create/delete/administer
-- granting – определяет тип назначения (1 – разрешающее, 0 - запрещающее)
-- audit_success – определяет будет ли записываться в лог информация об удачном применении ACE
-- audit_failure - определяет будет ли записываться в лог информация об не удачном применении ACE
values (1, 1, 1, 1, 1, 1, 1),
       (1, 2, 1, 2, 1, 1, 1),
       (1, 3, 3, 1, 1, 1, 1),
       (2, 1, 2, 1, 1, 1, 1),
       (2, 2, 3, 1, 1, 1, 1),
       (3, 1, 3, 1, 1, 1, 1),
       (3, 2, 3, 2, 1, 1, 1);