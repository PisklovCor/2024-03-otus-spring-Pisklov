insert into account_book(created_date, last_modified_date, account_id, book_id)
SELECT now(), null, a.id, 1 FROM account a limit 1;

insert into account_book(created_date, last_modified_date, account_id, book_id)
SELECT now(), null, a.id, 2 FROM account a limit 1;
