drop database IF EXISTS shop;
create database shop;
GRANT ALL ON shop.* TO 'jhc'@'localhost' IDENTIFIED BY 'jhc123';
use shop;
-- 创建用户表
create table t_user(
	id int(11) primary key auto_increment,
	username varchar(100),
	password varchar(100),
	nickname varchar(100),
	type int(5)
);
-- 创建地址表
create table t_address(
	id int(11) primary key auto_increment,
	name varchar(255),
	phone varchar(100),
	postcode varchar(100),
	user_id int(11),
	CONSTRAINT FOREIGN KEY(user_id) REFERENCES t_user(id)
);
-- 创建订单表
create table t_orders(
	id int(11) primary key auto_increment,
	buy_date datetime,
	pay_date datetime,
	confirm_date datetime,
	status int(5),
	user_id int(11),
	address_id int(11),
	price double,
	CONSTRAINT FOREIGN KEY(user_id) REFERENCES t_user(id),
	CONSTRAINT FOREIGN KEY(address_id) REFERENCES t_address(id)
);

-- 创建商品种类表
create table t_category(
	id int(11) primary key auto_increment,
	name varchar(100)
);
-- 创建产品表
create table t_product(
	id int(11) primary key auto_increment,
	name varchar(100),
	price double,
	intro text,
	img varchar(100),
	stock int(10),
	status int(2),
	c_id int(10),
	CONSTRAINT FOREIGN KEY(c_id) REFERENCES t_category(id)
);
-- 创建产品订单联系表
create table t_product_orders(
	id int(11) primary key auto_increment,
	product_id int(10),
	orders_id int(10),
	CONSTRAINT FOREIGN KEY(product_id) REFERENCES t_product(id),
	CONSTRAINT FOREIGN KEY(orders_id) REFERENCES t_orders(id)
);
-- 创建购物车产品联系表
create table t_cart_product(
	id int(11) primary key auto_increment,
	number int(10),
	price double,
	o_id int(11),
	p_id int(11),
	CONSTRAINT FOREIGN KEY(o_id) REFERENCES t_orders(id),
	CONSTRAINT FOREIGN KEY(p_id) REFERENCES t_product(id)
);
-- 添加管理员数据
insert into t_user values(null,'admin','123','超级管理员',1);
