package com.example.synctask.Models;

import com.example.synctask.DTOs.GetUserDto;

public class Friends {
    public int Id;

    public int UserId;
    public int FriendId;
    public GetUserDto Friend;

    public boolean IsAccepted;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
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

    public GetUserDto getFriend() {
        return Friend;
    }

    public void setFriend(GetUserDto friend) {
        Friend = friend;
    }

    public boolean isAccepted() {
        return IsAccepted;
    }

    public void setAccepted(boolean accepted) {
        IsAccepted = accepted;
    }
}
