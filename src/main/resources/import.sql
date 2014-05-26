-- You can use this file to load seed data into the database using SQL statements
insert into Partner (id, name) values (0, 'First test partner');
insert into Person (id, email, role, partner_id) values (0, 'test@ooo.pl', 'ADMIN', 0);
insert into SupportForm (id, name, partner_id, key) values (0, 'First test support form', 0, '6374fc35-24d2-4cc1-9a57-326696a2f6f6'); 