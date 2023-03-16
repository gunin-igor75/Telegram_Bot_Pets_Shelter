create table dog_report(
    dog_id  bigint,
    report_id bigint,
    primary key (dog_id, report_id),
    foreign key(dog_id) references dog(id),
    foreign key (report_id) references report(id)
);