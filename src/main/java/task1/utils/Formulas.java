package task1.utils;

public interface Formulas {

    static double getPurchasePrisePercentage( int age, int mileage ) {
        return 102 + (-7.967) * age +
                0.8337334 * Math.pow(age, 2) +
                (-0.07785488) * Math.pow(age, 3) +
                (0.002518395) * Math.pow(age, 4) +
                (-0.0002236396) * mileage +
                (3.669157e-10) * Math.pow(mileage, 2) +
                (-1.813681e-16) * Math.pow(mileage, 3);
    }

    static double getAnnualFee( int age, int mileage ) {
        return 102 + (-7.967) * age +
                0.8337334 * Math.pow(age, 2) +
                (-0.07785488) * Math.pow(age, 3) +
                (0.002518395) * Math.pow(age, 4) +
                (-0.0002236396) * mileage +
                (3.669157e-10) * Math.pow(mileage, 2) +
                (-1.813681e-16) * Math.pow(mileage, 3);
    }

}
