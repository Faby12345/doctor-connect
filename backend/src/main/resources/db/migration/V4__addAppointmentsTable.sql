CREATE TABLE appointments(
    id uuid primary key,
    patient_id uuid,
    doctor_id uuid,
    date varchar(255),
    time varchar(255),
    status varchar(255)
);