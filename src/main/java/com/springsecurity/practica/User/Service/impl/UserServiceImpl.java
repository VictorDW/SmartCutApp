package com.springsecurity.practica.User.Service.impl;

import com.springsecurity.practica.User.DTO.UserResponse;
import com.springsecurity.practica.User.Mapper.MapperUser;
import com.springsecurity.practica.User.Repository.UserRepository;
import com.springsecurity.practica.User.Service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(MapperUser::mapperUserToUserResponse)
                .toList();
    }
}
