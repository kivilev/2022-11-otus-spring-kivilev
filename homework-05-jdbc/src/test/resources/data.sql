insert into genre (id, name)
values (1, 'детектив');
insert into genre (id, name)
values (2, 'ужасы');
insert into genre (id, name)
values (3, 'драма');
insert into genre (id, name)
values (4, 'исторический роман');

insert into author (id, name, birthday, deathday)
values (1, 'Стивен Кинг', date'1962-01-01', null);
insert into author (id, name, birthday, deathday)
values (2, 'Лев Толстой', date'1800-09-02', date'1890-10-01');
insert into author (id, name, birthday, deathday)
values (3, 'Александр Пушкин', date'1799-01-01', date'1826-02-03');
insert into author (id, name, birthday, deathday)
values (4, 'Агата Кристи', date'1899-02-01', date'1968-01-01');

insert into book(title, created_year, author_id, genre_id)
values ('Killing in castle', 1920, 4, 1);
insert into book(title, created_year, author_id, genre_id)
values ('Some name 2', 1826, 3, 4);
insert into book(title, created_year, author_id, genre_id)
values ('Dark', 1986, 1, 2);
insert into book(title, created_year, author_id, genre_id)
values ('War and peace', 1846, 2, 4);