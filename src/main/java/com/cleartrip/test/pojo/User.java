package com.cleartrip.test.pojo;

import java.io.Serializable;

public class User implements Serializable {

    private String emailId;
    private String uname;



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


