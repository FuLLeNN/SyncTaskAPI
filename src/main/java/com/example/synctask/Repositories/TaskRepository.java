package com.example.synctask.Repositories;

import com.example.synctask.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface TaskRepository  extends JpaRepository<Task, Long> {
    List<Task> findByStartDate(String date);
    List<Task> findByEndDate(String date);
    List<Task> findByName(String name);
    List<Task> findByDescription(String description);
    List<Task> findAllByUserId(Long userId);
    List<Task> findByGroupId(Long groupId);
    void deleteByGroupId(Long groupId);
}
