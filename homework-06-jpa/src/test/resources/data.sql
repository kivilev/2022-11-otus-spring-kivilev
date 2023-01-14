insert into genre (name)
values ('детектив'),
       ('ужасы'),
       ('драма'),
       ('исторический роман');

insert into author (name, birthday, deathday)
values ('Стивен Кинг', date'1962-01-01', null),
       ('Лев Толстой', date'1800-09-02', date'1890-10-01'),
       ('Александр Пушкин', date'1799-01-01', date'1826-02-03'),
       ('Агата Кристи', date'1899-02-01', date'1968-01-01');


/*
insert into book(title, created_year, author_id, genre_id)
values ('Killing in castle', 1920, 4, 1);
insert into book(title, created_year, author_id, genre_id)
values ('Some name 2', 1826, 3, 4);
insert into book(title, created_year, author_id, genre_id)
values ('Dark', 1986, 1, 2);
insert into book(title, created_year, author_id, genre_id)
values ('War and peace', 1846, 2, 4);
*/
