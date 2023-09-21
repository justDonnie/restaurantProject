package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.UserRequest;
import peaksoft.dto.UserResponse;
import peaksoft.exceptions.NotFoundException;
import peaksoft.models.User;
import peaksoft.repository.UserRepository;
import peaksoft.repository.template.UserJdbcTemplate;
import peaksoft.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserJdbcTemplate userJdbcTemplate;

    @Override
    public List<UserResponse> getAllUsers(){
        return userJdbcTemplate.getAllUser();
    }

    @Override
    public Optional<UserResponse>getUserById(Long userId) {
        return userJdbcTemplate.findUserById(userId);
    }

    @Override
    public SimpleResponse updateUser(Long userId, UserRequest newUserRequest) {
        User user =userRepository.findUserById(userId).orElseThrow(
                () -> {
                    String massage = "Student with id: " + userId + " is not found!";
                    log.error(massage);
                    return  new NotFoundException(massage);
                });
        newUserRequest.userUpdate(user);
        userRepository.save(user);
        log.info("Successfully updated");
        return new SimpleResponse(
                HttpStatus.OK,
                "User is successfully updated !!!"
        );
    }

    @Override
    public SimpleResponse deleteUser(Long userId) {
        User user =userRepository.findUserById(userId).orElseThrow(
                () -> {
                    String massage = "Student with id: " + userId + " is not found!";
                    log.error(massage);
                    return  new NotFoundException(massage);
                });
        userRepository.delete(user);
        return new SimpleResponse(
                HttpStatus.OK,
                "User is successfully deleted !!!"
        );
    }
}
