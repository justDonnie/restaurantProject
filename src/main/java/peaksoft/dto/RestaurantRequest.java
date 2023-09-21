package peaksoft.dto;

import jakarta.validation.Valid;
import peaksoft.models.Restaurant;

public record RestaurantRequest(
        @Valid
        Restaurant restaurant
) {
    public RestaurantRequest {
    }

    public Restaurant restaurantBuild(){
        return Restaurant.builder()
                .name(this.restaurant.getName())
                .location(this.restaurant.getLocation())
                .restType(this.restaurant.getRestType())
                .numberOfEmployees(this.restaurant.getNumberOfEmployees())
                .services(this.restaurant.getServices())
                .build();
    }

    public Restaurant updateRest(Restaurant restaurant){
        restaurant.setName(this.restaurant.getName());
        restaurant.setLocation(this.restaurant.getLocation());
        restaurant.setRestType(this.restaurant().getRestType());
        restaurant.setNumberOfEmployees(this.restaurant.getNumberOfEmployees());
        restaurant.setServices(this.restaurant.getServices());
        return restaurant;
    }

}
