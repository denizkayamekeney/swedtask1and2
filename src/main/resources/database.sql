create table vehicles (
    id integer not null,
    plate_number varchar(10),
    first_registration varchar(4),
    purchase_prise integer,
    producer varchar(50),
    milage integer,
    previous_indemnity double,
    primary key ( id )
 );
