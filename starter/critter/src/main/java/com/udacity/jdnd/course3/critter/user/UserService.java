package com.udacity.jdnd.course3.critter.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    public Employee getUserById(Long id) {
//        return userRepository.findById(id).orElseThrow(
//                () -> new IllegalStateException("This id is not mapped to a user")
//        );
//    }
}
