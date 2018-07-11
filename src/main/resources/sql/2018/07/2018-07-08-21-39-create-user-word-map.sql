alter table category add column user_id int not null default 1;
ALTER TABLE category ADD CONSTRAINT category_user_id FOREIGN KEY (user_id) REFERENCES user(id);

alter table category add column status_code int not null default 1 ;

CREATE INDEX category_user_id_index
on category (user_id);