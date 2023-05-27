package com.example.synctask.Models;

import com.example.synctask.DTOs.GetUserDto;
import jakarta.persistence.*;

@Entity
public class Friends {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long Id;

    public int UserId;
    public int FriendId;

    public boolean IsAccepted;

    public Friends(){

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getFriendId() {
        return FriendId;
    }

    public void setFriendId(int friendId) {
        FriendId = friendId;
    }


    public boolean isAccepted() {
        return IsAccepted;
    }

    public void setAccepted(boolean accepted) {
        IsAccepted = accepted;
    }
}
