package com.abdullahsoubeih.usingretrofitwihsql.RecyclerViewWithApi.controller;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abdullahsoubeih.usingretrofitwihsql.RecyclerViewWithApi.ItemAdapter;
import com.abdullahsoubeih.usingretrofitwihsql.R;
import com.abdullahsoubeih.usingretrofitwihsql.RecyclerViewWithApi.api.Client;
import com.abdullahsoubeih.usingretrofitwihsql.RecyclerViewWithApi.api.Service;
import com.abdullahsoubeih.usingretrofitwihsql.RecyclerViewWithApi.model.Item;
import com.abdullahsoubeih.usingretrofitwihsql.RecyclerViewWithApi.model.ItemResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewWithApiActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    TextView Dissconnected;
    private Item item;
    ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;


    private Timer timer;

    class AdvertisementsTimerTask extends TimerTask{

        @Override
        public void run() {

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_with_api);

        initViews();

        swipeContainer = (SwipeRefreshLayout)findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadJSON();
                Toast.makeText(RecyclerViewWithApiActivity.this,"Github Users Refreshed",Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void initViews(){
        pd = new ProgressDialog(this);
        pd.setMessage("Fetching Github Users...");
        pd.setCancelable(false);
        pd.show();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.smoothScrollToPosition(0);

        loadJSON();

    }

    private void loadJSON(){
        Dissconnected = (TextView)findViewById(R.id.disconnected);
        try {
            Client Client = new Client();
            final Service apiService = Client.getClient().create(Service.class);
            try {
                // parameters.put("login", SearchText);
                Call<ItemResponse> call = apiService.getItems();
                //  Call<ItemResponse> call = apiService.getItems();
                call.enqueue(new Callback<ItemResponse>() {
                    @Override
                    public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                        List<Item> items = response.body().getItems();
                        if (items != null) {
                            recyclerView.setAdapter(new ItemAdapter(getApplicationContext(), items));
                            recyclerView.smoothScrollToPosition(0);
                            swipeContainer.setRefreshing(false);
                            pd.hide();
                        }
                    }

                    @Override
                    public void onFailure(Call<ItemResponse> call, Throwable t) {
                        Log.d("Error", t.getMessage());
                        Toast.makeText(RecyclerViewWithApiActivity.this, "Error Fetching Data !", Toast.LENGTH_SHORT).show();
                        Dissconnected.setVisibility(View.VISIBLE);
                        pd.hide();
                    }
                });
            } catch (Exception e) {
                Log.d("Error", e.getMessage());
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }




        }catch (Exception e){}
    }


    @Override
    protected void onStart() {
        super.onStart();
        //create a new instance of Timer when activity is going to start
        timer = new Timer();

        //schedule or start timer , with 0 or no delay , and 
        timer.schedule(new AdvertisementsTimerTask(),0,2*1000);
    }
}
