
INSERT INTO ais_service.subjects (title, type_subject, hours) VALUES ('Mathematics', 'PRACTICE', 120);
INSERT INTO ais_service.subjects (title, type_subject, hours) VALUES ('English', 'PRACTICE', 90);
INSERT INTO ais_service.subjects (title, type_subject, hours) VALUES ('Physics', 'PRACTICE', 100);
INSERT INTO ais_service.subjects (title, type_subject, hours) VALUES ('History', 'PRACTICE', 80);
INSERT INTO ais_service.subjects (title, type_subject, hours) VALUES ('Chemistry', 'PRACTICE', 110);
INSERT INTO ais_service.subjects (title, type_subject, hours) VALUES ('Geography', 'PRACTICE', 70);
INSERT INTO ais_service.subjects (title, type_subject, hours) VALUES ('Biology', 'PRACTICE', 95);
INSERT INTO ais_service.subjects (title, type_subject, hours) VALUES ('Art', 'PRACTICE', 60);
INSERT INTO ais_service.subjects (title, type_subject, hours) VALUES ('Music', 'PRACTICE', 75);
INSERT INTO ais_service.subjects (title, type_subject, hours) VALUES ('Physical Education', 'PRACTICE', 45);


INSERT INTO ais_service.groups (name, form_education, institute) VALUES ('РПС-11', 'FULL', 'IMNSaCS');
INSERT INTO ais_service.groups (name, form_education, institute) VALUES ('РПС-21', 'FULL', 'IMNSaCS');
INSERT INTO ais_service.groups (name, form_education, institute) VALUES ('РПС-31', 'FULL', 'IMNSaCS');
INSERT INTO ais_service.groups (name, form_education, institute) VALUES ('РПС-41', 'FULL', 'IMNSaCS');
INSERT INTO ais_service.groups (name, form_education, institute) VALUES ('ПО-11', 'FULL', 'IMNSaCS');
INSERT INTO ais_service.groups (name, form_education, institute) VALUES ('ПО-21', 'FULL', 'IMNSaCS');
INSERT INTO ais_service.groups (name, form_education, institute) VALUES ('ПО-31', 'FULL', 'IMNSaCS');
INSERT INTO ais_service.groups (name, form_education, institute) VALUES ('ПО-41', 'FULL', 'IMNSaCS');
INSERT INTO ais_service.groups (name, form_education, institute) VALUES ('ПИБ-31', 'FULL', 'IMNSaCS');
INSERT INTO ais_service.groups (name, form_education, institute) VALUES ('ПИБ-41', 'FULL', 'IMNSaCS');

INSERT INTO ais_service.subjects_groups (group_id, subject_id, teacher_id, start_lesson, end_lesson, week_even, weekday, classroom)
VALUES (1, 2, '138e9194-f572-4703-b892-d94e7c782b48', '08:00:00', '09:30:00', true, 3, '227/2');

INSERT INTO ais_service.subjects_groups (group_id, subject_id, teacher_id, start_lesson, end_lesson, week_even, weekday, classroom)
VALUES (2, 2, '138e9194-f572-4703-b892-d94e7c782b48', '09:40:00', '11:10:00', true, 4, '227/2');

INSERT INTO ais_service.subjects_groups (group_id, subject_id, teacher_id, start_lesson, end_lesson, week_even, weekday, classroom)
VALUES (2, 4, '138e9194-f572-4703-b892-d94e7c782b48', '11:40:00', '13:10:00', true, 4, '227/2');

INSERT INTO ais_service.subjects_groups (group_id, subject_id, teacher_id, start_lesson, end_lesson, week_even, weekday, classroom)
VALUES (2, 2, '138e9194-f572-4703-b892-d94e7c782b48', '13:10:00', '14:40:00', true, 3, '227/2');

INSERT INTO ais_service.subjects_groups (group_id, subject_id, teacher_id, start_lesson, end_lesson, week_even, weekday, classroom)
VALUES (3, 4, '138e9194-f572-4703-b892-d94e7c782b48', '08:00:00', '09:30:00', false, 2, '227/2');

INSERT INTO ais_service.subjects_groups (group_id, subject_id, teacher_id, start_lesson, end_lesson, week_even, weekday, classroom)
VALUES (3, 2, '138e9194-f572-4703-b892-d94e7c782b48', '09:40:00', '11:10:00', false, 1, '227/2');

INSERT INTO ais_service.subjects_groups (group_id, subject_id, teacher_id, start_lesson, end_lesson, week_even, weekday, classroom)
VALUES (4, 6, '138e9194-f572-4703-b892-d94e7c782b48', '11:40:00', '13:10:00', false, 6, '227/2');

INSERT INTO ais_service.subjects_groups (group_id, subject_id, teacher_id, start_lesson, end_lesson, week_even, weekday, classroom)
VALUES (4, 2, '7aafa9e5-6f44-41b8-b354-38b85bc1dc27', '13:10:00', '14:40:00', false, 3, '227/2');

INSERT INTO ais_service.subjects_groups (group_id, subject_id, teacher_id, start_lesson, end_lesson, week_even, weekday, classroom)
VALUES (5, 7, '138e9194-f572-4703-b892-d94e7c782b48', '08:00:00', '09:30:00', true, 4, '227/2');

INSERT INTO ais_service.subjects_groups (group_id, subject_id, teacher_id, start_lesson, end_lesson, week_even, weekday, classroom)
VALUES (5, 2, '138e9194-f572-4703-b892-d94e7c782b48', '09:40:00', '11:10:00', true, 2, '227/2');
