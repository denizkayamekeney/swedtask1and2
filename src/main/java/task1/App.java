package task1;

import task1.dao.VehiclesDao;
import task1.dao.VehiclesDaoImpl;
import task1.dto.CalculationCriterias;
import task1.dto.CoeficientsData;
import task1.dto.Vehicle;
import task1.services.VehiclesService;
import task1.utils.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class App {
    VehiclesService vehiclesService;

    public static void main( String[] args ) throws SQLException, IOException {
        DB db = new DB(Constants.USER,Constants.PASS)
                    .createDatabaseTables(Constants.DDL_FILE_PATH);

        System.out.println("Tables are created...");
        CoeficientsData coeficientsData = DataParser.loadDataFile(Constants.JSON_DATA_FILE_PATH);
        System.out.println("Data file is parsed");
        coeficientsData.addCoefficient("Test1",10d);
        coeficientsData.addCoefficient("Test1",10d);
        coeficientsData.addCoefficient("Test1",10d);
        coeficientsData.deleteCoefficient("Test1111");

        CascoCalculator cascoCalculator = new CascoCalculator(coeficientsData);
        cascoCalculator.addCriterias(CalculationCriterias.Make,
                CalculationCriterias.VehicleAge,
                CalculationCriterias.VehicleValue);

        App app = new App();

        VehiclesDao vehiclesDao = new VehiclesDaoImpl(db.getConnection());

        LogHelper logHelper = new LogHelper(Constants.ERROR_FILE_PATH);

        VehicleCSVParser vehicleCSVParser = new VehicleCSVParser(logHelper);

        VehiclesService vehiclesService = new VehiclesService(vehiclesDao,
                vehicleCSVParser,
                logHelper);

        app.vehiclesService =  vehiclesService;
        int a;
        try {

            vehiclesService.loadVehicleFromCsvFile(Constants.CSV_DATA_FILE_PATH);

        } catch (Exception exception){
            System.out.println(exception.getMessage());
        }

        List<Vehicle> vehicles = vehiclesService.findAll();
        System.out.println(vehicles.size());
        Vehicle vehicle = vehiclesService.findById(1);
        System.out.println(vehicle);
        System.out.println(vehiclesService.findById(1));
    }
}
