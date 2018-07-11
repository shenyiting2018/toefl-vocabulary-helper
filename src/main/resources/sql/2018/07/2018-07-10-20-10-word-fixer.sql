update word set word_string = 'byzantine' where word_string = 'Byzantine';

alter table category drop index category_name;

create index category_name on category(category_name);