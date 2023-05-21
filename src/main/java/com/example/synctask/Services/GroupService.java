package com.example.synctask.Services;

import com.example.synctask.DTOs.GroupByUserDto;
import com.example.synctask.Models.Groups;

import java.util.List;

public interface GroupService {
    List<Groups> findAll();
    Groups saveGroup(Groups group);
    Groups updateGroup(Groups group);
    void deleteGroupById(Long id);
    List<Groups> findAllByOwner(Long id);
    List<GroupByUserDto> findByMembersIdOrOwnerId(Long userId);
    Groups findById(Long id);
}
