--liquibase formatted sql

--changeset pisklov:2024-11-18-001-raw_message
insert into raw_message(created_date, last_modified_date, login, content, external_system_name, message_type, status)
values (now(), null, 'user', 'Dark Matter', 'ORDER_SERVICE', 'CREATION', 'CREATED'),
       (now(), null, 'user', 'Dune: Part Two', 'ORDER_SERVICE', 'CREATION', 'CREATED');

