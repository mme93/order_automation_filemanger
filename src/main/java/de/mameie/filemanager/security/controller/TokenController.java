package de.mameie.filemanager.security.controller;


import de.mameie.filemanager.security.model.JwtResponse;
import de.mameie.filemanager.security.service.SecurityUserService;
import de.mameie.filemanager.security.utility.JWTUtility;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    private final JWTUtility jwtUtility;
    private final SecurityUserService securityUserService;

    @Autowired
    public TokenController(JWTUtility jwtUtility, SecurityUserService securityUserService) {
        this.jwtUtility = jwtUtility;
        this.securityUserService = securityUserService;
    }

    @PostMapping("/isTokenExpired")
    public ResponseEntity<JwtResponse> isTokenExpired(@RequestBody JwtResponse jwtResponse) {
        String oldToken = jwtResponse.getJwtToken();
        try {
            if (this.jwtUtility.isTokenExpired(oldToken)) {
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            }
            jwtResponse.setJwtToken(
                    this.jwtUtility.generateToken(
                            securityUserService.loadUserByUsername(
                                    this.jwtUtility.getUsernameFromToken(oldToken)
                            )
                    )
            );
            return new ResponseEntity(jwtResponse, HttpStatus.OK);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }catch (IllegalArgumentException e){
            return  new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

}
