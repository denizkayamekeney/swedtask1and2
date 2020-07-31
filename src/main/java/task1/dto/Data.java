package task1.dto;

import java.util.Map;

public class Data {
    Map<String,Double> coefficients;
    Map<String,Double> make_coefficients;
    Map<String,Double> avg_purchase_price;

    public Data( Map<String, Double> coefficients, Map<String, Double> make_coefficients, Map<String, Double> avg_purchase_price ) {
        this.coefficients = coefficients;
        this.make_coefficients = make_coefficients;
        this.avg_purchase_price = avg_purchase_price;
    }

    public Data() {
    }

    public Map<String, Double> getCoefficients() {
        return coefficients;
    }

    public void setCoefficients( Map<String, Double> coefficients ) {
        this.coefficients = coefficients;
    }

    public Map<String, Double> getMake_coefficients() {
        return make_coefficients;
    }

    public void setMake_coefficients( Map<String, Double> make_coefficients ) {
        this.make_coefficients = make_coefficients;
    }

    public Map<String, Double> getAvg_purchase_price() {
        return avg_purchase_price;
    }

    public void setAvg_purchase_price( Map<String, Double> avg_purchase_price ) {
        this.avg_purchase_price = avg_purchase_price;
    }
}
