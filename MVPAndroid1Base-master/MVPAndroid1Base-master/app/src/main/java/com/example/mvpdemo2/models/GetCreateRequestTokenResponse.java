package com.example.mvpdemo2.models;

public class GetCreateRequestTokenResponse {

    /**
     * success : true
     * expires_at : 2020-04-27 15:08:20 UTC
     * request_token : 5ebe94b512765dff2dcf6567b40f516b67111c44
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
