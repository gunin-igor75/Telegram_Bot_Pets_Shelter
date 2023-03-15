create table cat_report(
    cat_id  bigint constraint cat_id  references cat,
    report_id bigint not null primary key constraint report_id references report
);