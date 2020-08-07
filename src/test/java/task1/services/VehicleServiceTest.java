package task1.services;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
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

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class VehicleServiceTest {
    @Autowired
    private VehiclesService vehiclesService;

    @Test
    public void calculateVehiclesCasco_without_indemnity_Test(){
        Vehicle vehicle1 = VehicleHelper.createRandomVehicle();
        vehicle1.setPreviousIndemnity(1000);

        // We dont put CalculationCriterias.PreviousIndemnity in calculations.
        vehiclesService.getCascoCalculator().setCriterias(new HashSet<>(Set.of(CalculationCriterias.Make,
                CalculationCriterias.VehicleAge,
                CalculationCriterias.VehicleValue)));

        List<Vehicle> vehicles = List.of(vehicle1);
        vehiclesService.calculateVehiclesCasco(vehicles);

        Assertions.assertTrue(vehicles.get(0).getCascoWithoutIndemnity() > 0);
        Assertions.assertTrue(vehicles.get(0).getCascoWithIndemnity() ==  0);
    }

    @Test
    public void calculateAnnualVehiclesCasco_with_indemnity_Test() {
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
    public void calculateAnnualVehiclesCasco_with_Skip_Test(){
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
