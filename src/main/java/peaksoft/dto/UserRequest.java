package peaksoft.dto;

import jakarta.validation.Valid;
import peaksoft.models.User;

public record UserRequest(
        @Valid
        User user
) {
    public UserRequest {
    }

    public User userBuild() {                   //for save methods
        return User.builder()
                .firstName(this.user.getFirstName())
                .lastName(this.user.getLastName())
                .dateOfBirth(this.user.getDateOfBirth())
                .email(this.user.getEmail())
                .password(this.user.getPassword())
                .phoneNumber(this.user().getPhoneNumber())
                .role(this.user.getRole())
                .experience(this.user.getExperience())
                .build();
    }

    public User userUpdate(User user) {
        user.setFirstName(this.user().getFirstName());
        user.setLastName(this.user.getLastName());
        user.setDateOfBirth(this.user.getDateOfBirth());
        user.setEmail(this.user.getEmail());
        user.setPassword(this.user.getPassword());
        user.setPhoneNumber(this.user.getPhoneNumber());
        user.setRole(this.user.getRole());
        user.setExperience(this.user.getExperience());
        return user;
    }
}
