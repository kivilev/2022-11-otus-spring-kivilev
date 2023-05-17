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


--- Spring security users
insert into users(username,
                  password,
                  enabled,
                  account_non_locked,
                  account_non_expired,
                  credentials_non_expired)
values ('user',
        '{bcrypt}$2y$10$2IAWAAK5bmz4Sga7tqxBeeZB/hqLrmNm6.3HdQe.E3BgSSDotD66m', -- pass1
        true, true, true, true);

insert into users(username,
                  password,
                  enabled,
                  account_non_locked,
                  account_non_expired,
                  credentials_non_expired)
values ('admin',
        '{bcrypt}$2y$10$ZgRVWqtqHrm4TkAYaOs9seEFl5Wwgg2NeVZtoDL2h.SSzpKd6Vt9O', -- pass2
        false, true, true, true);


/*
insert into users(username, password)
values ('user1', '{bcrypt}$2y$10$2IAWAAK5bmz4Sga7tqxBeeZB/hqLrmNm6.3HdQe.E3BgSSDotD66m' */
/*pass1*//*
);

insert into users(username, password)
values ('admin', '{bcrypt}$2y$10$ZgRVWqtqHrm4TkAYaOs9seEFl5Wwgg2NeVZtoDL2h.SSzpKd6Vt9O' */
/*pass2*//*
 );

insert into authorities(username, authority)
values ('user1', 'ROLE_USER');

insert into authorities(username, authority)
values ('admin', 'ROLE_USER');

insert into authorities(username, authority)
values ('admin', 'ROLE_ADMIN');
*/
