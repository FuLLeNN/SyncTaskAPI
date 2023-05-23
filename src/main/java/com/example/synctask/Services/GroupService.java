package com.example.synctask.Services;

import com.example.synctask.DTOs.GroupByUserDto;
import com.example.synctask.Models.Groups;

import java.util.List;

public interface GroupService {
    List<GroupByUserDto> findAll();
    Groups saveGroup(Groups group);
    GroupByUserDto updateGroup(Groups group);
    void deleteGroupById(Long id);
    List<GroupByUserDto> findAllByOwner(Long id);
    List<GroupByUserDto> findByMembersIdOrOwnerId(Long userId);
    Groups findById(Long id);
    Groups findByTaskId(Long id);
}
