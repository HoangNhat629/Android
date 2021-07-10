package com.example.android1_module3_tmdb.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.android1_module3_tmdb.R;
import com.example.android1_module3_tmdb.api.APIService;
import com.example.android1_module3_tmdb.api.RetrofitConfiguration;
import com.example.android1_module3_tmdb.models.DeleteSessionIdRequest;
import com.example.android1_module3_tmdb.models.DeleteSessionIdResponse;
import com.example.android1_module3_tmdb.models.GetCreateRequestTokenResponse;
import com.example.android1_module3_tmdb.models.PostCreateSessionRequest;
import com.example.android1_module3_tmdb.models.PostCreateSessionResponse;
import com.example.android1_module3_tmdb.models.PostCreateSessionWithLoginRequest;
import com.example.android1_module3_tmdb.models.PostCreateSessionWithLoginResponse;
import com.example.android1_module3_tmdb.utils.Utils;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_sign_in)
    TextView tvSignIn;
    @BindView(R.id.ll_sign_in)
    LinearLayout llSignIn;
    @BindView(R.id.ll_loading)
    LinearLayout llLoading;
    @BindView(R.id.ll_account)
    LinearLayout llAccount;

    private APIService service;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, view);

       if (Utils.getSessionId(getContext()) == null) {
           llAccount.setVisibility(View.INVISIBLE);
           llSignIn.setVisibility(View.VISIBLE);
       } else {
           llAccount.setVisibility(View.VISIBLE);
           llSignIn.setVisibility(View.INVISIBLE);
       }

        service = RetrofitConfiguration.getInstance().create(APIService.class);

        return view;
    }

    @OnClick({R.id.tv_sign_in, R.id.tv_sign_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sign_in:
                createRequestToken();
                break;
            case R.id.tv_sign_out:
                signOut();
                break;
        }
    }

    private void signOut() {
        llLoading.setVisibility(View.VISIBLE);
        DeleteSessionIdRequest body = new DeleteSessionIdRequest();
        body.setSession_id(Utils.getSessionId(getContext()));
        Call<DeleteSessionIdResponse> call = service.deleteSessionId(body);
        call.enqueue(new Callback<DeleteSessionIdResponse>() {
            @Override
            public void onResponse(Call<DeleteSessionIdResponse> call, Response<DeleteSessionIdResponse> response) {
                llLoading.setVisibility(View.INVISIBLE);
                if (response.code() == 200) {
                    llAccount.setVisibility(View.INVISIBLE);
                    llSignIn.setVisibility(View.VISIBLE);

                    Utils.saveSessionId(null, getContext());
                } else {
                    llLoading.setVisibility(View.INVISIBLE);
                    Utils.showErrorFromServer(response, getContext());
                }
            }

            @Override
            public void onFailure(Call<DeleteSessionIdResponse> call, Throwable t) {
                llLoading.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createRequestToken() {
        llLoading.setVisibility(View.VISIBLE);
        Call<GetCreateRequestTokenResponse> call = service.getCreateRequestToken();
        call.enqueue(new Callback<GetCreateRequestTokenResponse>() {
            @Override
            public void onResponse(Call<GetCreateRequestTokenResponse> call, Response<GetCreateRequestTokenResponse> response) {
                if (response.code() == 200) {
                    createSessionWithLogin(response.body().getRequest_token());
                } else {
                    llLoading.setVisibility(View.INVISIBLE);
                    Utils.showErrorFromServer(response, getContext());
                }
            }

            @Override
            public void onFailure(Call<GetCreateRequestTokenResponse> call, Throwable t) {
                llLoading.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createSessionWithLogin(String token) {
        PostCreateSessionWithLoginRequest body = new PostCreateSessionWithLoginRequest();
        body.setUsername(etUsername.getText().toString());
        body.setPassword(etPassword.getText().toString());
        body.setRequest_token(token);

        Call<PostCreateSessionWithLoginResponse> call = service.postCreateSessionWithLogin(body);
        call.enqueue(new Callback<PostCreateSessionWithLoginResponse>() {
            @Override
            public void onResponse(Call<PostCreateSessionWithLoginResponse> call, Response<PostCreateSessionWithLoginResponse> response) {
                if (response.code() == 200) {
                    createSession(response.body().getRequest_token());
                } else {
                    llLoading.setVisibility(View.INVISIBLE);
                    Utils.showErrorFromServer(response, getContext());
                }
            }

            @Override
            public void onFailure(Call<PostCreateSessionWithLoginResponse> call, Throwable t) {
                llLoading.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
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
                    llLoading.setVisibility(View.INVISIBLE);
                    llSignIn.setVisibility(View.INVISIBLE);
                    llAccount.setVisibility(View.VISIBLE);

                    Utils.saveSessionId(response.body().getSession_id(), getContext());
                } else {
                    llLoading.setVisibility(View.INVISIBLE);
                    Utils.showErrorFromServer(response, getContext());
                }
            }

            @Override
            public void onFailure(Call<PostCreateSessionResponse> call, Throwable t) {
                llLoading.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
