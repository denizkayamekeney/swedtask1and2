package task1.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity(name="groups")
@Getter
@Setter
public class CoefficientGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Size(min = 3, max=30)
    @Column(name="name", length=30, nullable=false, unique = true)
    private String name;

    @Size(min = 3, max=30)
    @Column(name="display_name", length=30, nullable=false)
    private String displayName;

    @Size(min = 3, max=30)
    @Column(name="data_type", length=30, nullable=false)
    private String dataType;

    public CoefficientGroup( String name, String displayName, String dataType ) {
        this.name = name;
        this.displayName = displayName;
        this.dataType = dataType;
    }

    public CoefficientGroup() {
    }
}