package task1.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import task1.services.VehiclesService;

@Component
@Profile("!test")
public class DataLoader implements ApplicationRunner {

    @Value("${data.vehicles.cvsfile}")
    private String vehiclesCSVFile;

    private VehiclesService vehiclesService;

    @Autowired
    public DataLoader(VehiclesService vehiclesService) {
        this.vehiclesService = vehiclesService;
    }

    public void run( ApplicationArguments args) {
        var vehicles = vehiclesService.loadVehicleFromCsvFile(vehiclesCSVFile);
        vehiclesService.insertVehicles(vehicles);
        System.out.println("Vehicles are inserted to databases!");
    }
}