package peaksoft.service;

import peaksoft.dto.*;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {

    AuthenticationResponse saveEmployee(UserRequest userRequest);

    Optional<RestaurantResponse> getRestaurantById(Long restaurantId);

    SimpleResponse deleteRestaurantById(Long restId);

    List<RestaurantResponse> getAllRestaurant();

    SimpleResponse updateRestaurant(Long restId, RestaurantRequest restaurantRequest);

    SimpleResponse acceptEmployee(Long userId, String acceptOrReject);

}
