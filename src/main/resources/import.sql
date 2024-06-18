-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

-- drop database if exists almedin;
-- Create database if not exists almedin;

use almedin;

-- show tables;

-- select*from doctors;
-- select*from	 patients;
-- select*from appointments;
-- select*from recipes;
-- select*from doctor_schedule;
-- select*from schedules;


INSERT INTO schedules (day, entry_time, departure_time) 
VALUES 
('MONDAY', '08:00:00', '17:00:00'),
('TUESDAY', '08:00:00', '17:00:00'),
('WEDNESDAY', '08:00:00', '17:00:00'),
('THURSDAY', '08:00:00', '17:00:00'),
('FRIDAY', '08:00:00', '17:00:00');


INSERT INTO patients (email, first_name, last_name, password, role, phone)
 VALUES
("gepeto@gmail.com", "Dante", "Mirno", "Passw123-", "PATIENT", 1122333444),
("lucrecia@gmail.com", "Lucre", "Yanaris", "Passw1234-", "AUTHORIZED_PATIENT", 1122333445),
("morticia@gmail.com", "Morti", "Lopardo", "Pass321-", "ADMIN", 11223334446),
("peter@gmail.com", "Peter", "Parker", "Pass132-", "PATIENT", 1122333447),
("bruce@gmail.com", "Bruce", "Wayne", "Pass312-", "PATIENT", 1122333448);

 INSERT INTO branches (name, address, city) 
 VALUES 
("Hospitalintro", "Rivadavia 3000", "CABA"),
("Hosptalito", "Morrison 4000", "Morris"),
("Escalera al cielo", "Yatay 2222", "Lugano"),
("Clínica San Martín", "San Martín 100", "Buenos Aires"),
("Centro Médico Integral", "Av. de Mayo 500", "CABA");

 INSERT INTO doctors (firstName, lastName, dni, speciality, branch_id) 
 VALUES 
("Julian", "Ocampos", 39999999, "CLINICA_MEDICA", 1),
("Nacho", "Daster", 39999998, "TRAUMATOLOGIA", 2),
("Martin", "Rocoso", 39999997, "GINECOLOGIA", 3),
("Carolina", "Perez", 39999996, "PEDIATRIA", 1),
("Federico", "Gonzalez", 39999995, "DERMATOLOGIA", 2);
 INSERT INTO doctor_schedule (doctor_id, schedule_id) VALUES 
 (1, 1),
 (1, 2),
 (1, 3),
 (1, 4),
 (1, 5),
 (2, 1),
 (2, 2),
 (2, 3),
 (2, 4),
 (2, 5),
 (3, 1),
 (3, 2),
 (3, 3),
 (3, 4),
 (3, 5),
 (4, 1),
 (4, 2),
 (4, 3),
 (4, 4),
 (4, 5),
 (5, 1),
 (5, 2),
 (5, 3);

INSERT INTO appointments (doctor_id, patient_id, dateHour, queryReason) 
VALUES 
(1, 2, '2024-06-16T12:15:50', 'Dolor de cabeza'),
(2, 3, '2024-06-17T14:30:00', 'Fractura de pierna'),
(3, 1, '2024-06-18T10:00:00', 'Control ginecológico'),
(4, 2, '2024-06-19T09:30:00', 'Vacunación'),
(1, 3, '2024-06-20T11:20:00', 'Revisión anual'),
(2, 1, '2024-06-21T15:00:00', 'Consulta general'),
(3, 2, '2024-06-22T08:30:00', 'Ecografía abdominal');

INSERT INTO recipes (appointment_id, issue_date, description)
VALUES
(1, '2024-06-16', 'Ibuprofeno cada 12 hs'),
(4, '2024-06-17', 'Amoxicilina cada 8 hs'),
(1, '2024-06-18', 'Paracetamol cada 6 hs'),
(3, '2024-06-19', 'Calcio y Vitamina D diariamente'),
(3, '2024-06-20', 'Losartán una vez al día'),
(5, '2024-06-21', 'Cetirizina una vez al día'),
(6, '2024-06-22', 'Fluoxetina una vez al día'),
(7, '2024-06-23', 'Tramadol según necesidad'),
(5, '2024-06-24', 'Azitromicina cada 24 hs'),
(6, '2024-06-25', 'Atorvastatina una vez al día');
