package task1.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

@Entity(name="vehicles")
@Getter
@Setter
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="plate_number", length=10, nullable=false, unique = true)
    private String plateNumber;

    @Column(name="firstRegistration", nullable=false)
    private int firstRegistration;

    @Column(name="purchase_prise", nullable=false)
    private double purchasePrise;

    @Column(name="producer")
    private String producer;

    @Column(name="milage")
    private int milage;

    @Column(name="previous_indemnity")
    private double previousIndemnity;

    @Column(name="casco_without_indemnity")
    private double cascoWithoutIndemnity;

    @Column(name="casco_with_indemnity")
    private double cascoWithIndemnity;

    public void setPlateNumber(String plateNumber){
        this.plateNumber = plateNumber;
    }

    public Vehicle( String plateNumber, int firstRegistration, double purchasePrise, String producer, int milage, double previousIndemnity ) {
        this.plateNumber = plateNumber.toUpperCase();
        this.firstRegistration = firstRegistration;
        this.purchasePrise = purchasePrise;
        this.producer = producer;
        this.milage = milage;
        this.previousIndemnity = previousIndemnity;
    }


    public Vehicle() {
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
