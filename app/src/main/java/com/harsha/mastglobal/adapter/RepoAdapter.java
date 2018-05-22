package com.harsha.mastglobal.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.harsha.mastglobal.DetailFragment;
import com.harsha.mastglobal.R;
import com.harsha.mastglobal.model.UserRepoResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by harsha on 2/14/18.
 */

public class RepoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<UserRepoResponse> mList;

    public RepoAdapter(Context context, List<UserRepoResponse> list) {
        this.mContext = context;
        this.mList = list;
    }

    public class PrintViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.card_view)
        public CardView cardView;

        @BindView(R.id.title)
        public TextView tvTitle;

        @BindView(R.id.link)
        public TextView tvLink;

        @BindView(R.id.size)
        public TextView tvSize;

        @BindView(R.id.watchers)
        public TextView tvWatchers;

        @BindView(R.id.issues)
        public TextView tvIssues;

        public PrintViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View categoryView = inflater.inflate(R.layout.card_repo, parent, false);
        return new PrintViewHolder(categoryView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        PrintViewHolder printViewHolder = (PrintViewHolder) holder;
        printViewHolder.tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(mList.get(holder.getAdapterPosition()).getUrl()));
                mContext.startActivity(i);
            }
        });
        printViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                DetailFragment detailFragment = new DetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title", mList.get(holder.getAdapterPosition()).getName());
                bundle.putString("description", mList.get(holder.getAdapterPosition()).getDescription());
                detailFragment.setArguments(bundle);
                transaction.add(R.id.container, detailFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        bindPrintViewHolder(printViewHolder, position);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    private void bindPrintViewHolder(PrintViewHolder printViewHolder, int position) {
        UserRepoResponse response = mList.get(position);
        printViewHolder.tvSize.setText(String.valueOf(response.getSize()));
        printViewHolder.tvWatchers.setText(String.valueOf(response.getWatchers()));
        printViewHolder.tvIssues.setText(String.valueOf(response.getOpenIssues()));
        printViewHolder.tvTitle.setText(response.getName());
    }
}
