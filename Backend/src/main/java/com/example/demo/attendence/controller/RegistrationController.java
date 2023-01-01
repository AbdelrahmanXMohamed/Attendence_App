package com.example.demo.attendence.controller;

import com.example.demo.attendence.Model.LoginRequestModel;
import com.example.demo.attendence.Model.LoginResponseModel;
import com.example.demo.attendence.registration.RegistrationRequest;
import com.example.demo.attendence.security.config.JwtUtil;
import com.example.demo.attendence.service.AppUserService;
import com.example.demo.attendence.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("registration")
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService ;
    private AppUserService appUserService ;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil ;

    @PostMapping
    public String register (@RequestBody RegistrationRequest request){

        return registrationService.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        System.out.println(token);
        return registrationService.confirmToken(token);
    }


    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponseModel signIn(@RequestBody LoginRequestModel loginRequestModel) throws Exception {

        final UserDetails userDetails =appUserService.loadUserByUsername(loginRequestModel.getEmail());


        String jwt= jwtUtil.generateToken(userDetails);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDetails.getUsername(),
                            loginRequestModel.getPassword()
                    )
                    );
        } catch (BadCredentialsException e)
        {
            throw new Exception("Incorrect username or password",e);
        }

        return new LoginResponseModel(jwt);
    }


}
