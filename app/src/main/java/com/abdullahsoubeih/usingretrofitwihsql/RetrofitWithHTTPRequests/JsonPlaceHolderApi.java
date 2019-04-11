package com.abdullahsoubeih.usingretrofitwihsql.RetrofitWithHTTPRequests;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceHolderApi {


    //HTTP Requests

    //////////////////////////////////////////////////////////////////////////////////////Use @GET to Fetch[get data]////////////////////////////////////////////////////////////
    //called with Refrence [posts] from[baseUrl : (https://jsonplaceholder.typicode.com/)]


/////////////by @Query /////////////////////////
    //called with Post.java [class]
    @GET("posts")
    Call<List<Post>> getPosts(  //to get data from this link [https://jsonplaceholder.typicode.com/posts?userId=1&_sort=id&_order=desc] ..... get Array UserId
            @Query("userId") Integer[] userId,
            @Query("_sort") String sort,
            @Query("_order") String order
            );



///////////////////////////////by @QueryMap with Parameters////////////////////////////
    //called with Post.java [class]
    @GET("posts")
    Call<List<Post>> getPosts(@QueryMap Map<String,String> parameters);


//////////by id /////////////////////////////
    // use [ @Path("id") ] to Specific Comments by it PostId
    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId);


    ////////////////////
    @GET
    Call<List<Comment>> getComments(@Url String url);
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //////////////////////////////////////////////////////////////////////////////////Use @POST to set new Data////////////////////////////////////////////////////////////////////
    @POST("posts")
    Call<Post> createPost(@Body Post post);

    /////////////////////////////////////////////////////
    @FormUrlEncoded
    @POST("posts")
    Call<Post>  createPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String text
    );

    //////////////////////////////////////////////////
    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(@FieldMap Map<String,String> fields);

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////Use @PUT to Update a Post  ................... Put change all Values as (title & text) by (id)/////////////////////////////////////////////////////////
    @PUT("posts/{id}")
    Call<Post> putPost(@Path("id") int id, @Body Post post);
    //////////////////////////////////////////////////////////////////////////Use @PATCH to Update a Post ................. Patch change only Values that found as (text) by (id)//////////////////////////////////////////////////////////////////////////
    @PATCH("posts/{id}")
    Call<Post> patchPost(@Path("id") int id, @Body Post post);
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////Use @DELETE to Delete Post //////////////////////////////////////////////////////////////////////////////
    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);

}
