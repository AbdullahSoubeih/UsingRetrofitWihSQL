package com.abdullahsoubeih.usingretrofitwihsql.RetrofitWithListView.Classes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.abdullahsoubeih.usingretrofitwihsql.R;
import com.abdullahsoubeih.usingretrofitwihsql.RetrofitWithHTTPRequests.UsingHTTPRequestsActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button OpenRecyclerView = findViewById(R.id.OpenRecyclerView);
        OpenRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,UsingHTTPRequestsActivity.class));
            }
        });




        final ListView listView = findViewById(R.id.listView);



        //use Retrofit library to connection with BASE_URL
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<ItemForListView>> call = api.getHeroes();
        call.enqueue(new Callback<List<ItemForListView>>() {
            @Override
            public void onResponse(Call<List<ItemForListView>> call, Response<List<ItemForListView>> response) {

                List<ItemForListView> items = response.body();

                String[] heroNames = new String[items.size()];

                for (int i=0 ; i<items.size(); i++){
                    heroNames[i] = items.get(i).getName();
                }

                listView.setAdapter(
                        new ArrayAdapter<String>(
                                getApplicationContext(),
                                android.R.layout.simple_list_item_1,
                                heroNames
                        )
                );

                /*
                for (ItemForListView h: heroes){
                    Log.d("name",h.getName());
                    Log.d("realname",h.getRealname());
                    Log.d("imageurl",h.getImageurl());
                }
                */

            }

            @Override
            public void onFailure(Call<List<ItemForListView>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}
