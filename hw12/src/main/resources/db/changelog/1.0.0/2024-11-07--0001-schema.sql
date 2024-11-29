--liquibase formatted sql

--changeset pisklov:2024-11-07-001-table-authors
create table authors
(
    id        bigserial,
    full_name varchar(255),
    primary key (id)
);

--changeset pisklov:2024-11-07-002-table-genres
create table genres
(
    id   bigserial,
    name varchar(255),
    primary key (id)
);

--changeset pisklov:2024-11-07-003-table-books
create table books
(
    id        bigserial,
    title     varchar(255),
    author_id bigint references authors (id) on delete cascade,
    primary key (id)
);

--changeset pisklov:2024-11-07-004-table-books_genres
create table books_genres
(
    book_id  bigint references books (id) on delete cascade,
    genre_id bigint references genres (id) on delete cascade,
    primary key (book_id, genre_id)
);

--changeset pisklov:2024-11-07-005-table-comments
create table comments
(
    id      bigserial,
    content varchar(255),
    book_id bigint references books (id) on delete cascade,
    primary key (id)
);

--changeset pisklov:2024-11-07-006-table-users
create table users
(
    id       bigserial,
    login    varchar(20) UNIQUE,
    password varchar(255),
    primary key (id)
);

--changeset pisklov:2024-11-07-007-table-role
create table roles
(
    id          bigserial,
    name        varchar(255) UNIQUE,
    description varchar(255),
    primary key (id)
);

--changeset pisklov:2024-11-07-008-table-user_role
create table users_roles
(
    user_id bigint references users (id) on delete cascade,
    role_id bigint references roles (id) on delete cascade,
    primary key (user_id, role_id)
);

--changeset pisklov:2024-11-07-009-table-acl_sid
create table acl_sid
(
    id        bigserial,
    principal boolean      not null,
    sid       varchar(100) not null,
    primary key (id),
    constraint unique_uk_acl_sid unique (sid, principal)
);

--changeset pisklov:2024-11-07-010-table-acl_class
create table acl_class
(
    id           bigserial,
    class        varchar(100) not null,
    class_id_type varchar(100) not null,
    primary key (id),
    constraint unique_uk_acl_class unique (class)
);

--changeset pisklov:2024-11-07-011-table-acl_object_identity
create table acl_object_identity
(
    id                 bigserial,
    object_id_class    bigint references acl_class (id) on delete cascade not null,
    object_id_identity varchar(36)                                        not null,
    parent_object      bigint,
    owner_sid          bigint references acl_sid (id) on delete cascade,
    entries_inheriting boolean                                            not null,
    primary key (id),
    constraint unique_uk_acl_object_identity unique (object_id_class, object_id_identity)
);

--changeset pisklov:2024-11-07-012-table-acl_object_identity-fk_parent_object
alter table acl_object_identity
    add foreign key (parent_object) references acl_object_identity (id) on delete cascade;

--changeset pisklov:2024-11-07-013-table-acl_entry
create table acl_entry
(
    id                  bigserial,
    acl_object_identity bigint references acl_object_identity (id) on delete cascade not null,
    ace_order           int                                                          not null,
    sid                 bigint references acl_sid (id) on delete cascade             not null,
    mask                integer                                                      not null,
    granting            boolean                                                      not null,
    audit_success       boolean                                                      not null,
    audit_failure       boolean                                                      not null,
    primary key (id),
    constraint unique_uk_acl_entry unique (acl_object_identity, ace_order)
);