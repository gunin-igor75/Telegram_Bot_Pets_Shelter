create table cat(
                     id bigint primary key generated by default as identity,
                     name varchar(255)  not null,
                     adopted boolean,
                     date_adoption date,
                     test_period integer default 30
);
