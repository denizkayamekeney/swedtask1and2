package task1.dao;

import com.sun.xml.bind.v2.TODO;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import task1.DbAppException;
import task1.dto.Vehicle;
import task1.utils.DataLoader;

import java.sql.SQLException;
import java.util.List;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class VehiclesDaoImplTest {

    @Autowired
    private VehiclesDao vehiclesDao;

    @Test
    public void succesfully_Insert_Vehicle(){
        Vehicle vehicle1 = vehiclesDao.save(VehicleHelper.createRandomVehicle());
        Vehicle vehicle2 = vehiclesDao.findById(vehicle1.getId()).orElse(null);
        Assertions.assertEquals(vehicle1,vehicle2,"The object needs to be equal");
    }

    @Test
    public void succesfully_Update_Vehicle(){
        Vehicle vehicle1 = VehicleHelper.createRandomVehicle();
        vehicle1.setCascoWithIndemnity(300);
        vehicle1 = vehiclesDao.save(vehicle1);
        var vehicle2 = vehiclesDao.findById(vehicle1.getId()).orElse(null);
        Assertions.assertEquals(vehicle1,vehicle2,"The object needs to be equal");
    }

    @Test
    public void findById_Vehicle(){
        Vehicle vehicle1 = vehiclesDao.save(VehicleHelper.createRandomVehicle());
        var vehicle2 = vehiclesDao.findById(vehicle1.getId()).orElse(null);
        Assertions.assertEquals(vehicle1,vehicle2,"It could not find the inserted data");
    }

    @Test
    public void Exception_Dublicate_Plate_Number(){
        Vehicle vehicle1 = vehiclesDao.save(VehicleHelper.createRandomVehicle());

        Vehicle vehicle2 = VehicleHelper.createRandomVehicle();
        vehicle2.setPlateNumber(vehicle1.getPlateNumber());

        Assertions.assertThrows(DataIntegrityViolationException.class, ()->vehiclesDao.save(vehicle2));

    }

    @Test
    public void findAll_Vehicle_test() throws SQLException {
        final int recordCount = 10;
        int initialRecordSize = ((List<Vehicle>)vehiclesDao.findAll()).size();

        for (int i = 0; i < recordCount; i++){
            vehiclesDao.save(VehicleHelper.createRandomVehicle());
        }
        int finalRecordSize = ((List<Vehicle>)vehiclesDao.findAll()).size();
                Assertions.assertTrue(finalRecordSize == initialRecordSize + recordCount,"It is ecpected to insert " + recordCount + " record");
    }

}
