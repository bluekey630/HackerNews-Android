package com.hacker.news.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;
import com.hacker.news.Adapters.PostAdapter;
import com.hacker.news.ApiSerivce.RetrofitClient;
import com.hacker.news.Models.PostModel;
import com.hacker.news.R;
import com.hacker.news.Utils.LoadingDialog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    List<Integer> postIDs = new ArrayList<>();
    ArrayList<PostModel> posts = new ArrayList<>();

    PostAdapter adapter;

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        showPostData();
        getTopStories();
    }

    private void getTopStories() {
        loadingDialog = new LoadingDialog(this, false);

        Call<List<Integer>> call = RetrofitClient.getInstance().getMyApi().getTopStories();
        call.enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
//                loadingDialog.hide();
                postIDs = response.body();

                if (postIDs.size() > 0) {
                    getPostDetails();
                }
            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {
                if (loadingDialog != null) {
                    loadingDialog.hide();
                }

                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getPostDetails() {
        ArrayList<Integer> ids = new ArrayList<>();
        if (postIDs.size() > 30) {
            for(int i = 0; i < 30; i ++) {
                ids.add(postIDs.get(0));
                postIDs.remove(0);
            }
        } else {
            if (postIDs.size() > 0) {
                for (Iterator<Integer> iterator = postIDs.iterator(); iterator.hasNext(); ) {
                    Integer id = iterator.next();
                    ids.add(id);
                    iterator.remove();
                }
            }
        }

        for (Integer postID : ids) {
            Call<PostModel> call = RetrofitClient.getInstance().getMyApi().getPostDetailByID(postID.toString());
            call.enqueue(new Callback<PostModel>() {
                @Override
                public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                    PostModel post = response.body();
                    posts.add(post);
                    if (posts.size() >= 30) {
                        if (loadingDialog != null) {
                            loadingDialog.hide();
                        }
                        adapter.updatePosts(posts);
                    }
                }

                @Override
                public void onFailure(Call<PostModel> call, Throwable t) {

                    if (loadingDialog != null) {
                        loadingDialog.hide();
                    }
                    Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void showPostData() {
        @SuppressLint("WrongConstant") LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setSmoothScrollbarEnabled(true);
        mLayoutManager.setRecycleChildrenOnDetach(true);
        recycleView.setLayoutManager(mLayoutManager);

        adapter = new PostAdapter(this, posts);
        recycleView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int pos = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition ();
                if (pos == posts.size() - 1 && postIDs.size() > 0) {
                    getPostDetails();
                }
            }
        });
    }
}