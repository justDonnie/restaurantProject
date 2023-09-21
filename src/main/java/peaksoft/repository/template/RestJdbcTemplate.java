package peaksoft.repository.template;

import peaksoft.dto.RestaurantResponse;

import java.util.List;
import java.util.Optional;

public interface RestJdbcTemplate {
    List<RestaurantResponse> getAllRestaurants();

    Optional<RestaurantResponse>findRestaurantById(Long restId);
}
