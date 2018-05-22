package com.harsha.mastglobal;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.harsha.mastglobal.model.UserResponse;
import com.harsha.mastglobal.rest.ApiClient;
import com.harsha.mastglobal.rest.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by harsha on 5/21/18.
 */

public class UserFragment extends Fragment {

    Unbinder unbinder;
    ProgressDialog mProgressDialog;
    View mView;
    RepoListFragment fragment;

    @BindView(R.id.name)
    EditText editTextName;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_user, container, false);
        unbinder = ButterKnife.bind(this, mView);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null)
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.get_user)
    public void onGetUserClick(){
        if (validateInputs()) {
            final String name = editTextName.getText().toString().trim();
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(getString(R.string.dialog_message));
            mProgressDialog.show();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<UserResponse> call = apiService.getUser(name);
            call.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    mProgressDialog.dismiss();
                    if (response.body() != null && response.body().getLogin().equals(name)) {
                        fragment = new RepoListFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("username", response.body().getLogin());
                        fragment.setArguments(bundle);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, fragment);
                        transaction.commit();
                    } else {
                        Snackbar.make(mView, R.string.user_error, Snackbar.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Snackbar.make(mView, R.string.network_error, Snackbar.LENGTH_LONG).show();
                    mProgressDialog.dismiss();
                }
            });
        }
    }

    private boolean validateInputs() {
        boolean validUserName = false;

        validUserName = validateName();
        if (!validUserName && editTextName != null) {
            editTextName.requestFocus();
        }
        return validUserName;
    }

    private boolean validateName() {
        if (editTextName == null) return false;
        boolean valid = !TextUtils.isEmpty(editTextName.getText().toString().trim());
        if (!valid) {
            if (editTextName != null) {
                editTextName.setError("Field should not be empty");
            }
        }
        return valid;
    }
}
