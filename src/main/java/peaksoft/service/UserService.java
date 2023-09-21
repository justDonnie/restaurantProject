package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.UserRequest;
import peaksoft.dto.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserResponse> getAllUsers();

    Optional<UserResponse> getUserById(Long userId);

    SimpleResponse updateUser(Long userId, UserRequest newUserRequest);

    SimpleResponse deleteUser(Long userId);
}
