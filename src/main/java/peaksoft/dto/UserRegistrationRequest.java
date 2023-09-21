package peaksoft.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import peaksoft.enums.Role;
import peaksoft.validation.PassValid;
import peaksoft.validation.PhoneNumberValidation;

import java.time.LocalDate;

@Builder
public record UserRegistrationRequest(

        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        @Email
        String email,
        @PassValid
        String password,
        @PhoneNumberValidation
        String phoneNumber,
        Role role,
        int experience

) {
}
