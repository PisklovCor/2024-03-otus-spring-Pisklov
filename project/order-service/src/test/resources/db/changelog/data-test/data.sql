--liquibase formatted sql

--changeset pisklov:2024-11-11-001-order
insert into book_order(created_date, last_modified_date, login, book_title, status)
values (now(), now(), 'guest', 'BookTitle_1', 'WAIT'),
       (now(), now(), 'guest', 'BookTitle_2', 'WAIT'),
       (now(), now(), 'user_test', 'BookTitle_3', 'WAIT');