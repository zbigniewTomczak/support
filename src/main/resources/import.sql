-- You can use this file to load seed data into the database using SQL statements
insert into Partner (id, name) values (0, 'First test partner');
insert into Person (id, email, role, partner_id) values (0, 'test@ooo.pl', 'ADMIN', 0);
insert into SupportForm (id, name, partner_id, key) values (0, 'First test support form', 0, '6374fc35-24d2-4cc1-9a57-326696a2f6f6');

insert into SupportFormResponse (content, email, name, supportForm_id, id) values ('Content', 'test@opp.pl', 'Tadeusz Boniek', '0', 0)
insert into Ticket (date, number, partner_id, status, supportFormResponse_id, id) values ('2014-05-26 22:19:05.000', '1111', 0, 'NEW', 0, 0)