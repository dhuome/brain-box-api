-- ## Seed Privileges ## --
INSERT INTO privilege (name_ar, name_en)
VALUES (N'مسنخدم', 'USER');
INSERT INTO privilege (name_ar, name_en)
VALUES (N'مدير', 'ADMIN');

-- ## Seed Admin Account ## --
-- ## Admin password is 11111111 ## --
INSERT INTO platform_users (email, mobile_number, password, username)
VALUES ('admin@tabadul.com', '507090677', '$2a$10$dJF9WY7OeF62JweIWChzI./KUhbcikc2VtRAx1LmcVXkjfBK7Fyh6', 'admin');

INSERT INTO user_privilege (user_id, privilege_id)
VALUES ((select id from platform_users where username = 'admin'), (select id from privilege where name_en = 'ADMIN'));