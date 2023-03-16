create table cat_report(
    cat_id  bigint,
    report_id bigint,
    primary key (cat_id, report_id),
    foreign key(cat_id) references cat(id),
    foreign key (report_id) references report(id)
);