package peaksoft.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.UserRequest;
import peaksoft.dto.UserResponse;
import peaksoft.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User API")
public class UserAPI {
    private final UserService userService;

    @GetMapping("/getAll")
    public List<UserResponse> getAllUser() {
        return userService.getAllUsers();
    }

    @GetMapping("/byId/{userId}")
    public Optional<UserResponse> findUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/update/{userId}")
    SimpleResponse updateUserById(@PathVariable Long userId,
                                  @RequestBody UserRequest request) {
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/delete/{userId}")
    SimpleResponse deleteUser(@PathVariable Long userId){
        return userService.deleteUser(userId);
    }
}
