create table vehicles (
    id integer not null,
    plate_number varchar(10) not null,
    first_registration integer not null,
    purchase_prise integer not null,
    producer varchar(50),
    milage integer,
    previous_indemnity double,
    primary key ( id )
 );
CREATE UNIQUE INDEX PLATE_NUMBER_UIX
ON  vehicles ( plate_number ASC);