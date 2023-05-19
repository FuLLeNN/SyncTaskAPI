package com.example.synctask.Services;

import com.example.synctask.Models.GroupMember;
import com.example.synctask.Models.Groups;

import java.util.List;

public interface GroupMemberService {
    List<GroupMember> findAll();
    GroupMember saveGroupMember(GroupMember groupMember);
    GroupMember updateGroupMember(GroupMember groupMember);
    void deleteGroupMemberById(Long id);
    void acceptGroupRequest(Long groupId, Long userId);
    void denyGroupRequest(Long groupId, Long userId);
    List<GroupMember> getPendingGroupRequestByUser(Long userId);
    boolean existsByGroupIdAndUserId(Long groupId, Long userId);
}
