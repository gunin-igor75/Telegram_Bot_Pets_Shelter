create table dog_report(
    dog_id  bigint NOT NULL,
    report_id bigint NOT NULL UNIQUE,
    PRIMARY KEY (dog_id, report_id),
    FOREIGN KEY (dog_id) REFERENCES dog(id),
    FOREIGN KEY (report_id) REFERENCES report(id)
);