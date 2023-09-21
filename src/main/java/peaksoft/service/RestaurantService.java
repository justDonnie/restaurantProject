package peaksoft.service;

import peaksoft.dto.RestaurantRequest;
import peaksoft.dto.RestaurantResponse;
import peaksoft.dto.SimpleResponse;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {

    Optional<RestaurantResponse> getRestaurantById(Long restaurantId);

    SimpleResponse deleteRestaurantById(Long restId);

    List<RestaurantResponse> getAllRestaurant();

    SimpleResponse updateRestaurant(Long restId, RestaurantRequest restaurantRequest);

    SimpleResponse acceptEmployee(Long userId, String acceptOrReject);

}
