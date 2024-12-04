insert into message_to_user(created_date, last_modified_date, login, mail, message, status)
values (now(), null, 'user_test', 'user_test@protonmail.com', 'Dune: Part One', 'CREATED'),
       (now(), null, 'user_test', 'user_test@protonmail.com', 'Dune: Part Two', 'WAIT'),
       (now(), null, 'user_test_test', 'user_test@protonmail.com', 'Dune: Part Three', 'CONFIRMED');