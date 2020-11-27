delete from user_role;
delete from usr;

insert into usr(id, username, password, active) values
(1, 'aleksei', 'aleksei.oFRzC', true),
(2, 'dima', 'dima', true);

insert into user_role(user_id, roles) values
(1, 'READER'), (1, 'WRITER'),
(2, 'READER');
