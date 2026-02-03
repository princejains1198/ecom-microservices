package com.app.ecom.service;

import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.mapper.UserMapper;
import com.app.ecom.model.User;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> fetchAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> UserMapper.mapToUserResponse(user, new UserResponse()))
                .collect(Collectors.toList());
    }

    public void addUser(UserRequest userRequest) {
        User user = UserMapper.updateUserFromRequest(userRequest, new User());
        userRepository.save(user);
    }

    public Optional<UserResponse> fetchUser(Long id) {
        return userRepository.findById(id).map(user -> UserMapper.mapToUserResponse(user, new UserResponse()));
    }

    public Boolean updateUser(Long id, UserRequest updatedUserRequest) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    UserMapper.updateUserFromRequest(updatedUserRequest, existingUser);
                    userRepository.save(existingUser);
                    return true;
                })
                .orElse(false);
    }
}
