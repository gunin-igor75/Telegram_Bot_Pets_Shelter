create table cat_report(
    cat_id  bigint NOT NULL,
    report_id bigint NOT NULL UNIQUE ,
    PRIMARY KEY (cat_id, report_id),
    FOREIGN KEY (cat_id) REFERENCES cat(id),
    FOREIGN KEY (report_id) REFERENCES report(id)
);