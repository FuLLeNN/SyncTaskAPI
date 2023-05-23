package com.example.synctask.Repositories;

import com.example.synctask.Models.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Groups, Long> {

    List<Groups> findAllByOwner(Long id);

    @Query("SELECT DISTINCT g FROM Groups g INNER JOIN g.members gm WHERE g.owner = :userId OR gm.user.id = :userId")
    List<Groups> findByMemberIdOrOwnerId(Long userId);

    Groups findByTasksId(Long taskId);
}
