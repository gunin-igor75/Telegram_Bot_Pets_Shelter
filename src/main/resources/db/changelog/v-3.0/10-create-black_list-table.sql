create table black_list(
    id bigint primary key generated by default as identity,
    chat_id  bigint,
    username varchar(30)
);