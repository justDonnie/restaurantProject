package peaksoft.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import peaksoft.enums.CuisineType;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RestaurantResponse {
    private Long id;
    private String name;
    private String location;
    private CuisineType restType;
    private int numberOfEmployees;
    private int services;


    public RestaurantResponse(Long id, String name, String location, CuisineType restType, int numberOfEmployees, int services) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.restType = restType;
        this.numberOfEmployees = numberOfEmployees;
        this.services = services;
    }

    public RestaurantResponse(String name, String location, CuisineType restType, int numberOfEmployees, int services) {
        this.name = name;
        this.location = location;
        this.restType = restType;
        this.numberOfEmployees=numberOfEmployees;
        this.services = services;
    }



    public RestaurantResponse() {
    }
}
