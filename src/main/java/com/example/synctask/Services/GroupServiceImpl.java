package com.example.synctask.Services;

import com.example.synctask.Models.Groups;
import com.example.synctask.Repositories.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService{

    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Groups> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public Groups saveGroup(Groups group) {
        return groupRepository.save(group);
    }

    @Override
    public Groups updateGroup(Groups group) {
        return groupRepository.save(group);
    }

    @Override
    public void deleteGroupById(Long id) {
        groupRepository.deleteById(id);
    }

    @Override
    public List<Groups> findAllByOwner(Long id) {
        return groupRepository.findAllByOwner(id);
    }

    @Override
    public List<Groups> findByMembersIdOrOwnerId(Long userId){
        return groupRepository.findByMemberIdOrOwnerId(userId);
    }

    @Override
    public Groups findById(Long id) {
        return groupRepository.findById(id).get();
    }
}
