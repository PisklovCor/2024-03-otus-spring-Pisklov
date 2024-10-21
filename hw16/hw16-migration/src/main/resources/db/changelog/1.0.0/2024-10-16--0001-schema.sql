--liquibase formatted sql

--changeset pisklov:2024-10-16-001-table-authors
create table authors
(
    id        bigserial,
    full_name varchar(255),
    primary key (id)
);

--changeset pisklov:2024-10-16-002-table-genres
create table genres
(
    id   bigserial,
    name varchar(255),
    primary key (id)
);

--changeset pisklov:2024-10-16-003-table-books
create table books
(
    id        bigserial,
    title     varchar(255),
    author_id bigint references authors (id) on delete cascade,
    primary key (id)
);

--changeset pisklov:2024-10-16-004-table-books_genres
create table books_genres
(
    book_id  bigint references books (id) on delete cascade,
    genre_id bigint references genres (id) on delete cascade,
    primary key (book_id, genre_id)
);

--changeset pisklov:2024-10-16-005-table-comments
create table comments
(
    id        bigserial,
    content     varchar(255),
    book_id bigint references books (id) on delete cascade,
    primary key (id)
);

--changeset pisklov:2024-10-16-006-table-users
create table users
(
    id        bigserial,
    login     varchar(20) UNIQUE,
    password  varchar(255),
    primary key (id)
);

--changeset pisklov:2024-10-16-007-table-role
create table roles
(
    id              bigserial,
    name            varchar(255) UNIQUE,
    description     varchar(255),
    primary key (id)
);

--changeset pisklov:2024-10-16-008-table-user_role
create table users_roles
(
    user_id     bigint references users (id) on delete cascade,
    role_id     bigint references roles (id) on delete cascade,
    primary key (user_id, role_id)
);