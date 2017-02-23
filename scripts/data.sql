--Users table

insert into user (created, email, first_name, last_name, mobile, status, 	username, password)
values (now(), 'hiteshskit@gmail.com', 'Hitesh', 'Katariya', '9929335632', 0, 'a', 'a');

insert into user (created, email, first_name, last_name, mobile, status, username, password)
values (now(), 'bhanvar@gmail.com', 'Bhanvar', 'Singh', '9929335632', 0, 'b', 'b');


insert into product_category (category_id, name, user_id)
values
(1, 'Med & Groc', 1);


insert into product (user_id, name, status, created, updated, category_id)
values (1, 'Product-1', 0, now(), now(), 1),
(1, 'Product-2', 0, now(), now(), 1),
(1, 'Product-3', 0, now(), now(), 1),
(1, 'Product-4', 0, now(), now(), 1),
(1, 'Product-5', 0, now(), now(), 1);

insert into supplier(created, updated, email, mobile, name, user_id)
values (now(), now(), 'hh@hh.com', '1234512345', 'Supplier-12', 1);

insert into item (item_id, bar_code, created, updated, expiry_date, tax_percent, unit_mrp, unit_sp, units_purchased, units_sold, product_id, supplier_id)
values (1, 130, now(), now(), now(), 15, 20, 18, 250, 10, 1, 1),
(2, 140, now(), now(), now(), 15, 20, 18, 250, 10, 1, 1),
(3, 150, now(), now(), now(), 15, 20, 18, 250, 10, 1, 1),
(4, 160, now(), now(), now(), 15, 20, 18, 250, 10, 1, 1),
(5, 170, now(), now(), now(), 15, 20, 18, 250, 10, 1, 1),
(6, 180, now(), now(), now(), 15, 20, 18, 250, 10, 1, 1),
(7, 190, now(), now(), now(), 15, 20, 18, 250, 10, 1, 1),
(8, 200, now(), now(), now(), 15, 20, 18, 250, 10, 1, 1),
(9, 210, now(), now(), now(), 15, 20, 18, 250, 10, 1, 1),
(10, 1600, now(), now(), now(), 15, 20, 18, 250, 10, 1, 1),
(11, 1601, now(), now(), now(), 15, 20, 18, 250, 10, 1, 1),
(12, 1602, now(), now(), now(), 15, 20, 18, 250, 10, 1, 1),
(13, 1701, now(), now(), now(), 15, 20, 18, 250, 10, 1, 1);

