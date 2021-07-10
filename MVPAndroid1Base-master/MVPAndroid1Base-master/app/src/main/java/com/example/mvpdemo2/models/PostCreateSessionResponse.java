package com.example.mvpdemo2.models;

public class PostCreateSessionResponse {

    /**
     * success : true
     * session_id : ddf3f5101e351dfcf3574b7a3966688132568ba1
     */

    private boolean success;
    private String session_id;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}
