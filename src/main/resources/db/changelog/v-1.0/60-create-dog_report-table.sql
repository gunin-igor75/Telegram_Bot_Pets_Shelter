create table dog_report(
    dog_id  bigint constraint dog_id  references cat,
    report_id bigint not null primary key constraint report_id references report
);