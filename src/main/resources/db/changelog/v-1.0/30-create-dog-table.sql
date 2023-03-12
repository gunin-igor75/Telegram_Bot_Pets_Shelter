create table dog(
                     id bigint primary key generated by default as identity,
                     name varchar(255) unique not null,
                     adopted boolean,
                     date_adoption date,
                     report_id bigint constraint report_id references report unique
);
