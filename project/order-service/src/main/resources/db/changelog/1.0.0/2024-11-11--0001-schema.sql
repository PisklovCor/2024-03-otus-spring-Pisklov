--liquibase formatted sql

--changeset pisklov:2024-11-11-001-table-book_order
create table book_order
(
    id                 bigserial,
    created_date       timestamp not null,
    last_modified_date timestamp,
    login              varchar(255),
    book_title         varchar(255),
    status             varchar(55),
    primary key (id)
);