package task.util;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import task1.App;
import task1.dao.VehicleHelper;
import task1.dto.CalculationCriterias;
import task1.dto.CoeficientsData;
import task1.dto.Vehicle;
import task1.utils.*;

import java.io.IOException;
import java.util.Set;

@SpringBootTest(classes = App.class)
@TestPropertySource("classpath:application-test.properties")
public class CascoCalculatorTest {

    @Autowired
    CascoCalculator cascoCalculator;

    @BeforeEach
    public void emptyCriterias() {
        cascoCalculator.getCriterias().clear();
    }

    @Test
    public void addCriteriaCheck() {
        cascoCalculator.addCriterias(CalculationCriterias.Make,
                CalculationCriterias.VehicleAge,
                CalculationCriterias.VehicleValue);

        Assertions.assertTrue(cascoCalculator.getCriterias()
                        .containsAll(Set.of(CalculationCriterias.Make,
                                CalculationCriterias.VehicleAge,
                                CalculationCriterias.VehicleValue)
                        ),
                "It needs to include three criteria"
        );
    }

    @Test
    public void deleteCriteriaCheck() {
        cascoCalculator.addCriterias(CalculationCriterias.Make,
                CalculationCriterias.VehicleAge,
                CalculationCriterias.VehicleValue);

        Assertions.assertTrue(cascoCalculator.getCriterias()
                        .containsAll(Set.of(CalculationCriterias.Make,
                                CalculationCriterias.VehicleAge,
                                CalculationCriterias.VehicleValue)
                        ),
                "It needs to include three criteria"
        );

        cascoCalculator.removeCriterias(CalculationCriterias.VehicleAge,
                CalculationCriterias.VehicleValue);

        Assertions.assertTrue(cascoCalculator.getCriterias().size() == 1,
                "It needs to include only one criteria"
        );

    }

    @Test
    public void getPurchasePrisePercentageCheck() {
        Vehicle vehicle = VehicleHelper.createRandomVehicle();
        Double result = cascoCalculator.getPurchasePrisePercentage(vehicle.getAge(), vehicle.getMilage());
        Double expected = 102 + (-7.967) * vehicle.getAge() +
                0.8337334 * Math.pow(vehicle.getAge(), 2) +
                (-0.07785488) * Math.pow(vehicle.getAge(), 3) +
                (0.002518395) * Math.pow(vehicle.getAge(), 4) +
                (-0.0002236396) * vehicle.getMilage() +
                (3.669157e-10) * Math.pow(vehicle.getMilage(), 2) +
                (-1.813681e-16) * Math.pow(vehicle.getMilage(), 3);

        Assertions.assertTrue(result.compareTo(expected) == 0,
                "The values should be equal!"
        );

    }

    @Test
    public void getAnnualFee_skipWhen_ProducerNotInAvgPurchasePriseList() {
        Vehicle vehicle = VehicleHelper.createRandomVehicle();
        vehicle.setProducer("UNKNOWN_PRODUCER");

        Double result = cascoCalculator.getAnnualFee(vehicle);

        Assertions.assertTrue(result.compareTo(-1d) == 0,
                "The value should be -1 (minus one)!"
        );
    }

    @Test
    public void getAnnualFee_ProducerInAvgPurchasePriseList() {
        Vehicle vehicle = VehicleHelper.createRandomVehicle();
        vehicle.setProducer("PORSCHE"); // It is in the list coefficients purchase list
        cascoCalculator.getCooefficientData();

        Double result = cascoCalculator.getAnnualFee(vehicle);

        Assertions.assertTrue(result.compareTo(-1d) != 0,
                "The value should be -1 (minus one)!"
        );
    }

    @Test
    public void getAnnualFee_VehicleInPurchasePriseList() {
        Vehicle vehicle = VehicleHelper.createRandomVehicle();
        vehicle.setProducer("PORSCHE"); // It is in the list coefficients purchase list
        cascoCalculator
                .addCriterias(CalculationCriterias.Make, CalculationCriterias.VehicleAge, CalculationCriterias.VehicleValue);

        CoeficientsData coefsData = cascoCalculator.getCooefficientData();
        Double makeRisk = coefsData.getMakeCoefficient(vehicle.getProducer());
        Double ageRisk = coefsData.getCoefficient("vehicle_age") * vehicle.getAge();


        Double pricePercentage = cascoCalculator.getPurchasePrisePercentage(vehicle.getAge(), vehicle.getMilage());
        Double valueRisk = 0.01 * pricePercentage * coefsData.getCoefficient("vehicle_value") * vehicle.getPurchasePrise();

        Double expected = makeRisk * (ageRisk + valueRisk);

        Double calculated = cascoCalculator.getAnnualFee(vehicle);

        Assertions.assertTrue(expected.compareTo(calculated) == 0,
                "The expected and calculated annual fee should be equal!"
        );
    }

