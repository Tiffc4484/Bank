package com.example.Banking.service;

import com.example.Banking.entity.User;
import com.example.Banking.payload.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    List<UserDto> getAllUsers();

    UserDto getUserById(long id);

    UserDto updateUser(UserDto postDto, long id);

    void deleteUserById(long id);
}
