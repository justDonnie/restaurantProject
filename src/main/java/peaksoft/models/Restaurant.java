package peaksoft.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import peaksoft.enums.CuisineType;

import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;


@Entity
@Table(name = "restaurants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Restaurant {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rest_gen"
    )
    @SequenceGenerator(
            name = "rest_gen",
            sequenceName = "rest_seq",
            allocationSize = 1
    )
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String location;
    @Enumerated(EnumType.STRING)
    private CuisineType restType;
    private int numberOfEmployees;
    private int services;

    @OneToMany(mappedBy = "restaurant",fetch = EAGER,cascade = {REFRESH,DETACH,MERGE,PERSIST})
    private List<User>users;

    @OneToMany(mappedBy = "restaurant",fetch = EAGER,cascade = {REFRESH,DETACH,MERGE,PERSIST})
    private List<MenuItem>menuItems;


    public Restaurant(String name, String location, CuisineType restType,int numberOfEmployees, int services) {
        this.name = name;
        this.location = location;
        this.restType = restType;
        this.numberOfEmployees=numberOfEmployees;
        this.services = services;
    }



    public void assign(User user) {            //user assigner to restaurant
        this.users.add(user);
    }
}