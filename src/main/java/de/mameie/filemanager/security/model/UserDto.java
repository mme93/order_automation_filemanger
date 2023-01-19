package de.mameie.filemanager.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private Long userId;

    private String password;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String callNumber;

    private String company;

    private String roll;

    private String info;

    public UserDto(String username, String firstName, String lastName, String email, String callNumber, String company, String roll, String info, Long userId) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.callNumber = callNumber;
        this.company = company;
        this.roll = roll;
        this.info = info;
        this.userId=userId;
    }
}
