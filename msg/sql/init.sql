drop database IF EXISTS msg;
create database msg;
GRANT ALL ON msg.* TO 'jhc'@'localhost' IDENTIFIED BY 'jhc123';
use msg;
create table t_user(
	id int(10) primary key auto_increment,
	username varchar(100),
	password varchar(100),
	nickname varchar(100),
	status int(3),
	type int(3)
);
-- 添加管理员数据
insert into t_user values(null,'admin','123','超级管理员',1,1);