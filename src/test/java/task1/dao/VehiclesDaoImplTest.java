package task1.dao;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import task1.dto.Vehicle;
import task1.utils.Constants;
import task1.utils.DB;
import java.sql.SQLException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VehiclesDaoImplTest {

    private VehiclesDao vehiclesDao;
    private DB db;

    @BeforeAll
    public void initDataBase() throws SQLException {
        this.db = new DB(Constants.USER,
                Constants.PASS)
                .createDatabaseTables(Constants.DDL_FILE_PATH);

        vehiclesDao = new VehiclesDaoImpl(db.getConnection());
    }

    @Test
    public void succesfully_Insert_Vehicle() throws SQLException {
        Vehicle vehicle1 = VehicleHelper.createRandomVehicle();
        vehiclesDao.insert(vehicle1);
        var vehicle2 = vehiclesDao.findById(vehicle1.getId());
        Assertions.assertEquals(vehicle1,vehicle2,"The object needs to be equal");
    }

    @Test
    public void succesfully_Update_Vehicle() throws SQLException {
        Vehicle vehicle1 = VehicleHelper.createRandomVehicle();
        vehiclesDao.insert(vehicle1);
        vehicle1.setCascoWithIndemnity(300);
        vehiclesDao.update(vehicle1);
        var vehicle2 = vehiclesDao.findById(vehicle1.getId());
        Assertions.assertEquals(vehicle1,vehicle2,"The object needs to be equal");
    }

    @Test
    public void findById_Vehicle() throws SQLException {
        Vehicle vehicle1 = VehicleHelper.createRandomVehicle();
        vehiclesDao.insert(vehicle1);
        var vehicle2 = vehiclesDao.findById(vehicle1.getId());
        Assertions.assertEquals(vehicle1,vehicle2,"It could not fint the inserted data");
    }

    @Test
    public void Exception_Dublicate_id() throws SQLException {
        Vehicle vehicle1 = VehicleHelper.createRandomVehicle();
        vehiclesDao.insert(vehicle1);

        Vehicle vehicle2 = VehicleHelper.createRandomVehicle();
        vehicle2.setId(vehicle1.getId());
        Assertions.assertThrows(JdbcSQLIntegrityConstraintViolationException.class,()-> vehiclesDao.insert(vehicle2));
    }
    @Test
    public void Exception_Dublicate_Plate_Number() throws SQLException {
        Vehicle vehicle1 = VehicleHelper.createRandomVehicle();
        vehiclesDao.insert(vehicle1);

        Vehicle vehicle2 = VehicleHelper.createRandomVehicle();
        vehicle2.setPlateNumber(vehicle1.getPlateNumber());
        Assertions.assertThrows(JdbcSQLIntegrityConstraintViolationException.class,()-> vehiclesDao.insert(vehicle2));
    }

    @Test
    public void findAll_Vehicle_test() throws SQLException {
        final int recordCount = 10;
        int initialRecordSize = vehiclesDao.findAll().size();
        for (int i = 0; i < recordCount; i++){
            vehiclesDao.insert(VehicleHelper.createRandomVehicle());
        }
        var vehicless = vehiclesDao.findAll();
        Assertions.assertTrue(vehiclesDao.findAll().size() == initialRecordSize + recordCount,"It is ecpected to insert " + recordCount + " record");
    }

}
