INSERT INTO poll (title, question, answer1, answer2, answer3, votes1, votes2, votes3, date)
VALUES 
('JAVA Poll', 'Is Java fun?', 'Yes', 'No', 'Not Bad', 50, 25, 3, '2023-09-12'),
('Social Media Poll', 'Which is your favorite social media platform?', 'Twitter', 'Facebook', 'Instagram', 3, 25, 50, '2023-09-12'),
('Vacation Poll', 'Which is your favorite holiday destination?', 'Cancun', 'Niagara Falls', 'Florida', 25, 3, 50, '2023-09-12'),
('Drink Poll', 'Which is your favorite drink?', 'Tea', 'Coffee', 'Coke', 3, 50, 25, '2023-09-12');

INSERT INTO sec_user (email, fullName, encryptedPassword, enabled)
VALUES ('manager@pollapp.com','Lorenzo Bari', '$2a$10$bK0SDlJjkm.q9x5ET1TR4ugYmFuOKQLr.KItiHM3iFLDszo/KXmYS', 1);
 
INSERT INTO sec_role (roleName)
VALUES ('ROLE_USER');

INSERT INTO sec_role (roleName)
VALUES ('ROLE_MANAGER');
 
INSERT INTO user_role (userId, roleId)
VALUES (1, 2);