    @Test
    public void getAnnualFee_VehicleInPurchasePriseList_VehiclePurchasePriseEmpty() {
        Vehicle vehicle = VehicleHelper.createRandomVehicle();
        vehicle.setProducer("PORSCHE"); // It is in the list coefficients purchase list
        vehicle.setPurchasePrise(0); // It is in the list coefficients purchase list
        cascoCalculator
                .addCriterias(CalculationCriterias.Make, CalculationCriterias.VehicleAge, CalculationCriterias.VehicleValue);

        CoeficientsData coefsData = cascoCalculator.getCooefficientData();
        Double makeRisk = coefsData.getMakeCoefficient(vehicle.getProducer());
        Double ageRisk = coefsData.getCoefficient("vehicle_age") * vehicle.getAge();

        Double pricePercentage = cascoCalculator.getPurchasePrisePercentage(vehicle.getAge(), vehicle.getMilage());

        Double valueRisk = 0.01 * pricePercentage
                                * coefsData.getCoefficient("vehicle_value")
                                * coefsData.getAvgPurchasePrise(vehicle.getProducer());

        Double expected = makeRisk * (ageRisk + valueRisk);

        Double calculated = cascoCalculator.getAnnualFee(vehicle);

        Assertions.assertTrue(expected.compareTo(calculated) == 0,
                "It should take the purchase prise from avg_purchase_price of Json data file!"
        );
    }

    @Test
    public void getAnnualFee_MakeValueIsOne() {
        Vehicle vehicle = VehicleHelper.createRandomVehicle();
        vehicle.setProducer("DACIA"); // It is not in the list coefficients purchase list
        cascoCalculator
                .addCriterias(CalculationCriterias.Make, CalculationCriterias.VehicleAge, CalculationCriterias.VehicleValue);

        CoeficientsData coefsData = cascoCalculator.getCooefficientData();
        Double makeRisk = 1.0;
        Double ageRisk = coefsData.getCoefficient("vehicle_age") * vehicle.getAge();

        Double pricePercentage = cascoCalculator.getPurchasePrisePercentage(vehicle.getAge(), vehicle.getMilage());
        Double valueRisk = 0.01 * pricePercentage
                * coefsData.getCoefficient("vehicle_value")
                * vehicle.getPurchasePrise();
        Double expected = makeRisk * (ageRisk + valueRisk);
        Double calculated = cascoCalculator.getAnnualFee(vehicle);

        Assertions.assertTrue(expected.compareTo(calculated) == 0,
                "The make risk should be taken as 1 in calculation!"
        );

    }

    @Test
    public void getAnnualFee_withIndemnity() {
        Vehicle vehicle = VehicleHelper.createRandomVehicle();
        vehicle.setProducer("VOLVO"); // It is in the list coefficients purchase list
        vehicle.setPreviousIndemnity(1500); // It is in the list coefficients purchase list
        cascoCalculator.addCriterias(
                CalculationCriterias.Make,
                CalculationCriterias.VehicleAge,
                CalculationCriterias.VehicleValue,
                CalculationCriterias.PreviousIndemnity);
        CoeficientsData coefsData = cascoCalculator.getCooefficientData();
        Double makeRisk = coefsData.getMakeCoefficient(vehicle.getProducer());
        Double ageRisk = coefsData.getCoefficient("vehicle_age") * vehicle.getAge();

        Double pricePercentage = cascoCalculator.getPurchasePrisePercentage(vehicle.getAge(), vehicle.getMilage());
        Double valueRisk = 0.01 * pricePercentage
                * coefsData.getCoefficient("vehicle_value")
                * vehicle.getPurchasePrise();
        Double indemnityRisk = coefsData.getCoefficient("previous_indemnity") * vehicle.getPreviousIndemnity();

        Double expected = makeRisk * (ageRisk + valueRisk + indemnityRisk);
        Double calculated = cascoCalculator.getAnnualFee(vehicle);

        Assertions.assertTrue(expected.compareTo(calculated) == 0,
                "Indemnity needs to be taken into account in annual casco calculation!"
        );
    }
}
