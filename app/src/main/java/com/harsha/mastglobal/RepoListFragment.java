package com.harsha.mastglobal;

import android.app.ProgressDialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.harsha.mastglobal.adapter.RepoAdapter;
import com.harsha.mastglobal.model.UserRepoResponse;
import com.harsha.mastglobal.model.UserResponse;
import com.harsha.mastglobal.rest.ApiClient;
import com.harsha.mastglobal.rest.ApiInterface;

import java.util.ArrayList;

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

public class RepoListFragment extends Fragment {

    Unbinder unbinder;
    ProgressDialog mProgressDialog;
    View mView;
    String userName;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_repo_list, container, false);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null)
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        unbinder = ButterKnife.bind(this, mView);
        userName = getArguments().getString("username");
        getUserRepo();
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void getUserRepo() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.dialog_message));
        mProgressDialog.show();
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<UserRepoResponse>> call = apiService.getUserRepo(userName);
        call.enqueue(new Callback<ArrayList<UserRepoResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<UserRepoResponse>> call, Response<ArrayList<UserRepoResponse>> response) {
                RepoAdapter adapter = new RepoAdapter(getActivity(), response.body());
                RecyclerViewMargin decoration = new RecyclerViewMargin(10, 1);
                mRecyclerView.addItemDecoration(decoration);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setAdapter(adapter);
                mProgressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ArrayList<UserRepoResponse>> call, Throwable t) {
                Snackbar.make(mView, R.string.network_error, Snackbar.LENGTH_LONG).show();
                mProgressDialog.dismiss();
            }
        });
    }

    public class RecyclerViewMargin extends RecyclerView.ItemDecoration {
        private final int columns;
        private int margin;

        /**
         * constructor
         *
         * @param margin  desirable margin size in px between the views in the recyclerView
         * @param columns number of columns of the RecyclerView
         */
        public RecyclerViewMargin(@IntRange(from = 0) int margin, @IntRange(from = 0) int columns) {
            this.margin = margin;
            this.columns = columns;

        }

        /**
         * Set different margins for the items inside the recyclerView: no top margin for the first row
         * and no left margin for the first column.
         */
        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {

            int position = parent.getChildLayoutPosition(view);
            //set right margin to all
            outRect.right = margin;
            //set bottom margin to all
            outRect.bottom = margin;
            //we only add top margin to the first row
            if (position < columns) {
                outRect.top = margin;
            }
            //add left margin only to the first column
            if (position % columns == 0) {
                outRect.left = margin;
            }
        }
    }
}
