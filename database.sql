create sequence books_id_seq
    as integer;

alter sequence books_id_seq owner to postgres;

create table books
(
    id     serial,
    title  varchar(255) not null,
    author varchar(255) not null,
    isbn   varchar(50),
    primary key ()
);

alter table books
    owner to postgres;

alter sequence books_id_seq owned by books.id;

create unique index books_pkey
    on books (id);

