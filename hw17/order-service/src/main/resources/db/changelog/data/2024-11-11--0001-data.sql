--liquibase formatted sql

--changeset pisklov:2024-11-11-001-book_order
insert into book_order(created_date, last_modified_date, login, book_title, status)
values (now(), now(), 'guest', 'Dark Matter', 'CREATED');