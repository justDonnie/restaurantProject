package peaksoft.service;


import peaksoft.dto.AuthenticationResponse;
import peaksoft.dto.SignInRequest;
import peaksoft.dto.UserRegistrationRequest;

public interface AuthenticationService {

    AuthenticationResponse userRegistration(UserRegistrationRequest request);

    AuthenticationResponse signIn(SignInRequest signInRequest);

    void initMethod();

}
