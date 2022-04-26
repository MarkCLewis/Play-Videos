create table Faculty (
faculty_id serial primary key, 
name varchar(40) not null, 
username varchar(20) not null,
password varchar(200) not null
);

create table Student (
student_id serial primary key,
name varchar(40),
username varchar(20) not null,
password varchar (200) not null
);

create table Course (
course_id serial,
course_name varchar(100) not null, 
course_number varchar(9) not null, 
faculty_id int not null,
primary key (course_id),
foreign key (faculty_id) references Faculty
);

create table StudentCourses (
student_id int not null,
course_id int not null,
rating int, 
primary key (student_id, course_id),
foreign key (student_id) references Student,
foreign key (course_id) references Course
);

