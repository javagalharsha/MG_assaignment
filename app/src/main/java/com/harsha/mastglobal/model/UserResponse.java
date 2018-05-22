package com.harsha.mastglobal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by harsha on 5/21/18.
 */

public class UserResponse {

    @SerializedName("login")
    @Expose
    private String login;

    public String getLogin() {
        return login;
    }
}
