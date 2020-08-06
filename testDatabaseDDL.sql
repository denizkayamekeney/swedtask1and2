drop table if exists test_vehicles;
create table test_vehicles (
    id integer not null,
    plate_number varchar(10) not null,
    first_registration integer not null,
    purchase_prise integer not null,
    producer varchar(50),
    milage integer,
    previous_indemnity double,
    primary key ( id )
 );
CREATE UNIQUE INDEX PLATE_NUMBER__TEST_UIX
ON  test_vehicles ( plate_number ASC)