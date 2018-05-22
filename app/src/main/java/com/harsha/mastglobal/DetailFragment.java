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
import android.widget.EditText;
import android.widget.TextView;

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

public class DetailFragment extends Fragment {

    Unbinder unbinder;
    View mView;

    @BindView(R.id.title)
    TextView tvTitle;

    @BindView(R.id.description)
    TextView tvDescription;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_details, container, false);
        unbinder = ButterKnife.bind(this, mView);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null)
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String title = getArguments().getString("title");
        String description = getArguments().getString("description");
        tvTitle.setText(title);
        tvDescription.setText(description);
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
