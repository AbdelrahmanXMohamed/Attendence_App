package com.example.demo.attendence.controller;


import com.example.demo.attendence.model.LoginRequestModel;
import com.example.demo.attendence.model.LoginResponseModel;
import com.example.demo.attendence.model.RegistrationRequestModel;
import com.example.demo.attendence.model.UserRequestModel;
import com.example.demo.attendence.security.JwtUtil;
import com.example.demo.attendence.service.UserService;
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

    private UserService userService ;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil ;

    @PostMapping
    public String register (@RequestBody UserRequestModel request){

        return userService.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        System.out.println(token);
        return userService.confirmToken(token);
    }
    
    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponseModel signIn(@RequestBody LoginRequestModel loginRequestModel) throws Exception {

        final UserDetails userDetails =userService.loadUserByUsername(loginRequestModel.getEmail());


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
