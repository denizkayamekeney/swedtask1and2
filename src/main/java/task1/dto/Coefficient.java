package task1.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name="coefficient")
@Getter
@Setter
public class Coefficient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @Size(min = 3, max=30)
    @Column(name="name", length=30, nullable=false, unique = true)
    private String name;

    @NotNull
    @Size(min = 3, max=50)
    @Column(name="display_name", length=50, nullable=false)
    private String displayName;

    @NotNull
    @Column(name="data_value", length=20, nullable=false)
    private String dataValue;

    @NotNull
    @Column(name="group_id", nullable=false)
    private int groupId;

    public Coefficient( @NotNull @Size(min = 3, max = 30) String name,
                        @NotNull @Size(min = 3, max = 50) String displayName,
                        @NotNull String dataValue,
                        @NotNull int groupId ) {
        this.name = name;
        this.displayName = displayName;
        this.dataValue = dataValue;
        this.groupId = groupId;
    }

    public Coefficient() {
    }
}
