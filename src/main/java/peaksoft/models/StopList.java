package peaksoft.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;


@Entity
@Table(name = "stop_lists")
@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
public class StopList {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sList_gen"
    )
    @SequenceGenerator(
            name = "sList_gen",
            sequenceName = "sList_seq",
            allocationSize = 1
    )
    private Long id;
    private String reason;
    private LocalDate date;

    @OneToOne(cascade = {REFRESH,DETACH,MERGE,PERSIST},fetch = EAGER)
    private MenuItem menuItem;


    public StopList(Long id, String reason, LocalDate date, MenuItem menuItem) {
        this.id = id;
        this.reason = reason;
        this.date = date;
        this.menuItem = menuItem;
    }
    public StopList(String reason, LocalDate date) {

    }
}