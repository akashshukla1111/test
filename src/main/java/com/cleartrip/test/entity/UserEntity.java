package com.cleartrip.test.entity;

import java.util.List;

public class UserEntity {

    private String emailId;
    private String uname;
    private List<String> bid;


    public List<String> getBid() {
        return bid;
    }

    public void setBid(List<String> bid) {
        this.bid = bid;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }


}
