drop table if exists book_comment;
drop table if exists book;
drop table if exists genre;
drop table if exists author;

create table genre
(
    id   bigserial primary key,
    name varchar(255) not null
);
create unique index genre_name_unq on genre (name);

create table author
(
    id       bigserial primary key,
    name     varchar(255) not null,
    birthday date         not null,
    deathday date
);

create table book
(
    id           bigserial primary key,
    title        varchar(255) not null,
    created_year int          not null,
    author_id    bigint       not null,
    genre_id     bigint       not null,
    foreign key (author_id) references author,
    foreign key (genre_id) references genre
);

create index book_author_id_idx on book (author_id);
create index book_genre_id_idx on book (genre_id);

create table book_comment
(
    id           bigserial primary key,
    book_id      bigint references book(id) on delete cascade,
    text         text   not null,
    create_dtime timestamp with time zone
);
create index book_comment_book_id_idx on book_comment (book_id);
