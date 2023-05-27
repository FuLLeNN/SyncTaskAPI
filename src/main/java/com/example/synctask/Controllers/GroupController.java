package com.example.synctask.Controllers;

import com.example.synctask.DTOs.CreateGroupDto;
import com.example.synctask.DTOs.CreateTask;
import com.example.synctask.DTOs.GroupByUserDto;
import com.example.synctask.Models.GroupMember;
import com.example.synctask.Models.Groups;
import com.example.synctask.Models.Task;
import com.example.synctask.Models.UserT;
import com.example.synctask.Services.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Tag(description = "",name = "Groups Resource")
@RestController
@RequestMapping("api/groups")
public class GroupController {
    private final GroupMemberServiceImpl groupMemberService;
    private final GroupServiceImpl groupService;
    private final UserServiceImpl userService;
    private final TaskServiceImpl taskService;

    public GroupController(GroupMemberServiceImpl groupMemberService, GroupServiceImpl groupService, UserServiceImpl userService, TaskServiceImpl taskService) {
        this.groupMemberService = groupMemberService;
        this.groupService = groupService;
        this.userService = userService;
        this.taskService = taskService;
    }

    @Operation(summary  = "Get a Group by id", description = "Returns a Group by the given id")
    @GetMapping("/{id}")
    public GroupByUserDto getGroupById(@PathVariable("id") Long id){
        return groupService.mapToGroupByUserDto(groupService.findById(id));
    }

    @Operation(summary  = "Get all Groups", description = "Returns all Groups")
    @GetMapping("/")
    public List<GroupByUserDto> getAllGroups(){
        return groupService.findAll();
    }

    @Operation(summary = "Create Group")
    @PostMapping("/")
    public GroupByUserDto createGroup(@RequestBody CreateGroupDto group){
        Groups groups = new Groups();
        groups.setOwner(group.getOwner());
        groups.setGroupName(group.getGroupName());
        Groups g = groupService.saveGroup(groups);
        GroupMember gp = new GroupMember();
        gp.setGroup(g);
        gp.setAccepted(true);
        gp.setUser(userService.getUser(group.getOwner()));
        groupMemberService.saveGroupMember(gp);
        return groupService.mapToGroupByUserDto(g);
    }

    @Operation(summary = "Update Group by id", description = "Make changes on a Group by the id")
    @PutMapping("/{id}")
    public GroupByUserDto updateGroup(@PathVariable("id") Long id, @RequestBody CreateGroupDto group ){
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
    public List<GroupByUserDto> getAllByOwner(@PathVariable("ownerId") Long ownerId){
        return groupService.findAllByOwner(ownerId);
    }

    @Operation(summary = "Find all groups by user")
    @GetMapping("/user/{userId}")
    public List<GroupByUserDto> getAllByUser(@PathVariable("userId") Long userId){
        return groupService.findByMembersIdOrOwnerId(userId);
    }

    @Operation(summary = "Get all pending group invites by user")
    @GetMapping("/user/{userId}/invites")
    public List<GroupMember> getAllPendingInvites(@PathVariable("userId") Long userId){
        return groupMemberService.getPendingGroupRequestByUser(userId);
    }

    @Operation(summary = "Create task for group")
    @PostMapping("/{groupId}/{userId}/task/")
    public Task createTaskForGroup(@PathVariable("groupId") Long groupId, @PathVariable("userId") Long userId, @RequestBody CreateTask task){
        if(groupMemberService.existsByGroupIdAndUserId(groupId, userId)){
            Task t = new Task();
            t.setName(task.getName());
            t.setDescription(task.getDescription());
            t.setGroup(true);
            t.setUserId(task.getUserId());
            t.setStartDate(task.getStartDate());
            t.setEndDate(task.getEndDate());
            Groups group = groupService.findById(groupId);
            group.getTasks().add(t);
            userService.getUser(userId).getTasks().add(t);
            return taskService.saveTask(t);
        }
        return null;
    }

    @Operation(summary = "Remove user from group")
    @PostMapping("/{groupId}/users/{userId}/remove")
    public ResponseEntity<String> removeUserFromGroup(@PathVariable("groupId") Long groupId, @PathVariable("userId") Long userId){
        groupMemberService.removeGroupMemberByUserIdAndGroupId(userId,groupId);
        return ResponseEntity.ok("User removed successful");
    }

    @Operation(summary =  "Admin remove task from group")
    @PostMapping("/{groupId}/tasks/{taskId}/remove")
    public ResponseEntity removeTaskFromGroupId(@PathVariable("groupId") Long groupId, @PathVariable("taskId") Long taskId){
        Task task = taskService.findTaskById(taskId).get();
        Groups group = groupService.findById(groupId);
        for (Task t:group.getTasks()) {
            if(t.getId() == task.getId()){
                taskService.deleteTaskById(taskId);
                return ResponseEntity.ok("Task removed");
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Owner of task remove task")
    @PostMapping("/{groupId}/tasks/{userId}/{taskId}/remove")
    public ResponseEntity removeTaskFromGroupByOwner(@PathVariable("groupId") Long groupId, @PathVariable("userId") Long userId, @PathVariable("taskId") Long taskId){
        Task task = taskService.findTaskById(taskId).get();
        if(task.getUserId() == userId){
            taskService.deleteTaskById(taskId);
            return ResponseEntity.ok("Task deleted");
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
