package com.abdullahsoubeih.usingretrofitwihsql.RetrofitWithHTTPRequests;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.abdullahsoubeih.usingretrofitwihsql.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsingHTTPRequestsActivity extends AppCompatActivity {

    private TextView textViewResult;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using_http_requests);

        textViewResult = findViewById(R.id.text_view_result);


       /*
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        */

        Gson gson = new GsonBuilder().serializeNulls().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        //call with [JsonPlaceHolderApi.java] to call Refrence [posts]
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


        //Using HTTP Requests
        getPosts();

        //getComments();

       // createPost();

       // updatePost();

       // patchPost();

        //deletePost();

    }

    //get Post
    private void getPosts(){
        //call method [getPosts()] from [JsonPlaceHolderApi.java]

        Map<String,String> parameters = new HashMap<>();
        parameters.put("userId","1");
        parameters.put("_sort","id");
        parameters.put("_order","desc");

         Call<List<Post>> call = jsonPlaceHolderApi.getPosts(parameters);  //to get data from this link [https://jsonplaceholder.typicode.com/posts?userId=1&_sort=id&_order=desc]

        // Call<List<Post>> call = jsonPlaceHolderApi.getPosts(new Integer[]{2,3,6},null,null);  //to get data from this link [https://jsonplaceholder.typicode.com/posts?userId=1&_sort=id&_order=desc]  ..... get Array UserId

        call.enqueue(new Callback<List<Post>>() {
            //if process is success
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                //if responce is not Successful
                if (!response.isSuccessful()){
                    textViewResult.setText("Code: "+response.code());
                    return;
                }
                List<Post> posts = response.body();

                for (Post post : posts){
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n\n";


                    textViewResult.append(content);
                }
            }

            //when wrong thing is occured
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
    //get Comments
    private void getComments(){
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments("posts/3/comments");

      //  Call<List<Comment>> call = jsonPlaceHolderApi.getComments(7);

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                //if responce is not Successful
                if (!response.isSuccessful()){
                    textViewResult.setText("Code: "+response.code());
                    return;
                }
                List<Comment> comments = response.body();

                for (Comment comment : comments){
                    String content = "";
                    content += "ID: " + comment.getId() + "\n";
                    content += "Post ID: " + comment.getPostId() + "\n";
                    content += "Name: " + comment.getName() + "\n";
                    content += "Email: " + comment.getEmail() + "\n";
                    content += "Text: " + comment.getText() + "\n\n";


                    textViewResult.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }




    //Add new Post  [createPost]
    private void createPost(){

       // Post post = new Post(23,"New Title","New Text");
       // Call<Post> call = jsonPlaceHolderApi.createPost(post);

        // Call<Post> call = jsonPlaceHolderApi.createPost(23,"New Title", "New Text");


        Map<String,String> fields = new HashMap<>();
        fields.put("userId","25");
        fields.put("title","New Title");

        Call<Post> call = jsonPlaceHolderApi.createPost(fields);


        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                //if responce is not Successful
                if (!response.isSuccessful()){
                    textViewResult.setText("Code: "+response.code());
                    return;
                }

                Post postResponce = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponce.getId() + "\n";
                content += "User ID: " + postResponce.getUserId() + "\n";
                content += "Title: " + postResponce.getTitle() + "\n";
                content += "Text: " + postResponce.getText() + "\n\n";

                textViewResult.setText(content);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }


    //Update Post by @PUT ................... Put change all Values as (title & text) by (id)
    private void updatePost(){
        Post post = new Post(12,null,"New Text");

        Call<Post> call = jsonPlaceHolderApi.putPost(5,post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                //if responce is not Successful
                if (!response.isSuccessful()){
                    textViewResult.setText("Code: "+response.code());
                    return;
                }

                Post postResponce = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponce.getId() + "\n";
                content += "User ID: " + postResponce.getUserId() + "\n";
                content += "Title: " + postResponce.getTitle() + "\n";
                content += "Text: " + postResponce.getText() + "\n\n";

                textViewResult.setText(content);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }


    //Update Post by @PATCH ................. Patch change only Values that found as (text) by (id)
    private void patchPost(){
        Post post = new Post(12,null,"New Text");

        Call<Post> call = jsonPlaceHolderApi.patchPost(5,post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                //if responce is not Successful
                if (!response.isSuccessful()){
                    textViewResult.setText("Code: "+response.code());
                    return;
                }

                Post postResponce = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponce.getId() + "\n";
                content += "User ID: " + postResponce.getUserId() + "\n";
                content += "Title: " + postResponce.getTitle() + "\n";
                content += "Text: " + postResponce.getText() + "\n\n";

                textViewResult.setText(content);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    //Delete  Post by @DELETE
    private void deletePost(){
        Call<Void> call = jsonPlaceHolderApi.deletePost(5);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textViewResult.setText("Code: " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
               textViewResult.setText(t.getMessage());
            }
        });
    }


}
