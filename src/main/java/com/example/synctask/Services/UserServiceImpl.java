package com.example.synctask.Services;

import com.example.synctask.Models.UserT;
import com.example.synctask.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserT> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserT getUser(Long id) {
        return userRepository.findById(id).get();
    }
}
