insert into role (id, role) values (1, 'SUPER');
insert into role (id, role) values (2, 'ADMIN');
insert into role (id, role) values (3, 'USER');
insert into role (id, role) values (4, 'DEMO');

insert into user (id, email, first_name, last_name, password) values (1, 'superuser', 'Louis', 'Smart', 'admin');

insert into user_role(user_id, role_id) values (1, 1);