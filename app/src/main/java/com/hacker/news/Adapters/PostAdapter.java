package com.hacker.news.Adapters;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;
import com.hacker.news.Models.PostModel;
import com.hacker.news.R;
import com.hacker.news.Utils.Common;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.CustomViewHolder> {

    private AppCompatActivity mContext;

    ArrayList<PostModel> posts;

    public PostAdapter(AppCompatActivity mContext, ArrayList<PostModel> posts) {
        this.mContext = mContext;
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new PostAdapter.CustomViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final PostAdapter.CustomViewHolder customViewHolder, final int pos) {
        PostModel post = posts.get(pos);
        customViewHolder.txtNumber.setText((pos + 1) + ".");

        String host = "";
        if (post.getUrl() != null) {
            URL url = null;
            try {
                url = new URL(post.getUrl());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            host = url.getHost();
        } else {
            host = "";
        }

        SpannableString ss1 = host.length() > 0 ? new SpannableString(post.getTitle() + " (" + host + ")") : new SpannableString(post.getTitle());
        ss1.setSpan(new RelativeSizeSpan(1.33f), 0, post.getTitle().length(), 0); // set size
        ss1.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.colorBack)), 0, post.getTitle().length(), 0);// set color
        customViewHolder.txtTitle.setText(ss1);

        String comments = post.getDescendants() == 0 ? "discuss" : post.getDescendants() + " comments";

        long timestamp = System.currentTimeMillis()/1000 - post.getTime();
        String time = Common.convertTimeStampToGeneralTime(timestamp);

        customViewHolder.txtSubTitle.setText(post.getScore() + " points by " + post.getBy() + " " + time + " | hide | " + comments);

        customViewHolder.btnMain.setOnClickListener(v -> {
            if (post.getUrl() != null) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(mContext, Uri.parse(post.getUrl()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return (posts != null ? posts.size() : 0);
    }

    public void updatePosts(ArrayList<PostModel> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        LinearLayout btnMain;
        TextView txtNumber, txtTitle, txtSubTitle;

        CustomViewHolder(View view) {
            super(view);
            btnMain = view.findViewById(R.id.btnMain);
            txtNumber = view.findViewById(R.id.txtNumber);
            txtTitle = view.findViewById(R.id.txtTitle);
            txtSubTitle = view.findViewById(R.id.txtSubTitle);
        }
    }
}