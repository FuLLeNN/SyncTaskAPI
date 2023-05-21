package com.example.synctask.Services;

import com.example.synctask.Models.GroupMember;
import com.example.synctask.Repositories.GroupMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupMemberServiceImpl implements GroupMemberService {
    private final GroupMemberRepository groupMemberRepository;

    public GroupMemberServiceImpl(GroupMemberRepository groupMemberRepository) {
        this.groupMemberRepository = groupMemberRepository;
    }

    @Override
    public List<GroupMember> findAll() {
        return groupMemberRepository.findAll();
    }

    @Override
    public GroupMember saveGroupMember(GroupMember groupMember) {
        return groupMemberRepository.save(groupMember);
    }

    @Override
    public GroupMember updateGroupMember(GroupMember groupMember) {
        return groupMemberRepository.save(groupMember);
    }

    @Override
    public void deleteGroupMemberById(Long id) {
        groupMemberRepository.deleteById(id);
    }

    @Override
    public void acceptGroupRequest(Long groupId, Long userId) {
        GroupMember groupMember = groupMemberRepository.findByGroupIdAndUserId(groupId, userId);
        groupMember.setAccepted(true);
        groupMemberRepository.save(groupMember);
    }

    @Override
    public void denyGroupRequest(Long groupId, Long userId) {
        GroupMember groupMember = groupMemberRepository.findByGroupIdAndUserId(groupId, userId);
        deleteGroupMemberById(groupMember.getId());
    }

    @Override
    public List<GroupMember> getPendingGroupRequestByUser(Long userId) {
        return groupMemberRepository.findPendingInvitesByUserId(userId);
    }

    @Override
    public boolean existsByGroupIdAndUserId(Long groupId, Long userId) {
        return groupMemberRepository.existsByGroupIdAndUserId(groupId, userId);
    }

    @Override
    public GroupMember removeGroupMemberByUserIdAndGroupId(Long userId, Long groupId) {
        return groupMemberRepository.removeGroupMemberByUserIdAndGroupId(userId, groupId);
    }
}
