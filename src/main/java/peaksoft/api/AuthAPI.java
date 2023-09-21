package peaksoft.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.dto.AuthenticationResponse;
import peaksoft.dto.SignInRequest;
import peaksoft.dto.UserRegistrationRequest;
import peaksoft.service.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authentication API")
public class AuthAPI {

    private final AuthenticationService authenticationService;


    @PostMapping("/registration")
    @Operation(summary = "Registration ",description = "To registration fill the all columns")
    public AuthenticationResponse userRegistration(UserRegistrationRequest request){
        return authenticationService.userRegistration(request);
    }


    @GetMapping("/signIn")
    public AuthenticationResponse signIn(SignInRequest signInRequest){
        return authenticationService.signIn(signInRequest);
    }

}
