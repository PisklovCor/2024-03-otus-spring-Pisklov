--liquibase formatted sql

--changeset pisklov:2024-11-14-001-table-account
create table account
(
    id                 bigserial,
    created_date       timestamp           not null,
    last_modified_date timestamp,
    name               varchar(255)        not null,
    surname            varchar(255)        not null,
    login              varchar(255) unique not null,
    mail               varchar(255) unique not null,
    primary key (id)
);

--changeset pisklov:2024-11-14-002-table-account_book
create table account_book
(
    id                 bigserial,
    created_date       timestamp not null,
    last_modified_date timestamp,
    account_id         bigint references account (id) on delete cascade,
    book_id            integer   not null,
    primary key (account_id, book_id)
);
