PRAGMA foreign_keys = ON;

CREATE TABLE sections (
	id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE
	,title VARCHAR
	);

CREATE TABLE dishes (
	id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE
	,section_id INTEGER
	,title VARCHAR
	,icon VARCHAR
	,price DOUBLE
	,description TEXT CHECK (typeof(description) = 'text')
	,FOREIGN KEY (section_id) REFERENCES sections(id)
	);

CREATE TABLE groups (
	id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE
	,title VARCHAR
	--,owner_id INTEGER
	);

CREATE TABLE users (
	id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE
	,first_name VARCHAR
	,last_name VARCHAR
	,password VARCHAR
	,phone VARCHAR
	,email VARCHAR
	,is_admin BOOL
	);
	
CREATE  TABLE user_requests (
	user_id INTEGER NOT NULL 
	, group_id INTEGER NOT NULL
	,FOREIGN KEY (user_id) REFERENCES users(id)
	,FOREIGN KEY (group_id) REFERENCES groups(id)
	);
	
CREATE TABLE user_group_relations (
	user_id INTEGER
	,group_id INTEGER
	,PRIMARY KEY (
		user_id,
		group_id
		)
	,FOREIGN KEY (user_id) REFERENCES users(id)
	,FOREIGN KEY (group_id) REFERENCES groups(id)
	);

CREATE TABLE order_statuses (
	id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE
	,value VARCHAR
	);

CREATE TABLE orders (
	id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE
	,title VARCHAR
	,user_id INTEGER
	,group_id INTEGER
	,status_id INTEGER
	,reservation_time DATETIME
	,FOREIGN KEY (user_id) REFERENCES users(id)
	,FOREIGN KEY (status_id) REFERENCES order_statuses(id)
	,FOREIGN KEY (group_id) REFERENCES groups(id)
	);

CREATE TABLE ordered_dishes (
	order_id INTEGER NOT NULL
	,user_id INTEGER NOT NULL
	,dish_id INTEGER NOT NULL
	--,count INTEGER NOT NULL
	,FOREIGN KEY (order_id) REFERENCES orders(id)
	,FOREIGN KEY (user_id) REFERENCES users(id)
	,FOREIGN KEY (dish_id) REFERENCES dishes(id)
	);

ALTER TABLE groups ADD COLUMN owner_id INTEGER REFERENCES users (id);
