package task1.dto;

import java.util.Map;

public class CoeficientsData {

    private Map<String,Double> coefficients;
    private Map<String,Double> make_coefficients;
    private Map<String,Double> avg_purchase_price;

    public CoeficientsData( Map<String, Double> coefficients, Map<String, Double> make_coefficients, Map<String, Double> avg_purchase_price ) {
        this.coefficients = coefficients;
        this.make_coefficients = make_coefficients;
        this.avg_purchase_price = avg_purchase_price;
    }

    public Double addCoefficient(String coefKey, Double coefValue){
        Map<String,Double> itemMap = getCoefficients();
        coefKey = coefKey.toLowerCase();
        return itemMap.put(coefKey, coefValue);
    }

    public Double addMakeCoefficient(String coefKey, Double coefValue){
        Map<String,Double> itemMap = getMake_coefficients();
        coefKey = coefKey.toLowerCase();
        return itemMap.put(coefKey,coefValue);
    }

    public Double addAvgPurchasePrise(String coefKey, Double coefValue){
        Map<String,Double> itemMap = getAvg_purchase_price();
        coefKey = coefKey.toUpperCase();
        return itemMap.put(coefKey,coefValue);
    }


    public Double deleteCoefficient(String coefKey){
        Map<String,Double> itemMap = getCoefficients();
        coefKey = coefKey.toLowerCase();
        return itemMap.remove(coefKey);
    }

    public Double deleteMakeCoefficient(String coefKey){
        Map<String,Double> itemMap = getMake_coefficients();
        coefKey = coefKey.toLowerCase();
        return itemMap.remove(coefKey);
    }

    public Double deleteAvgPurchasePrise(String coefKey){
        Map<String,Double> itemMap = getAvg_purchase_price();
        coefKey = coefKey.toUpperCase();
        return itemMap.remove(coefKey);
    }

    public Double getCoefficient(String coefKey){
        Map<String,Double> itemMap = getCoefficients();
        coefKey = coefKey.toLowerCase();
        return itemMap.get(coefKey);
    }

    public Double getMakeCoefficient(String coefKey){
        Map<String,Double> itemMap = getMake_coefficients();
        coefKey = coefKey.toLowerCase();
        return itemMap.get(coefKey);
    }

    public Double getAvgPurchasePrise(String coefKey){
        Map<String,Double> itemMap = getAvg_purchase_price();
        coefKey = coefKey.toUpperCase();
        return itemMap.get(coefKey);
    }


    public CoeficientsData() {
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
