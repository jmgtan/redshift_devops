create table orders (
    id bigint identity (0,1) not null,
    email varchar(100) not null,
    product_id int not null,
    quantity int not null,
    date_created timestamp
)