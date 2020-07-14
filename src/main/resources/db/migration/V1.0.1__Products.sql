create table products (
    id int not null identity(1, 1),
    product_name varchar(150) not null,
    price decimal(12,2) not null,
    status varchar(25) not null default 'ACTIVE',
    close_date date null
);

create table products_staging (
    id int not null identity (1, 1),
    product_name varchar(150) not null,
    price decimal(12,2) not null
);