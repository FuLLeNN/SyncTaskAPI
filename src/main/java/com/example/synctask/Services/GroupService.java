package com.example.synctask.Services;

import com.example.synctask.Models.Groups;

import java.util.List;

public interface GroupService {
    List<Groups> findAll();
    Groups saveGroup(Groups group);
    Groups updateGroup(Groups group);
    void deleteGroupById(Long id);
    List<Groups> findAllByOwner(Long id);
    List<Groups> findAllByMemberOrOwner(Long memberId, Long ownerId);
    Groups findById(Long id);
}
