create table users (
	id SERIAL PRIMARY KEY, 
	username varchar(20) NOT NULL, 
	password varchar(200) NOT NULL
);

create table items (
	item_id SERIAL PRIMARY KEY,
	user_id int4 REFERENCES users(id) ON DELETE CASCADE,
	text varchar(2000)
);
