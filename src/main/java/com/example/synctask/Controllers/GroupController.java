package com.example.synctask.Controllers;

import com.example.synctask.DTOs.CreateGroupDto;
import com.example.synctask.Models.GroupMember;
import com.example.synctask.Models.Groups;
import com.example.synctask.Services.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Tag(description = "",name = "Groups Resource")
@RestController
@RequestMapping("api/groups")
public class GroupController {
    private final GroupMemberServiceImpl groupMemberService;
    private final GroupServiceImpl groupService;
    private final UserServiceImpl userService;

    public GroupController(GroupMemberServiceImpl groupMemberService, GroupServiceImpl groupService, UserServiceImpl userService) {
        this.groupMemberService = groupMemberService;
        this.groupService = groupService;
        this.userService = userService;
    }

    @Operation(summary  = "Get a Group by id", description = "Returns a Group by the given id")
    @GetMapping("/{id}")
    public Groups getGroupById(@PathVariable("id") Long id){
        return groupService.findById(id);
    }

    @Operation(summary  = "Get all Groups", description = "Returns all Groups")
    @GetMapping("/")
    public List<Groups> getAllGroups(){
        return groupService.findAll();
    }

    @Operation(summary = "Create Group")
    @PostMapping("/")
    public Groups createGroup(@RequestBody CreateGroupDto group){
        Groups groups = new Groups();
        groups.setOwner(group.getOwner());
        groups.setGroupName(group.getGroupName());
        return groupService.saveGroup(groups);
    }

    @Operation(summary = "Update Group by id", description = "Make changes on a Group by the id")
    @PutMapping("/{id}")
    public Groups updateGroup(@PathVariable("id") Long id, @RequestBody CreateGroupDto group ){
        Groups groups = groupService.findById(id);
        groups.setGroupName(group.getGroupName());
        groups.setOwner(group.getOwner());
        return groupService.updateGroup(groups);
    }

    @Operation(summary = "Delete Group by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGroupById(@PathVariable("id") Long id){
        Groups group = groupService.findById(id);
        groupService.deleteGroupById(id);
        return ResponseEntity.ok("Group "+ group.getGroupName()+" deleted");
    }

    @Operation(summary = "Accept Group Request")
    @PostMapping("/{groupId}/members/{userId}/accept")
    public ResponseEntity<String> acceptGroupRequest(@PathVariable("groupId") Long groupId, @PathVariable("userId") Long userId){
        Groups group = groupService.findById(groupId);
        groupMemberService.acceptGroupRequest(groupId,userId);
        return ResponseEntity.ok("User "+userId+" accepted invite to group "+group.getGroupName());
    }

    @Operation(summary = "Deny Group Request")
    @PostMapping("/{groupId}/members/{userId}/deny")
    public ResponseEntity<String> denyGroupRequest(@PathVariable("groupId") Long groupId, @PathVariable("userId") Long userId){
        Groups group = groupService.findById(groupId);
        groupMemberService.denyGroupRequest(groupId,userId);
        return ResponseEntity.ok("User "+userId+" denied invite to group "+group.getGroupName());
    }

    @Operation(summary = "Leave Group")
    @PostMapping("/{groupId}/members/{userId}/leave")
    public ResponseEntity<String> leaveGroup(@PathVariable("groupId") Long groupId, @PathVariable("userId") Long userId){
        Groups group = groupService.findById(groupId);
        groupMemberService.denyGroupRequest(groupId,userId);
        return ResponseEntity.ok("User "+userId+" left invite to group "+group.getGroupName());
    }

    @Operation(summary = "Invite to group")
    @PostMapping("/{groupId}/members/invite/{userId}")
    public ResponseEntity<String> inviteToGroup(@PathVariable("groupId") Long groupId, @PathVariable("userId") Long userId){
        if(!groupMemberService.existsByGroupIdAndUserId(groupId, userId)){
            GroupMember groupMember = new GroupMember();
            groupMember.setUser(userService.getUser(userId));
            groupMember.setGroup(groupService.findById(groupId));
            groupMember.setAccepted(false);
            groupMemberService.saveGroupMember(groupMember);
            return ResponseEntity.ok("User "+userService.getUser(userId).getFullname()+"invited to "+groupService.findById(groupId).getGroupName());
        }
        return ResponseEntity.ok("User already invited");
    }

    @Operation(summary = "Find groups by owner")
    @GetMapping("/owner/{ownerId}")
    public List<Groups> getAllByOwner(@PathVariable("ownerId") Long ownerId){
        return groupService.findAllByOwner(ownerId);
    }

    @Operation(summary = "Find all groups by user")
    @GetMapping("/user/{userId}")
    public List<Groups> getAllByUser(@PathVariable("userId") Long userId){
        return groupService.findAllByMemberOrOwner(userId, userId);
    }

    @Operation(summary = "Get all pending group invites by user")
    @GetMapping("/user/{userId}/invites")
    public List<Groups> getAllPendingInvites(@PathVariable("userId") Long userId){
        List<GroupMember> groupMembers = groupMemberService.getPendingGroupRequestByUser(userId);
        List<Groups> groups = Arrays.asList();
        for (GroupMember gp :groupMembers) {
            groups.add(groupService.findById(gp.getGroup().getId()));
        }
        return groups;
    }

}