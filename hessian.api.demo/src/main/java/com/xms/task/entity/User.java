package com.xms.task.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/8 0008.
 */
public class User implements Serializable {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
