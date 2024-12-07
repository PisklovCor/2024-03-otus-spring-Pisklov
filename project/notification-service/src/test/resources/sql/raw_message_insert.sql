insert into raw_message(created_date, last_modified_date, login, content, external_system_name, message_type, status)
values (now(), null, 'user_test', 'Dark Matter', 'ORDER_SERVICE', 'CREATION', 'CREATED'),
       (now(), null, 'user_test', 'Dune: Part Two', 'ORDER_SERVICE', 'CREATION', 'CREATED');