-- CREATE TABLE users (
-- 	id SERIAL PRIMARY KEY, 
-- 	username varchar(20) NOT NULL, 
-- 	password varchar(200) NOT NULL
-- );

-- CREATE TABLE items (
-- 	item_id SERIAL PRIMARY KEY,
-- 	user_id int4 NOT NULL REFERENCES users(id) ON DELETE CASCADE,
-- 	text varchar(2000) NOT NULL
-- );

create table Faculty (
faculty_id serial primary key, 
courses int[], 
name varchar(40) not null, 
username varchar(20) not null,
password varchar(200) not null
);

create table Student (
student_id serial primary key,
name varchar(40),
courses int[],
username varchar(20) not null,
password varchar (200) not null
);

create table Course (
course_id serial,
name varchar(100) not null, 
course_number varchar(9) not null, 
faculty_id int not null,
primary key (course_id, faculty_id)
foreign key (teacher_id) references Faculty
);

create table My Courses (
student_id int not null,
courses int[],
primary key (student_id)
foreign key (student_id) references Student
);


create table Ratings (
course_id int not null, 
ratings double[], 
primary key (course_id)
foreign key (course_id) references Course
);

