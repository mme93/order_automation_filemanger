package de.mameie.filemanager.security.service;

import de.mameie.filemanager.security.model.UserDto;
import de.mameie.filemanager.security.model.UserEntity;
import de.mameie.filemanager.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(UserEntity userEntity){
        this.userRepository.save(userEntity);
    }

    public List<UserDto> findUsersByCompany(String company){
        List<UserDto> userDtoList= new ArrayList<>();
        for(UserEntity userEntity:userRepository.findAllByCompany(company)){
            userDtoList.add(new UserDto(
                    userEntity.getUsername(),
                    userEntity.getFirstName(),
                    userEntity.getLastName(),
                    userEntity.getEmail(),
                    userEntity.getCallNumber(),
                    userEntity.getCompany(),
                    userEntity.getRoll(),
                    userEntity.getInfo(),
                    userEntity.getId()
            ));
        }
        return userDtoList;
    }

    public UserDto findUserByName(String username){
        UserEntity userEntity=userRepository.findByUsername(username);
        return new UserDto(
                userEntity.getUsername(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                userEntity.getCallNumber(),
                userEntity.getCompany(),
                userEntity.getRoll(),
                userEntity.getInfo(),
                userEntity.getId()
        );
    }

}
