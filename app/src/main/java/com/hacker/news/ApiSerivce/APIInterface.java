package com.hacker.news.ApiSerivce;

import com.hacker.news.Models.PostModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIInterface {
    @GET("topstories.json")
    Call<List<Integer>> getTopStories();

    @GET("item/{id}.json")
    Call<PostModel> getPostDetailByID(@Path("id") String id);
}
