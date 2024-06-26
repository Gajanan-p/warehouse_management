package com.ssinfomate.warehousemanagement.webservices.login;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("login_Name")
    private String login_Name;

    @SerializedName("User_Pwd")
    private String User_Pwd;

    public User(String login_Name, String user_Pwd) {
        this.login_Name = login_Name;
        User_Pwd = user_Pwd;
    }

    public String getLogin_Name() {
        return login_Name;
    }

    public String getUser_Pwd() {
        return User_Pwd;
    }

    public void setLogin_Name(String login_Name) {
        this.login_Name = login_Name;
    }

    public void setUser_Pwd(String user_Pwd) {
        User_Pwd = user_Pwd;
    }
}
