--liquibase formatted sql

--changeset pisklov:2024-11-14-001-account
insert into account(created_date, last_modified_date, name, surname, login, mail)
values (now(), null, 'user_test_name', 'user_test_surname', 'user_test', 'user_test@protonmail.com');

--changeset pisklov:2024-11-16-002-account_book
insert into account_book(created_date, last_modified_date, account_id, book_id)
values (now(), null, 1, 1),
       (now(), null, 1, 2);