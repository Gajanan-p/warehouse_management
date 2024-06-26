package com.ssinfomate.warehousemanagement.webservices.login;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("login_Name")
    @Expose
    private String loginName;
    @SerializedName("User_Pwd")
    @Expose
    private String userPwd;
    @SerializedName("cobr_id")
    @Expose
    private String cobrId;
    @SerializedName("UserType_id")
    @Expose
    private String userTypeId;
    @SerializedName("led_id")
    @Expose
    private String ledId;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("WindowStatus")
    @Expose
    private String windowStatus;
    @SerializedName("IsLogin")
    @Expose
    private Integer isLogin;
    @SerializedName("MaxReducedPerc")
    @Expose
    private String maxReducedPerc;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserModel() {
    }

    /**
     *
     * @param userTypeId
     * @param msg
     * @param isLogin
     * @param maxReducedPerc
     * @param loginName
     * @param ledId
     * @param windowStatus
     * @param userPwd
     * @param userId
     * @param cobrId
     */
    public UserModel(Integer userId, String loginName, String userPwd, String cobrId, String userTypeId, String ledId, String msg, String windowStatus, Integer isLogin, String maxReducedPerc) {
        super();
        this.userId = userId;
        this.loginName = loginName;
        this.userPwd = userPwd;
        this.cobrId = cobrId;
        this.userTypeId = userTypeId;
        this.ledId = ledId;
        this.msg = msg;
        this.windowStatus = windowStatus;
        this.isLogin = isLogin;
        this.maxReducedPerc = maxReducedPerc;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getCobrId() {
        return cobrId;
    }

    public void setCobrId(String cobrId) {
        this.cobrId = cobrId;
    }

    public String getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(String userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getLedId() {
        return ledId;
    }

    public void setLedId(String ledId) {
        this.ledId = ledId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getWindowStatus() {
        return windowStatus;
    }

    public void setWindowStatus(String windowStatus) {
        this.windowStatus = windowStatus;
    }

    public Integer getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Integer isLogin) {
        this.isLogin = isLogin;
    }

    public String getMaxReducedPerc() {
        return maxReducedPerc;
    }

    public void setMaxReducedPerc(String maxReducedPerc) {
        this.maxReducedPerc = maxReducedPerc;
    }

}