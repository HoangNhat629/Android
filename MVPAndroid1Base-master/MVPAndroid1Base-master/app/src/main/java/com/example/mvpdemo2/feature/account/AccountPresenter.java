package com.example.mvpdemo2.feature.account;

import android.app.Fragment;
import android.app.MediaRouteButton;
import android.view.View;
import android.widget.Toast;

import com.example.mvpdemo2.api.APIService;
import com.example.mvpdemo2.api.RetrofitConfiguration;
import com.example.mvpdemo2.models.AccountSharePref;
import com.example.mvpdemo2.models.GetCreateRequestTokenResponse;
import com.example.mvpdemo2.models.PostCreateSessionRequest;
import com.example.mvpdemo2.models.PostCreateSessionResponse;
import com.example.mvpdemo2.models.PostCreateSessionWithLoginRequest;
import com.example.mvpdemo2.models.PostCreateSessionWithLoginResponse;

import butterknife.internal.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountPresenter implements AccoutContract.Presenter {
    AccoutContract.View view;
    APIService service;

    AccountSharePref accountSharePref;


    public AccountPresenter(AccoutContract.View view, AccountSharePref accountSharePref) {
        this.view = view;
        this.accountSharePref = accountSharePref;
        service = RetrofitConfiguration.getInstance().create(APIService.class);
    }


    @Override
    public void getSessionID() {
        if (accountSharePref.getSessionId() == null) {
            view.showLoginSection();
        } else {
            view.showAccountSection();
        }
    }

    @Override
    public void SignIn(String usename, String password) {
        createRequestToken(usename, password);
    }

    private void createRequestToken(String usename, String password) {

        view.showLoadingIndicator();


        Call<GetCreateRequestTokenResponse> call = service.getCreateRequestToken();
        call.enqueue(new Callback<GetCreateRequestTokenResponse>() {
            @Override
            public void onResponse(Call<GetCreateRequestTokenResponse> call, Response<GetCreateRequestTokenResponse> response) {
                if (response.code() == 200) {
                    createSessionWithLogin(response.body().getRequest_token(), usename, password);
                } else {
                    view.hideLoadingIndicator();
                    view.showErrorFromSever(response);
                }
            }

            @Override
            public void onFailure(Call<GetCreateRequestTokenResponse> call, Throwable t) {
                view.hideLoadingIndicator();
                view.showErrorWhenFailure(t.toString());
            }
        });
    }


    private void createSessionWithLogin(String token, String usename, String password) {
        PostCreateSessionWithLoginRequest body = new PostCreateSessionWithLoginRequest();

        body.setUsername(usename);

        body.setPassword(password);
        body.setRequest_token(token);


        Call<PostCreateSessionWithLoginResponse> call = service.postCreateSessionWithLogin(body);
        call.enqueue(new Callback<PostCreateSessionWithLoginResponse>() {
            @Override
            public void onResponse(Call<PostCreateSessionWithLoginResponse> call, Response<PostCreateSessionWithLoginResponse> response) {
                if (response.code() == 200) {
                    createSession(response.body().getRequest_token());
                } else {

                    view.hideLoadingIndicator();
                    view.showErrorFromSever(response);
                }
            }

            @Override
            public void onFailure(Call<PostCreateSessionWithLoginResponse> call, Throwable t) {
                view.hideLoadingIndicator();
                view.showErrorWhenFailure(t.toString());
            }
        });
    }

    private void createSession(String token) {
        PostCreateSessionRequest body = new PostCreateSessionRequest();
        body.setRequest_token(token);

        Call<PostCreateSessionResponse> call = service.postCreateSession(body);
        call.enqueue(new Callback<PostCreateSessionResponse>() {
            @Override
            public void onResponse(Call<PostCreateSessionResponse> call, Response<PostCreateSessionResponse> response) {
                if (response.code() == 200) {
                    view.hideLoadingIndicator();
                    view.showAccountSection();
                    accountSharePref.saveSessionId(response.body().getSession_id());
                } else {
                    view.hideLoadingIndicator();
                    view.showErrorFromSever(response);
                }
            }

            @Override
            public void onFailure(Call<PostCreateSessionResponse> call, Throwable t) {
                view.hideLoadingIndicator();
                view.showErrorWhenFailure(t.toString());
            }
        });
    }

}
