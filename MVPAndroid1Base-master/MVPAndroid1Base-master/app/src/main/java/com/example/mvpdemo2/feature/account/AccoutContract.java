package com.example.mvpdemo2.feature.account;

import retrofit2.Response;

public interface AccoutContract {
    interface View{
        void showAccountSection();
        void showLoginSection();
        void showLoadingIndicator();
        void hideLoadingIndicator();
        void showErrorFromSever(Response response);
        void showErrorWhenFailure(String error);
    }
    interface  Presenter{

        void getSessionID();
        void SignIn(String usename ,String password);
    }
}
