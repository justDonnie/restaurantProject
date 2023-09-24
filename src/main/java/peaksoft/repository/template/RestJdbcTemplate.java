package peaksoft.repository.template;

import org.springframework.stereotype.Repository;
import peaksoft.dto.RestaurantResponse;
import peaksoft.models.Restaurant;

import java.util.List;
import java.util.Optional;
@Repository
public interface RestJdbcTemplate {
    List<RestaurantResponse> getAllRestaurants();

    Optional<RestaurantResponse>findRestaurantById(Long restId);
}
