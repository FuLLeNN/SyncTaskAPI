package com.example.synctask.Repositories;

import com.example.synctask.Models.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    GroupMember findByGroupIdAndUserId(Long groupId, Long userId);

    List<GroupMember> findAllByUserId(Long userId);

    List<GroupMember> findAllByGroupId(Long groupId);

    boolean existsByGroupIdAndUserId(Long groupId, Long userId);

    GroupMember removeGroupMemberByUserIdAndGroupId(Long userId, Long groupId);

    @Query("SELECT gm FROM GroupMember gm WHERE gm.user.id = :userId AND gm.accepted = false")
    List<GroupMember> findPendingInvitesByUserId(Long userId);

    @Modifying
    @Query("DELETE FROM GroupMember gm WHERE gm.group.id = :groupId")
    void deleteGroupMembersByGroupIdRepo(@Param("groupId") Long groupId);
}
