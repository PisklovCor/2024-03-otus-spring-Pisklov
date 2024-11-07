insert into authors(full_name)
values ('Author_1'),
       ('Author_2'),
       ('Author_3');

insert into genres(name)
values ('Genre_1'),
       ('Genre_2'),
       ('Genre_3'),
       ('Genre_4'),
       ('Genre_5'),
       ('Genre_6');

insert into books(title, author_id)
values ('BookTitle_1', 1),
       ('BookTitle_2', 2),
       ('BookTitle_3', 3);

insert into books_genres(book_id, genre_id)
values (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (3, 6);

insert into comments(content, book_id)
values ('Content_1', 1),
       ('Content_2', 1),
       ('Content_3', 2),
       ('Content_4', 2),
       ('Content_5', 3),
       ('Content_6', 3);

insert into users(login, password)
values ('admin_test', '$2a$12$B5qBGF0j6kNC61rd1r1Z4.T/FfDZz.MKdxf1QHfmFDGKklpoRG8i2'),
       ('user_test', '$2a$12$2tcN8kkCE7dcxyXNB9nTV.hKgNPBXIcHfplo0nynixi2BqVsseX1q');

insert into roles(name, description)
values ('ADMIN', 'Administrator role'),
       ('USER', 'User role'),
       ('LIBRARIAN', 'Head of the library role');

insert into users_roles(user_id, role_id)
values (1, 1),
       (1, 2),
       (2, 2),
       (2, 3);

-- позволяет идентифицировать security identity (роль или пользователь)
insert into acl_sid (principal, sid)
-- principal – определяет тип security identity (0/1 – роль/имя пользователя)
-- sid – содержит security identity
values (1, 'admin'),
       (1, 'user'),
       (0, 'ROLE_EDITOR');

-- идентифицирует тип сущности
insert into acl_class (class, class_id_type)
values ('ru.otus.hw.models.Book', 'java.lang.Long');

-- содержит информацию о всех сущностях системы
insert into acl_object_identity (object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting)
-- object_id_class – ссылка на acl_class
-- object_id_identity – идентификатор бизнес сущности
-- parent_object – ссылка на родительский acl_object_identity
-- owner_sid – ссылка на acl_sid определяющая владельца объекта
values (1, 1, null, 3, 0),
       (1, 2, null, 3, 0),
       (1, 3, null, 3, 0);

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