package peaksoft.models;

import jakarta.persistence.*;
import lombok.*;
import peaksoft.validation.UniqueName;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;


@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Category {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "category_gen"
    )
    @SequenceGenerator(
            name = "category_gen",
            sequenceName = "category_seq",
            allocationSize = 1
    )
    private Long id;
    @UniqueName
    private String name;

    @OneToMany(mappedBy = "category",fetch = EAGER,cascade = ALL)
    private List<SubCategory> subCategories;
}