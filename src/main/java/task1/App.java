package task1;

import task1.dao.VehiclesDao;
import task1.dao.VehiclesDaoImpl;
import task1.dto.CalculationCriterias;
import task1.dto.CoeficientsData;
import task1.services.VehiclesService;
import task1.utils.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static task1.utils.Constants.*;

public class App {
    VehiclesService vehiclesService;

    public VehiclesService getVehiclesService() {
        return vehiclesService;
    }

    public static void main( String[] args ) throws SQLException, IOException {

        App app = new App();
        app.initializeApp();
        VehiclesService vehiclesService = app.getVehiclesService();

            // Loading the vehicles from vehicle file
            var vehicles = vehiclesService.loadVehicleFromCsvFile(CSV_DATA_FILE_PATH);
            System.out.println("Vehicles data is parsed from " + CSV_DATA_FILE_PATH + " file.");

            // Inserting the vehicles to the database..
            vehiclesService.insertVehicles(vehicles);
            System.out.println("Vehicles are inserted to databases!");

            // Calculating casco result without indemnity and writing to the file
            vehiclesService.calculateVehiclesCasco(vehicles);
            vehiclesService.saveVehiclesToCSVFile(vehicles, "CascoWithoutIndemnity.csv");
            System.out.println("Cascos are calculated without indemnity and wrtten to CascoWithoutIndemnity.csv file!");


            // Calculating casco result with indemnity and writing to the file
            // We are setting the casco calculating criterias.
            vehiclesService.getCascoCalculator().setCriterias(new HashSet<CalculationCriterias>(
                    Set.of(CalculationCriterias.Make,
                            CalculationCriterias.VehicleAge,
                            CalculationCriterias.VehicleValue,
                            CalculationCriterias.PreviousIndemnity)));

            vehiclesService.calculateVehiclesCasco(vehicles);
            vehiclesService.saveVehiclesToCSVFile(vehicles, "CascoWithIndemnity.csv");
            System.out.println("Cascos are calculated with indemnity and wrtten to CascoWithIndemnity.csv file!");

            // Updating the vehicle values in database.
            vehiclesService.updateVehicles(vehicles);
            System.out.println("The vehicles data updated to database!");

    }

    public void initializeApp() throws IOException, SQLException {
        DB db = new DB(USER, PASS).createDatabaseTables(DDL_FILE_PATH);
        System.out.println("Database is initialised!\nVehicle Table is created!");
        CoeficientsData coeficientsData = DataParser.loadDataFile(JSON_DATA_FILE_PATH);

        CascoCalculator cascoCalculator = new CascoCalculator(coeficientsData);
        cascoCalculator.setCriterias(new HashSet<CalculationCriterias>(
                Set.of(CalculationCriterias.Make,
                        CalculationCriterias.VehicleAge,
                        CalculationCriterias.VehicleValue)));

        VehiclesDao vehiclesDao = new VehiclesDaoImpl(db.getConnection());
        LogHelper logHelper = new LogHelper(ERROR_FILE_PATH);
        VehicleCSVParser vehicleCSVParser = new VehicleCSVParser(logHelper);

        VehiclesService vehiclesService = new VehiclesService(
                vehiclesDao,
                vehicleCSVParser,
                logHelper,
                cascoCalculator);

        this.vehiclesService = vehiclesService;

    }
}
