package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.RestaurantRequest;
import peaksoft.dto.RestaurantResponse;
import peaksoft.dto.SimpleResponse;
import peaksoft.exceptions.BadCredentialException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.models.Restaurant;
import peaksoft.models.User;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.repository.template.RestJdbcTemplate;
import peaksoft.service.RestaurantService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j

public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestJdbcTemplate restJdbcTemplate;
    private final UserRepository userRepository;


    @Override
    public Optional<RestaurantResponse> getRestaurantById(Long restaurantId) {
        return restJdbcTemplate.findRestaurantById(restaurantId);
    }

    @Override
    public List<RestaurantResponse>getAllRestaurant() {
        return restJdbcTemplate.getAllRestaurants();
    }

    @Override
    public SimpleResponse deleteRestaurantById(Long restId) {
        Restaurant restaurant = restaurantRepository.findRestaurantById(restId).orElseThrow(
                () -> {
                    String massage = "Student with id: " + restId + " is not found!";
                    log.error(massage);
                    return new NotFoundException(massage);
                });
        restaurantRepository.delete(restaurant);
        return new SimpleResponse(
                HttpStatus.OK,
                "Restaurant is successfully deleted !!!"
        );
    }

    @Override
    public SimpleResponse updateRestaurant(Long restId, RestaurantRequest restaurantRequest) {
        Restaurant restaurant = restaurantRepository.findRestaurantById(restId).orElseThrow(
                () -> {
                    String massage = "Student with id: " + restId + " is not found!";
                    log.error(massage);
                    return new NotFoundException(massage);
                });
        restaurantRequest.updateRest(restaurant);
        restaurantRepository.save(restaurant);
        log.info("Restaurant is successfully updated !!!");
        return new SimpleResponse(
                HttpStatus.OK,
                "Restaurant is successfully updated !!!"
        );
    }

    @Override
    public SimpleResponse acceptEmployee(Long userId, String acceptOrReject) {
        Restaurant restaurant1 = null;
        for (Restaurant restaurant: restaurantRepository.findAll()) {
            restaurant1=restaurant;
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with ID: " + userId + " is not found !!!"));
        if (restaurant1!=null){
            if (acceptOrReject.equalsIgnoreCase("accept")){
                if (restaurant1.getNumberOfEmployees()<=15){
                    user.setRestaurant(restaurant1);
                    restaurant1.assign(user);
                    restaurant1.setNumberOfEmployees(restaurant1.getNumberOfEmployees()+1);
                    userRepository.save(user);
                    restaurantRepository.save(restaurant1);
                    return new SimpleResponse(
                            HttpStatus.OK,
                            "Employee is successfully confirmed !!!"
                    );
                }else{
                    throw new BadCredentialException("Employee doesn't confirmed !!!");
                }
            } else if (acceptOrReject.equalsIgnoreCase("reject")) {
                userRepository.delete(user);
                return new SimpleResponse(
                        HttpStatus.OK,
                        "Employee is rejected !!!"
                );
            }
        }
        return null;
    }
}
