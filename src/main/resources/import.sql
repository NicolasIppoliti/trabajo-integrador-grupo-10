-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

drop database if exists almedin;
Create database if not exists almedin;

use almedin;

show tables;

select*from doctors;
select*from	 patients;
select*from appointments;
select*from recipes;
select*from doctor_schedule;
select*from schedules;


INSERT INTO schedules (day, entry_time, departure_time) 
VALUES 
('MONDAY', '08:20:00', '16:00:00'),
('WEDNESDAY', '08:20:00', '17:00:00'),
('SUNDAY', '09:20:00', '15:40:00');

INSERT INTO patients (email, first_name, last_name, password, role, phone)
 VALUES
 ("gepeto@gmail.com", "dante", "Lostas", "pass21132","PATIENT", 1122333444),
 ("lucrecia@gmail.com", "Mirrnos", "Merno", "psass2113","AUTHORIZED_PATIENT", 1522333444),
 ("morticia@gmail.com", "Morti", "Lopardo", "pasdsas2113","ADMIN", 15533444);

 INSERT INTO branches (name, address, city) 
 VALUES 
 ("Hospitalintro", "Rivadavia 4000", "CABA"),
 ("Hosptalito", "Morrison 4000", "Morris"),
 ("Escalera al cielo", "Yatay 2222", "Lugano");


 INSERT INTO doctors (firstName, lastName, dni, speciality, branch_id) 
 VALUES 
 ("Julian", "ocampos", 39999999, "CLINICA_MEDICA", 1),
 ("Nacho", "Dastesr", 39999999, "TRAUMATOLOGIA", 2),
 ("Martin", "Rocoso", 39999999, "GINECOLOGIA", 3);

 INSERT INTO doctor_schedule (doctor_id, schedule_id) VALUES 
 (1, 2),
 (1, 1);
INSERT INTO appointments (doctor_id, patient_id, dateHour, queryReason) VALUES (1,3, "2024-06-16T12:15:50", "Dolor de cabeza");