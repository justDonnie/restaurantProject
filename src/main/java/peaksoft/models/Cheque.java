package peaksoft.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;


@Entity
@Table(name = "cheques")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Cheque {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cheque_gen"
    )
    @SequenceGenerator(
            name = "cheque_gen",
            sequenceName = "cheque_seq",
            allocationSize = 1
    )
    private Long id;
    private BigDecimal priceAverage;
    private ZonedDateTime createdAt;

    @ManyToOne(cascade = {CascadeType.PERSIST,MERGE,REFRESH,DETACH})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade ={CascadeType.PERSIST,MERGE,REFRESH,DETACH},fetch = EAGER)
    private List<MenuItem>menuItems;

}