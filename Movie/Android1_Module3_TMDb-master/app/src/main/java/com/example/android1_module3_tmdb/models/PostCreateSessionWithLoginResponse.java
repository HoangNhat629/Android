package com.example.android1_module3_tmdb.models;

public class PostCreateSessionWithLoginResponse {
    /**
     * success : true
     * expires_at : 2020-04-27 14:18:15 UTC
     * request_token : 30c78fb4031c939492790dead924494f8999f26d
     */

    private boolean success;
    private String expires_at;
    private String request_token;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(String expires_at) {
        this.expires_at = expires_at;
    }

    public String getRequest_token() {
        return request_token;
    }

    public void setRequest_token(String request_token) {
        this.request_token = request_token;
    }
}
