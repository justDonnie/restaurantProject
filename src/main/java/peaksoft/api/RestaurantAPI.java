package peaksoft.api;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.*;
import peaksoft.service.RestaurantService;

import java.util.List;
import java.util.Optional;




@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
@Tag(name = "Restaurant API")
public class RestaurantAPI {
    private final RestaurantService restaurantService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public AuthenticationResponse saveEmployee(@RequestBody UserRequest userRequest){
        return restaurantService.saveEmployee(userRequest);
    }

    @PermitAll
    @GetMapping("/getAll")
    List<RestaurantResponse>getAllRestaurants(){
        return restaurantService.getAllRestaurant();
    }
    @PermitAll
    @GetMapping("/byId/{restId}")
    Optional<RestaurantResponse>findById(@PathVariable Long restId){
        return restaurantService.getRestaurantById(restId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{restId}")
    SimpleResponse updateRestaurant(@PathVariable Long restId,
                                    @RequestBody RestaurantRequest request){
        return restaurantService.updateRestaurant(restId,request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{restId}")
    SimpleResponse deleteRestaurant(@PathVariable Long restId){
        return restaurantService.deleteRestaurantById(restId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/accept/{userId}")
    SimpleResponse assignEmployee(@PathVariable Long userId,
                                  @RequestParam String acceptOrReject){
        return restaurantService.acceptEmployee(userId,acceptOrReject);
    }

}
