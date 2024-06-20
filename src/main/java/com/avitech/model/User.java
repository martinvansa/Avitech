package com.avitech.model;

public class User {

    private final String userGuid;
    private final String userName;

    public User(String userGuid, String userName) {
        this.userGuid = userGuid;
        this.userName = userName;
    }

    public String getUserGuid() {
        return userGuid;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "User{" +
                ", userGuid='" + userGuid + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
