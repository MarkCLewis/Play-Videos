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
primary key (course_id, faculty_id),
foreign key (faculty_id) references Faculty
);

create table MyCourses (
student_id int not null,
courses int[],
primary key (student_id),
foreign key (student_id) references Student
);

--Ratings table currently won't work in postgresql; throwing error that the number of  referencing and referenced columns for foreign key disagree
-- create table Ratings (
-- course_id int not null, 
-- ratings int[], 
-- foreign key (course_id) references Course
-- );

