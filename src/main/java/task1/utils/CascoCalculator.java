package task1.utils;

import task1.dto.CalculationCriterias;
import task1.dto.CoeficientsData;
import task1.dto.Vehicle;

import java.util.*;

public class CascoCalculator {

    CoeficientsData cooefficientData;

    private HashSet<CalculationCriterias>
            criterias = new HashSet<>();

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


    public static double getPurchasePrisePercentage( int age, int mileage ) {
        return 102 + (-7.967) * age +
                0.8337334 * Math.pow(age, 2) +
                (-0.07785488) * Math.pow(age, 3) +
                (0.002518395) * Math.pow(age, 4) +
                (-0.0002236396) * mileage +
                (3.669157e-10) * Math.pow(mileage, 2) +
                (-1.813681e-16) * Math.pow(mileage, 3);
    }

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


}
