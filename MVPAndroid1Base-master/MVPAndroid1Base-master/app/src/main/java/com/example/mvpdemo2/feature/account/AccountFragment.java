package com.example.mvpdemo2.feature.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mvpdemo2.BaseFragment;
import com.example.mvpdemo2.R;
import com.example.mvpdemo2.api.APIService;
import com.example.mvpdemo2.models.AccountSharePref;
import com.example.mvpdemo2.models.GetCreateRequestTokenResponse;
import com.example.mvpdemo2.models.PostCreateSessionWithLoginRequest;
import com.example.mvpdemo2.models.PostCreateSessionWithLoginResponse;


import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.internal.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends BaseFragment implements AccoutContract.View {
    AccoutContract.View view;
    AccountPresenter presenter;
    APIService service;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.ll_sign_in)
    LinearLayout llSignIn;
    @BindView(R.id.tv_sign_out)
    TextView tvSignOut;
    @BindView(R.id.ll_account)
    LinearLayout llAccount;
    @BindView(R.id.ll_loading)
    LinearLayout llLoading;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_account;
    }

    @Override
    protected void onInit() {
        presenter = new AccountPresenter(this, new AccountSharePref(getContext()));
        presenter.getSessionID();
    }

    @OnClick({R.id.tv_sign_in, R.id.tv_sign_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sign_in:
                presenter.SignIn(
                        etUsername.getText().toString(),
                        etPassword.getText().toString());

                break;
            case R.id.tv_sign_out:
                break;
        }
    }

    @Override
    public void showAccountSection() {
        llAccount.setVisibility(View.VISIBLE);
        llSignIn.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showLoginSection() {
        llAccount.setVisibility(View.INVISIBLE);
        llSignIn.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadingIndicator() {
        llLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        llLoading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showErrorFromSever(Response response) {
        try {
            JSONObject jsonObject = new JSONObject(response.errorBody().string());
            Toast.makeText(getContext(), jsonObject.getString("status_message"), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void showErrorWhenFailure(String error) {

        Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
    }
}
