--liquibase formatted sql

--changeset pisklov:2024-10-09-001-table-authors
create table authors
(
    id        bigserial,
    full_name varchar(255),
    primary key (id)
);

--changeset pisklov:2024-10-09-002-table-genres
create table genres
(
    id   bigserial,
    name varchar(255),
    primary key (id)
);

--changeset pisklov:2024-10-09-003-table-books
create table books
(
    id        bigserial,
    title     varchar(255),
    author_id bigint references authors (id) on delete cascade,
    primary key (id)
);

--changeset pisklov:2024-10-09-004-table-books_genres
create table books_genres
(
    book_id  bigint references books (id) on delete cascade,
    genre_id bigint references genres (id) on delete cascade,
    primary key (book_id, genre_id)
);

--changeset pisklov:2024-10-09-005-table-comments
create table comments
(
    id        bigserial,
    content     varchar(255),
    book_id bigint references books (id) on delete cascade,
    primary key (id)
);