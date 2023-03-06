create  table report(
                        id bigint primary key generated by default as identity,
                        owner_name varchar(255) unique not null,
                        date timestamp not null constraint date_unique unique,
                        health_behavior text,
                        diet text,
                        photo oid
);