package peaksoft.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;


@Entity
@Table(name = "sub_categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class SubCategory {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sub_gen"
    )
    @SequenceGenerator(
            name = "sub_gen",
            sequenceName = "sub_seq",
            allocationSize = 1
    )
    private Long id;
    private String name;

    @OneToMany(mappedBy = "subCategory",fetch = EAGER,cascade ={REFRESH,DETACH,MERGE,PERSIST})
    private List<MenuItem> menuItems;

    @ManyToOne(cascade ={REFRESH,DETACH,MERGE,PERSIST},fetch = EAGER)
    private Category category;



    public SubCategory(Object name) {

    }
}