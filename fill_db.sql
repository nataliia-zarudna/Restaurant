INSERT INTO users(first_name, last_name, password, phone, email) VALUES ('Ivan', 'Ivanov', '123', '+38123', 'email@gmail.com');

insert into sections(title, icon, description) values("First Dishes", "first_dishes.png", "Best first dishes");

insert into dishes(section_id, title, icon, price, description, is_available) values(1, 'Soup', 'thumb-1.jpg', 20, 'Best soup', 'true');
insert into dishes(section_id, title, icon, price, description, is_available) values(1, 'Gazpacho', 'thumb-2.jpg', 30, 'Best gazpacho', 'true');

insert into order_statuses(value) VALUES ('New');
insert into order_statuses(value) VALUES ('Submitted');
insert into order_statuses(value) VALUES ('Close');