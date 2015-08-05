INSERT INTO users(first_name, last_name, password, phone, email, is_admin)
  VALUES ('Admin', 'Admin', '$2a$10$iZwfkB3nTi5slIn6mlwvxeQm8UnUsHXJlTTuMirDe1B9pVpiOqy02' /* 123 */, '+38123', 'admin@gmail.com', 1);
INSERT INTO users(first_name, last_name, password, phone, email, is_admin)
  VALUES ('Ivan', 'Ivanov', '$2a$10$iZwfkB3nTi5slIn6mlwvxeQm8UnUsHXJlTTuMirDe1B9pVpiOqy02' /* 123 */, '+38123', 'ivan@gmail.com', 1);

insert into order_statuses(value) VALUES ('New');
insert into order_statuses(value) VALUES ('Submitted');
insert into order_statuses(value) VALUES ('Close');

insert into sections(title) values("First Dishes");
insert into sections(title) values("Deserts");

insert into dishes(section_id, title, icon, price, description) values(1, 'Soup', 'thumb-1.jpg', 20, 'Best soup');
insert into dishes(section_id, title, icon, price, description) values(1, 'Gazpacho', 'thumb-2.jpg', 30, 'Best gazpacho');

