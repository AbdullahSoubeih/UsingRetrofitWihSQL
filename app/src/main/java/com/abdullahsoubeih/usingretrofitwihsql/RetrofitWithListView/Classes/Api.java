package com.abdullahsoubeih.usingretrofitwihsql.RetrofitWithListView.Classes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    //we get(fetching) data from this link
    String BASE_URL = "https://simplifiedcoding.net/demos/";

    //call "marvel" from BASE_URL
    @GET("marvel")
    Call<List<ItemForListView>> getHeroes();

}
