--liquibase formatted sql

--changeset pisklov:2024-11-14-001-account
insert into account(created_date, last_modified_date, name, surname, login, mail)
values (now(), now(), 'user_name', 'user_surname', 'user', 'user@protonmail.com');