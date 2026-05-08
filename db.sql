

create database companydb;
use companydb;
create table companies(
    id bigint primary key auto_increment,
    name varchar(50),
    description varchar(75),	
    rating double
);
insert into companies(name, description, rating) values
	("company1", "company1 description", 3);




create database jobapp;
use jobapp;
create table jobs(
	id bigint primary key auto_increment,
    title varchar(50) not null,
    description varchar(255) not null, 
    salary int not null,
    location varchar(50) not null,
    company_id bigint,
    constraint fk_company 
    foreign key (company_id) 
    references companydb.companies(id) 
    on delete cascade
);
insert into jobs(title, description, salary, location, company_id) values
	("Software Engineer", "SE desc", 120000, "Paris", 1);





create database reviewdb;
use reviewdb;
create table reviews(
	id bigint primary key auto_increment,
    title varchar(50) not null,
    description varchar(50) not null,
    rating double not null,
    company_id bigint, 
	constraint fk_company 
    foreign key (company_id) 
    references companydb.companies(id) 
    on delete cascade
);
insert into reviews(title, description, rating, company_id) values
	("review 3", "review 3 description", 3.2, 1),
    ("review 2", "review 2 description", 4, 1); 
