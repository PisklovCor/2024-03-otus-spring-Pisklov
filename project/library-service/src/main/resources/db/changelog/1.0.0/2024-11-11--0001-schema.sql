--liquibase formatted sql

--changeset pisklov:2024-11-11-001-table-authors
create table authors
(
    id        bigserial,
    full_name varchar(255),
    primary key (id)
);

--changeset pisklov:2024-11-11-002-table-genres
create table genres
(
    id   bigserial,
    name varchar(255),
    primary key (id)
);

--changeset pisklov:2024-11-11-003-table-books
create table books
(
    id        bigserial,
    title     varchar(255),
    author_id bigint references authors (id) on delete cascade,
    primary key (id)
);

--changeset pisklov:2024-11-11-004-table-books_genres
create table books_genres
(
    book_id  bigint references books (id) on delete cascade,
    genre_id bigint references genres (id) on delete cascade,
    primary key (book_id, genre_id)
);

--changeset pisklov:2024-11-25-005-table-comment
create table comment
(
    id      bigserial,
    login   varchar(255) not null,
    content varchar(255),
    book_id bigint references books (id) on delete cascade,
    primary key (id)
);