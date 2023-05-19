package com.example.synctask.Services;

import com.example.synctask.Models.UserT;

import java.util.List;

public interface UserService {
    List<UserT> getAllUsers();
    UserT getUser(Long id);
}
