-- You can use this file to load seed data into the database using SQL statements
insert into Preferences (id,  fromName, smtpHost, pop3Host, address, password) values (0, 'Kwiaciarnia Różyczka','smtp.gmail.com', 'pop.gmail.com','system.pomoc@gmail.com', 'tojesthaslo' );
insert into Partner (id, name, preferences_id) values (0, 'Kwiaciarnia różyczka', 0);
insert into Person (id, name, email, password, role, partner_id, version) values (0, 'Admin', 'admin@example.com', 'asd', 'ADMIN', 0, 1);
insert into FormDefinition (id, filename) values (0, 'simple_contact_form_1.xhtml')
insert into FormPublication (id, name, partner_id, supportFormTemplate_id, key, title, confirmationMessage, width, height) values (0, 'Formularz kontaktowy', 0, 0, '6374fc35-24d2-4cc1-9a57-326696a2f6f6', 'Skontaktuj się z nami poprzez poniższy formularz','Dziękujemy! Twoja wiadomość została wysłana.', 710, 300);

insert into FormResponse (content, email, name, formPublication_id, id) values ('Nie działa drukarka przy stanowisku obsługi klienta.', 'system.pomoc@gmail.com', 'Tadeusz Boniek', '0', 0)
insert into Ticket (date, number, partner_id, status, formResponse_id, id) values ('2014-05-26 22:19:05.000', '1111', 0, 'NEW', 0, 0)