package peaksoft.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.dto.AuthenticationResponse;
import peaksoft.dto.SignInRequest;
import peaksoft.dto.UserRegistrationRequest;
import peaksoft.enums.CuisineType;
import peaksoft.enums.Role;
import peaksoft.exceptions.*;
import peaksoft.models.Restaurant;
import peaksoft.models.User;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.security.jwt.JwtService;
import peaksoft.service.AuthenticationService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RestaurantRepository restaurantRepository;


    @Override
    public AuthenticationResponse userRegistration(UserRegistrationRequest request) {
        try {
            if (userRepository.existsByEmail(request.email())) {
                throw new AlreadyExistException(
                        "User with email " + request.email() + " is already exists!"
                );
            }
            if (request.firstName().isBlank() &&
                    request.lastName().isBlank() &&
                    request.dateOfBirth() == null &&
                    request.email().isBlank() &&
                    request.password().isBlank() &&
                    request.phoneNumber().isBlank() &&
                    request.role() == null &&
                    request.experience() < 0) {
                throw new BadCredentialException(" Columns shouldn't be empty  !!!");
            }
            if (request.role()==(Role.CHEF)){
                if (request.experience()<2){
                    throw new BadRequestException(" The CHEF must have more than 2 years of experience!!!");
                }
                int now = LocalDate.now().getYear();
                int dateOfBirth = request.dateOfBirth().getYear();
                int num = now-dateOfBirth;
                if (num>45||num<25){
                    throw new BadCredentialException(" The CHEF's age must be between 25 and 45 years !!!");
                }
            }
            if (request.role()==(Role.WAITER)){
                if (request.experience()<1){
                    throw new BadRequestException(" The WAITER must have more than 1 years of experience!!!");
                }
                int now = LocalDate.now().getYear();
                int dateOfBirth = request.dateOfBirth().getYear();
                int num = now-dateOfBirth;
                if(num>30||num<18){
                    throw new BadCredentialException(" The WAITER's age must be between 18 and 30 years !!! ");
                }
            }
            User user = new User();
            user.setFirstName(request.firstName());
            user.setLastName(request.lastName());
            user.setDateOfBirth(request.dateOfBirth());
            user.setEmail(request.email());
            user.setPassword(passwordEncoder.encode(request.password()));
            user.setPhoneNumber(request.phoneNumber());
            user.setRole(request.role());
            user.setExperience(request.experience());
            userRepository.save(user);
            String token = jwtService.generateToken(user);
            System.out.println("User with Id: " + user.getId() + " is successfully registered !!!");
            return AuthenticationResponse.builder()
                    .token(token)
                    .email(user.getEmail())
                    .role(user.getRole())
                    .build();
        } catch (AuthenticationException e) {
            throw new AuthenticationException(" Input the correct values !!!");
        }
    }

    @Override
    public AuthenticationResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.getUserByEmail(signInRequest.email())
                .orElseThrow(() -> new NotFoundException(
                        "User with  email " + signInRequest.email() + " is not found"
                ));
        if (signInRequest.email().isBlank()) {
            throw new BadCredentialException("Email is blanked!!!");
        }
        if (!passwordEncoder.matches(signInRequest.password(), user.getPassword())) {
            throw new BadCredentialException("Wrong password!!!");
        }
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @PostConstruct
    @Override
    public void initMethod() {
        User user = new User();
        user.setFirstName("Admin");
        user.setLastName("Adminov");
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("admin1"));
        user.setPhoneNumber("+996777777777");
        user.setRole(Role.ADMIN);
        if (!userRepository.existsByEmail("admin@gmail.com")) {
            Restaurant restaurant = new Restaurant();
            restaurant.setName("Florance");
            restaurant.setLocation("Italy,Florencia province");
            restaurant.setRestType(CuisineType.ITALIAN);
            restaurant.setServices(15);
            restaurant.setNumberOfEmployees(0);
            restaurant.setUsers(List.of(user));
            restaurantRepository.save(restaurant);
            user.setRestaurant(restaurant);
            userRepository.save(user);
        }
    }


}
