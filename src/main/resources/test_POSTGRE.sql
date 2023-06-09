
-- create sequence "ghgj"
-- drop sequence person_id_seq

-- select nextval('person_id_seq')



-- alter sequence test_person.public.person_id_seq
-- restart with 4

-- alter table person
-- alter column id restart

-- create sequence "ghgj"
-- drop sequence person_id_seq

-- select nextval('person_id_seq')

create database db_film;

create table Director(
                         dir_id int generated by default as identity primary key ,
                         name varchar(100) not null unique,
                         age int check ( age > 10 and age < 120)
);

create table movie(
                      movie_id int generated by default as identity primary key ,
                      director_id int not null references director(dir_id),
                      name varchar(200) not null ,
                      year_of_production int check ( year_of_production > 1900 )
)