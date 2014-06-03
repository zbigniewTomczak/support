-- You can use this file to load seed data into the database using SQL statements
insert into Preferences (id,  fromName, smtpHost, pop3Host, address, password) values (0, 'Testowy kooperant','smtp.gmail.com', 'pop.gmail.com','system.pomoc@gmail.com', 'tojesthaslo' );
insert into Partner (id, name, preferences_id) values (0, 'Testowy kooperant', 0);
insert into Person (id, email, password, role, partner_id) values (0, 't@t.pl', 'asd', 'ADMIN', 0);
insert into SupportForm (id, name, partner_id, key) values (0, 'Pierwszy prosty formularz', 0, '6374fc35-24d2-4cc1-9a57-326696a2f6f6');

insert into SupportFormResponse (content, email, name, supportForm_id, id) values ('Nie działa drukarka przy stanowisku obsługi klienta.', 'system.pomoc@gmail.com', 'Tadeusz Boniek', '0', 0)
insert into Ticket (date, number, partner_id, status, supportFormResponse_id, id) values ('2014-05-26 22:19:05.000', '1111', 0, 'NEW', 0, 0)