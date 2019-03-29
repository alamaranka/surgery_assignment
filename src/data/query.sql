/*update surgery set surgery_duration = 4 where surgery_type = 's0003';*/
/*insert into patient values ('p0031', 's0005', 4.0);
insert into patient values ('p0032', 's0002', 4.0);*/
/*insert into capacity values ('Monday', 9, 9, 6);
insert into capacity values ('Tuesday', 9, 9, 6);*/
/*
update availability set OR2 = 0 where surgery_type = 's0001' or surgery_type = 's0002';*/
/*
insert into patient(patient_number, surgery_type) value ('p0033', 's0005');
insert into patient(patient_number, surgery_type) value ('p0034', 's0002');
insert into patient(patient_number, surgery_type) value ('p0035', 's0004');*/
/*insert into patient values ('p0036', 's0001', (select surgery_duration from surgery where surgery_type = 's0001'));*/
select * from surgery;
select * from patient;