package task1;

import task1.dao.VehiclesDao;
import task1.dao.VehiclesDaoImpl;
import task1.dto.Data;
import task1.dto.Vehicle;
import task1.services.VehiclesService;
import task1.utils.Constants;
import task1.utils.DB;
import task1.utils.DataParser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class App {
    VehiclesService vehiclesService;

    public static void main( String[] args ) throws SQLException, IOException {
        DB.getInstance().createDatabaseTables();
        System.out.println("Tables are created...");
        Data data = DataParser.loadDataFile(Constants.JSON_DATA_FILE_PATH);
        System.out.println("Data file is parsed");
        App app = new App();
        VehiclesDao vehiclesDao = new VehiclesDaoImpl(DB.getInstance().getConnection());
        VehiclesService vehiclesService = new VehiclesService(vehiclesDao);
        app.vehiclesService =  vehiclesService;
        int a;
        try {

            vehiclesService.loadVehicleFromCsvFile(Constants.CSV_DATA_FILE_PATH);

        } catch (Exception exception){
            System.out.println(exception.getMessage());
        }

        List<Vehicle> vehicles = vehiclesService.findAll();
        System.out.println(vehicles.size());

        vehiclesService.calculateAllKasko();
    }
}
