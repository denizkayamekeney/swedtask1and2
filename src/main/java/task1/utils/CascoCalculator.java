package task1.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;
import task1.dto.CalculationCriterias;
import task1.dto.CoeficientsData;
import task1.dto.Vehicle;

import java.util.*;

@Component
public class CascoCalculator {

    // It hold the data JSON as an object for taking calculation parameters.
    CoeficientsData cooefficientData;

    // It holds the risk criteria that will be taken into account in casco calculation.
    private HashSet<CalculationCriterias>
            criterias = new HashSet<>();

    @Autowired
    public CascoCalculator( CoeficientsData cooefficientData ) {
        this.cooefficientData = cooefficientData;
    }

    public CoeficientsData getCooefficientData() {
        return cooefficientData;
    }

    public void setCooefficientData( CoeficientsData cooefficientData ) {
        this.cooefficientData = cooefficientData;
    }


    public HashSet<CalculationCriterias> getCriterias() {
        return criterias;
    }

    public void setCriterias( HashSet<CalculationCriterias> criterias ) {
        this.criterias = criterias;
    }

    public void addCriterias( CalculationCriterias... criterias ) {
        for (CalculationCriterias criteria : criterias) {
            this.criterias.add(criteria);
        }
    }

    public void removeCriterias( CalculationCriterias... criterias ) {
        for (CalculationCriterias criteria : criterias) {
            this.criterias.remove(criteria);
        }
    }

    /**
     *   It calculates the purchase prise percentage according given formula!
     */
    public static double getPurchasePrisePercentage( int age, int mileage ) {
        return 102 + (-7.967) * age +
                0.8337334 * Math.pow(age, 2) +
                (-0.07785488) * Math.pow(age, 3) +
                (0.002518395) * Math.pow(age, 4) +
                (-0.0002236396) * mileage +
                (3.669157e-10) * Math.pow(mileage, 2) +
                (-1.813681e-16) * Math.pow(mileage, 3);
    }

    /**
     *   It returns the annual casco fee for a vehicle according the risk criteria!
     *   It  loops all risk criterias given in "criterias" set and adds up. If the
     *   Producer does not in avg_purchase_prise list in data file, then it is skipping.
     */
    public double getAnnualFee( Vehicle vehicle ) {
        double sumRiskValues = 0;

        Double avgPurchasePrise = cooefficientData.getAvgPurchasePrise(vehicle.getProducer());

        if (avgPurchasePrise == null)  return -1;

        Double coef, makeRisk = 1.0;

        for (CalculationCriterias criteria : criterias) {
            switch (criteria) {
                case Make:
                    coef = cooefficientData.getMakeCoefficient(vehicle.getProducer());
                    makeRisk = (coef != null) ? coef : 1;
                    break;
                case VehicleAge:
                    coef = cooefficientData.getCoefficient("vehicle_age");
                    sumRiskValues += coef * vehicle.getAge();
                    break;
                case VehicleValue:
                    sumRiskValues += 0.01 * getPurchasePrisePercentage(vehicle.getAge(), vehicle.getMilage()) *
                            cooefficientData.getCoefficient("vehicle_value") *
                            ((vehicle.getPurchasePrise() > 0)?vehicle.getPurchasePrise(): avgPurchasePrise);
                    break;
                case PreviousIndemnity:
                    sumRiskValues += cooefficientData.getCoefficient("previous_indemnity") *
                            vehicle.getPreviousIndemnity();
            }
        }
        return sumRiskValues *= makeRisk;
    }

    public Map<String,String> getCoefficientsForDiplay() {
        Map<String,String> displayCoeffients = new LinkedHashMap<>();
        displayCoeffients.put("make","Make Coefficient");
        for (Map.Entry<String, Double> entry : cooefficientData.getCoefficients().entrySet()) {
            displayCoeffients.put(entry.getKey(),
                    StringUtils.capitalizeWords(entry.getKey().replace('_',' ')));
        }
        return displayCoeffients;
    }

    public LinkedHashSet<CalculationCriterias> getCriteriasFromHashKeys(LinkedHashSet<String> keys) {
        LinkedHashSet<CalculationCriterias> criterias = new LinkedHashSet<>();
        for (String key : keys) {
            switch (key) {
                case "make":
                    criterias.add(CalculationCriterias.Make);
                    break;
                case "vehicle_age":
                    criterias.add(CalculationCriterias.VehicleAge);
                    break;
                case "vehicle_value":
                    criterias.add(CalculationCriterias.VehicleValue);
                    break;
                case "previous_indemnity":
                    criterias.add(CalculationCriterias.PreviousIndemnity);
                    break;
                default:
                    criterias.add(CalculationCriterias.NonSpecific);
                    break;
            }
        }
        return criterias;
    }

}
