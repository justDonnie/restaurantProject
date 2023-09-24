package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.models.Restaurant;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Optional<Restaurant> findRestaurantById(Long restId);


    @Query("select new peaksoft.dto.RestaurantResponse(r.id,r.name,r.location,r.restType,r.numberOfEmployees,r.services)from Restaurant r")
    List<Restaurant> allRestaurant();

}
