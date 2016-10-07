create table vacancy (
  vacancy_id integer PRIMARY KEY,
  title varchar (50) not null
);

insert into vacancy (vacancy_id, title) values (1, 'Java Developer at Amazon.com');
insert into vacancy (vacancy_id, title) values (2, 'Java Developer at Apple');
insert into vacancy (vacancy_id, title) values (3, 'C++ Developer at Microsoft');
insert into vacancy (vacancy_id, title) values (4, 'Python Developer at Source{d}');