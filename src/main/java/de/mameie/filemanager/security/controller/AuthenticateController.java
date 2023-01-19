package de.mameie.filemanager.security.controller;

import de.mameie.filemanager.security.model.JwtRequest;
import de.mameie.filemanager.security.model.Login;
import de.mameie.filemanager.security.model.UserDto;
import de.mameie.filemanager.security.service.SecurityUserService;
import de.mameie.filemanager.security.service.UserService;
import de.mameie.filemanager.security.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class AuthenticateController {

    private final JWTUtility jwtUtility;
    private final AuthenticationManager authenticationManager;
    private final SecurityUserService securityUserService;
    private final UserService userService;

    @Autowired
    public AuthenticateController(JWTUtility jwtUtility, AuthenticationManager authenticationManager, SecurityUserService securityUserService, UserService userService) {
        this.jwtUtility = jwtUtility;
        this.authenticationManager = authenticationManager;
        this.securityUserService = securityUserService;
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public Object authenticate(@RequestBody JwtRequest jwtRequest) throws Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            return  new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (IllegalArgumentException e){
            return  new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        final UserDetails userDetails
                = securityUserService.loadUserByUsername(jwtRequest.getUsername());
        final String token =
                jwtUtility.generateToken(userDetails);
        UserDto userDto =this.userService.findUserByName(jwtRequest.getUsername());
        return  new Login(token,userDto.getCompany(),userDto.getUserId());
    }

}
