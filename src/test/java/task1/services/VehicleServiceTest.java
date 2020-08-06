package task1.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import task1.dao.VehicleHelper;
import task1.dao.VehiclesDao;
import task1.dao.VehiclesDaoImpl;
import task1.dto.CalculationCriterias;
import task1.dto.CoeficientsData;
import task1.dto.Vehicle;
import task1.utils.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VehicleServiceTest {
    private VehiclesDao vehiclesDao;
    private VehicleCSVParser vehicleCSVParser;
    private LogHelper logHelper;
    private CascoCalculator cascoCalculator;

    private VehiclesService vehiclesService;

    @BeforeAll
    public void initDataBase() throws SQLException, IOException {
        DB db = new DB(Constants.USER,
                Constants.PASS)
                .createDatabaseTables(Constants.DDL_FILE_PATH);

        vehiclesDao = new VehiclesDaoImpl(db.getConnection());

        logHelper = new LogHelper(Constants.ERROR_FILE_PATH_TEST);
        vehicleCSVParser = new VehicleCSVParser(logHelper);
        CoeficientsData coeficientsData = DataParser.loadDataFile("src/test/resources/withFullNodes.json");


        cascoCalculator = new CascoCalculator(coeficientsData);
        cascoCalculator.addCriterias(CalculationCriterias.Make,
                CalculationCriterias.VehicleAge,
                CalculationCriterias.VehicleValue);
        vehiclesService = new VehiclesService(vehiclesDao,
                vehicleCSVParser,
                logHelper,
                cascoCalculator);
    }

    @Test
    public void calculateVehiclesCasco_without_indemnity_Test() throws IOException {
        Vehicle vehicle1 = VehicleHelper.createRandomVehicle();
        vehicle1.setPreviousIndemnity(1000);

        List<Vehicle> vehicles = List.of(vehicle1);
        vehiclesService.calculateVehiclesCasco(vehicles);

        Assertions.assertTrue(vehicles.get(0).getCascoWithoutIndemnity() > 0);
        Assertions.assertTrue(vehicles.get(0).getCascoWithIndemnity() ==  0);
    }

    @Test
    public void calculateAnnualVehiclesCasco_with_indemnity_Test() throws IOException {
        Vehicle vehicle1 = VehicleHelper.createRandomVehicle();
        vehicle1.setPreviousIndemnity(1000);

        List<Vehicle> vehicles = List.of(vehicle1);
        vehiclesService.getCascoCalculator().setCriterias(new HashSet<>(Set.of(CalculationCriterias.Make,
                CalculationCriterias.VehicleAge,
                CalculationCriterias.VehicleValue,
                CalculationCriterias.PreviousIndemnity)));

        vehiclesService.calculateVehiclesCasco(vehicles);

        Assertions.assertTrue(vehicles.get(0).getCascoWithoutIndemnity() == 0);
        Assertions.assertTrue(vehicles.get(0).getCascoWithIndemnity() >  0);
    }

    @Test
    public void calculateAnnualVehiclesCasco_with_Skip_Test() throws IOException {
        Vehicle vehicle1 = VehicleHelper.createRandomVehicle();
        vehicle1.setProducer("TATRA"); // TATRA is not in avg_purchase_list.
        vehicle1.setPreviousIndemnity(1000);

        List<Vehicle> vehicles = List.of(vehicle1);
        vehiclesService.getCascoCalculator().setCriterias(new HashSet<>(Set.of(CalculationCriterias.Make,
                CalculationCriterias.VehicleAge,
                CalculationCriterias.VehicleValue,
                CalculationCriterias.PreviousIndemnity)));

        vehiclesService.calculateVehiclesCasco(vehicles);

        Assertions.assertTrue((vehicles.get(0).getCascoWithIndemnity() < 0),"Car maker is not in avg_purchase_list. The result needs to be -1. (Skipped) ");
    }


}
