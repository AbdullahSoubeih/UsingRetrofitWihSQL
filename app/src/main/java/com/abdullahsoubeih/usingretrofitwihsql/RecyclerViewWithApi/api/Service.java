package com.abdullahsoubeih.usingretrofitwihsql.RecyclerViewWithApi.api;

import com.abdullahsoubeih.usingretrofitwihsql.RecyclerViewWithApi.model.ItemResponse;
import com.abdullahsoubeih.usingretrofitwihsql.RetrofitWithHTTPRequests.Comment;
import com.abdullahsoubeih.usingretrofitwihsql.RetrofitWithHTTPRequests.Post;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface Service {


    @GET("/search/users?q=language:java+location:lagos")
    Call<ItemResponse> getItems();

}
