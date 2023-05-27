package com.example.synctask.Repositories;

import com.example.synctask.Models.UserT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserT, Long> {
}
