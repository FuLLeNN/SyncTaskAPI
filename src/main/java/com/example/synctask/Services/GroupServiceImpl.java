package com.example.synctask.Services;

import com.example.synctask.DTOs.GetUserDto;
import com.example.synctask.DTOs.GroupByUserDto;
import com.example.synctask.DTOs.GroupMemberDto;
import com.example.synctask.Models.GroupMember;
import com.example.synctask.Models.Groups;
import com.example.synctask.Models.Task;
import com.example.synctask.Models.UserT;
import com.example.synctask.Repositories.GroupMemberRepository;
import com.example.synctask.Repositories.GroupRepository;
import com.example.synctask.Repositories.TaskRepository;
import com.example.synctask.Repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService{

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final TaskRepository taskRepository;
    private ObjectMapper objectMapper;

    public GroupServiceImpl(GroupRepository groupRepository, UserRepository userRepository, GroupMemberRepository groupMemberRepository, TaskRepository taskRepository, ObjectMapper objectMapper) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.taskRepository = taskRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<GroupByUserDto> findAll() {
        return mapToGroupByUserDtoList(groupRepository.findAll());
    }

    @Override
    public Groups saveGroup(Groups group) {
        return groupRepository.save(group);
    }

    @Override
    public GroupByUserDto updateGroup(Groups group) {
        return mapToGroupByUserDto(groupRepository.save(group));
    }

    @Override
    @Transactional
    public void deleteGroupById(Long id) {
        groupRepository.deleteById(id);
    }

    @Override
    public List<GroupByUserDto> findAllByOwner(Long id) {
        return mapToGroupByUserDtoList(groupRepository.findAllByOwner(id));
    }

    @Override
    public List<GroupByUserDto> findByMembersIdOrOwnerId(Long userId){
        return mapToGroupByUserDtoList(groupRepository.findByMemberIdOrOwnerId(userId));
    }

    @Override
    public Groups findById(Long id) {
        return groupRepository.findById(id).get();
    }

    @Override
    public Groups findByTaskId(Long id) {
        return groupRepository.findByTasksId(id);
    }


    public List<GroupByUserDto> mapToGroupByUserDtoList(List<Groups> groups) {
        List<GroupByUserDto> groupByUserDtos = new ArrayList<>();

        for (Groups group : groups) {
            GroupByUserDto groupByUserDto = new GroupByUserDto();
            groupByUserDto.setId(group.getId());
            groupByUserDto.setOwner(group.getOwner());
            groupByUserDto.setGroupName(group.getGroupName());
            groupByUserDto.setTasks(group.getTasks());

            List<GroupMemberDto> groupMemberDtos = new ArrayList<>();
            for (GroupMember member : group.getMembers()) {
                GroupMemberDto groupMemberDto = new GroupMemberDto();
                groupMemberDto.setId(member.getId());
                groupMemberDto.setAccepted(member.isAccepted());

                GetUserDto userDto = new GetUserDto();
                userDto.setId(member.getUser().getId());
                userDto.setFullName(member.getUser().getFullname());
                userDto.setUsername(member.getUser().getUsername());
                userDto.setEmail(member.getUser().getEmail());

                groupMemberDto.setUser(userDto);

                groupMemberDtos.add(groupMemberDto);
            }

            groupByUserDto.setMembers(groupMemberDtos);
            groupByUserDtos.add(groupByUserDto);
        }

        return groupByUserDtos;
    }

    public GroupByUserDto mapToGroupByUserDto(Groups group) {
        GroupByUserDto groupByUserDto = new GroupByUserDto();
        groupByUserDto.setId(group.getId());
        groupByUserDto.setOwner(group.getOwner());
        groupByUserDto.setGroupName(group.getGroupName());
        groupByUserDto.setTasks(group.getTasks());

        List<GroupMemberDto> groupMemberDtos = new ArrayList<>();
        for (GroupMember member : group.getMembers()) {
            GroupMemberDto groupMemberDto = new GroupMemberDto();
            groupMemberDto.setId(member.getId());
            groupMemberDto.setAccepted(member.isAccepted());

            GetUserDto userDto = new GetUserDto();
            userDto.setId(member.getUser().getId());
            userDto.setFullName(member.getUser().getFullname());
            userDto.setUsername(member.getUser().getUsername());
            userDto.setEmail(member.getUser().getEmail());

            groupMemberDto.setUser(userDto);

            groupMemberDtos.add(groupMemberDto);
        }

        groupByUserDto.setMembers(groupMemberDtos);

        return groupByUserDto;
    }

    public String convertTasksListToJson(List<Task> tasks) throws JsonProcessingException {
        return objectMapper.writeValueAsString(tasks);
    }
}
