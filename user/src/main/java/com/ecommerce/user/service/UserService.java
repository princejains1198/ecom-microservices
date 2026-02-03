package com.ecommerce.user.service;

import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.mapper.UserMapper;
import com.ecommerce.user.model.User;
import com.ecommerce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final KeyCloakAdminService keyCloakAdminService;

    public List<UserResponse> fetchAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> UserMapper.mapToUserResponse(user, new UserResponse()))
                .collect(Collectors.toList());
    }

    public void addUser(UserRequest userRequest) {
        String token = keyCloakAdminService.getAdminAccessToken();
        keyCloakAdminService.createUser(token, userRequest);
        String keycloakId = keyCloakAdminService.createUser(token, userRequest);
        User user = UserMapper.updateUserFromRequest(userRequest, new User());
        user.setKeycloakId(keycloakId);
        keyCloakAdminService.assignRealmRoleToUser(userRequest.getUsername(), userRequest.getRole().name(), keycloakId);
        userRepository.save(user);
    }

    public Optional<UserResponse> fetchUser(String id) {
        return userRepository.findById(id).map(user -> UserMapper.mapToUserResponse(user, new UserResponse()));
    }

    public Boolean updateUser(String id, UserRequest updatedUserRequest) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    UserMapper.updateUserFromRequest(updatedUserRequest, existingUser);
                    userRepository.save(existingUser);
                    return true;
                })
                .orElse(false);
    }
}
