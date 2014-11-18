package com.msevgi.facebookapiex;

/**
 * Created by mustafasevgi on 18/11/14.
 */
public class Model {
    public Model(String user_id) {
        this.user_id = user_id;
    }

    private String user_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
