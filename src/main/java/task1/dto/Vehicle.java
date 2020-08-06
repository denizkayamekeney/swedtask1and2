package task1.dto;

import java.util.Calendar;
import java.util.Objects;

public class Vehicle {
    private int id;

    private String plate_number;
    private int first_registration;
    private double purchase_prise;
    private String producer;
    private int milage;
    private double previous_indemnity;

    private double casco_without_indemnity;
    private double casco_with_indemnity;

    public double getCascoWithoutIndemnity() {
        return casco_without_indemnity;
    }

    public void setCascoWithoutIndemnity( double casco_without_indemnity ) {
        this.casco_without_indemnity = casco_without_indemnity;
    }

    public double getCascoWithIndemnity() {
        return casco_with_indemnity;
    }

    public void setCascoWithIndemnity( double casco_with_indemnity ) {
        this.casco_with_indemnity = casco_with_indemnity;
    }


    public Vehicle( int id, String plate_number, int first_registration, double purchase_prise, String producer, int milage, double previous_indemnity ) {
        this.id = id;
        this.plate_number = plate_number;
        this.first_registration = first_registration;
        this.purchase_prise = purchase_prise;
        this.producer = producer;
        this.milage = milage;
        this.previous_indemnity = previous_indemnity;
    }


    public Vehicle() {
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getPlateNumber() {
        return plate_number;
    }

    public void setPlateNumber( String plate_number ) {
        this.plate_number = plate_number;
    }

    public int getFirstRegistration() {
        return first_registration;
    }

    public void setFirstRegistration( int first_registration ) {
        this.first_registration = first_registration;
    }

    public double getPurchasePrise() {
        return purchase_prise;
    }

    public void setPurchasePrise( double purchase_prise ) {
        this.purchase_prise = purchase_prise;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer( String producer ) {
        this.producer = producer;
    }

    public int getMilage() {
        return milage;
    }

    public void setMilage( int milage ) {
        this.milage = milage;
    }

    public double getPreviousIndemnity() {
        return previous_indemnity;
    }

    public void setPreviousIndemnity( double previous_indemnity ) {
        this.previous_indemnity = previous_indemnity;
    }

    public int getAge(){
        return Calendar.getInstance().get(Calendar.YEAR) - getFirstRegistration();
    }

    /**
     *   It overriden because we are using in object comperation.
     */
    @Override
    public boolean equals( Object o ) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;
        Vehicle vehicle = (Vehicle) o;
        return getId() == vehicle.getId() &&
                getFirstRegistration() == vehicle.getFirstRegistration() &&
                getPurchasePrise() == vehicle.getPurchasePrise() &&
                getMilage() == vehicle.getMilage() &&
                Double.compare(vehicle.getPreviousIndemnity(), getPreviousIndemnity()) == 0 &&
                getPlateNumber().equals(vehicle.getPlateNumber()) &&
                getProducer().equals(vehicle.getProducer()) &&
                Double.compare(vehicle.getCascoWithIndemnity(), getCascoWithIndemnity()) == 0 &&
                Double.compare(vehicle.getCascoWithoutIndemnity(), getCascoWithoutIndemnity()) == 0;
    }

    /**
     *   It overriden because we are using in HashMap.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPlateNumber(), getFirstRegistration(), getPurchasePrise(), getProducer(), getMilage(), getPreviousIndemnity());
    }

    @Override
    public String toString() {
        return new StringBuilder("[ id " + this.getId())
                .append(", plate_number : " + this.getPlateNumber())
                .append(", first_registration : " + this.getFirstRegistration())
                .append(", purchase_prise : " + this.getPurchasePrise())
                .append(", producer : " + this.getProducer())
                .append(", milage : " + this.getMilage())
                .append(", previous_indemnity : " + this.getPreviousIndemnity() )
                .append(", casco_without_indemnity : " + this.getCascoWithoutIndemnity())
                .append(", casco_with_indemnity : " + this.getCascoWithIndemnity() + "]")
                .toString();
    }

}
