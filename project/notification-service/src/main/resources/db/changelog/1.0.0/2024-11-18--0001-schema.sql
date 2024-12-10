--liquibase formatted sql

--changeset pisklov:2024-11-18-001-table-raw_message
create table raw_message
(
    id                   bigserial,
    created_date         timestamp not null,
    last_modified_date   timestamp,
    login                varchar(255) not null,
    content              text,
    external_system_name varchar(255) not null,
    message_type         varchar(55) not null,
    status               varchar(55) not null,
    primary key (id)
);

--changeset pisklov:2024-11-18-002-table-message_to_user
create table message_to_user
(
    id                 bigserial,
    created_date       timestamp not null,
    last_modified_date timestamp,
    login              varchar(255) not null,
    mail               varchar(255),
    message            text,
    status             varchar(55) not null,
    primary key (id)
);