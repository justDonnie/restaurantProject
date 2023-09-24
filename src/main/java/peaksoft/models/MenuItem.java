package peaksoft.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;


@Entity
@Table(name = "menu_items")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MenuItem {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "menu_gen"
    )
    @SequenceGenerator(
            name = "menu_gen",
            sequenceName = "menu_seq",
            allocationSize = 1
    )
    private Long id;
    @Column(unique = true)
    private String name;
    private String image;
    @Positive(message = "Invalid price format !!!")
    private BigDecimal price;
    private String description;
    private Boolean isVegetarian;

    @ManyToOne(cascade = ALL,fetch = EAGER)
    private Restaurant restaurant;

    @ManyToMany(mappedBy = "menuItems",cascade = ALL,fetch = EAGER)
    private List<Cheque> cheques;

    @ManyToOne(cascade = {REFRESH,DETACH,PERSIST,MERGE},fetch = EAGER)
    private SubCategory subCategory;

    @OneToOne(cascade = ALL,fetch = EAGER)
    private StopList stopList;
    private LocalDate isBlocked;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getVegetarian() {
        return isVegetarian;
    }

    public void setVegetarian(Boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<Cheque> getCheques() {
        return cheques;
    }

    public void setCheques(List<Cheque> cheques) {
        this.cheques = cheques;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public StopList getStopList() {
        return stopList;
    }

    public void setStopList(StopList stopList) {
        this.stopList = stopList;
    }


    public void setIsBlocked(LocalDate isBlocked) {
        this.isBlocked = isBlocked;
    }
}