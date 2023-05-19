package com.example.synctask.Repositories;

import com.example.synctask.Models.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Groups, Long> {

    List<Groups> findAllByOwner(Long id);
    List<Groups> findAllByMembersOrOwner(Long memberId, Long ownerId);
}
