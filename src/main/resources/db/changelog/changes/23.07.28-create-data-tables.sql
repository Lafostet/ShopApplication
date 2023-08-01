--liquibase formatted sql

--changeset imyronenko:01
create table product (
    id int auto_increment not null,
    name varchar(255) not null,
    price numeric(6,2) not null,
    description varchar(255),
    constraint PK_product primary key (id),
    constraint UK_name_price_description unique key (name, price, description)
);