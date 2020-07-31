package dto;

public class Vehicle {
    private int id;
    private String plate_number;
    private int first_registration;
    private int purchase_prise;
    private String producer;
    private int milage;
    private double previous_indemnity;

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", plate_number='" + plate_number + '\'' +
                ", first_registration='" + first_registration + '\'' +
                ", purchase_prise=" + purchase_prise +
                ", producer='" + producer + '\'' +
                ", milage=" + milage +
                ", previous_indemnity=" + previous_indemnity +
                '}';
    }

    public Vehicle( int id, String plate_number, int first_registration, int purchase_prise, String producer, int milage, double previous_indemnity ) {
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

    public String getPlate_number() {
        return plate_number;
    }

    public void setPlate_number( String plate_number ) {
        this.plate_number = plate_number;
    }

    public int getFirst_registration() {
        return first_registration;
    }

    public void setFirst_registration( int first_registration ) {
        this.first_registration = first_registration;
    }

    public int getPurchase_prise() {
        return purchase_prise;
    }

    public void setPurchase_prise( int purchase_prise ) {
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

    public double getPrevious_indemnity() {
        return previous_indemnity;
    }

    public void setPrevious_indemnity( double previous_indemnity ) {
        this.previous_indemnity = previous_indemnity;
    }
}
