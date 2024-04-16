package com.example.Banking.service.Impl;

import com.example.Banking.dao.UserRepository;
import com.example.Banking.exception.ResourceNotFoundException;
import com.example.Banking.payload.UserDto;
import com.example.Banking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Banking.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = mapToEntity(userDto);
        User userSaved = userRepository.save(user);

        UserDto response = mapToDto(userSaved);
        return response;
    }


    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        return mapToDto(user);
    }

    @Override
    public UserDto updateUser(UserDto postDto, long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        user.setName(postDto.getName());
        user.setEmail(postDto.getEmail());
        user.setAddress(postDto.getAddress());

        User userSaved = userRepository.save(user);

        return mapToDto(userSaved);
    }

    @Override
    public void deleteUserById(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(user);
    }

    private UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setAddress(user.getAddress());
        userDto.setBalance(user.getBalance());

        return userDto;
    }

    private User mapToEntity(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setAddress(userDto.getAddress());
        user.setBalance(userDto.getBalance());

        return user;
    }


}
