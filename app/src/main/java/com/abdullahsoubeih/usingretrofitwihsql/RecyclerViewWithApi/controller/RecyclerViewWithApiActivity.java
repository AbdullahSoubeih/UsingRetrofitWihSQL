package com.abdullahsoubeih.usingretrofitwihsql.RecyclerViewWithApi.controller;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.abdullahsoubeih.usingretrofitwihsql.RecyclerViewWithApi.ItemAdapter;
import com.abdullahsoubeih.usingretrofitwihsql.R;
import com.abdullahsoubeih.usingretrofitwihsql.RecyclerViewWithApi.api.Client;
import com.abdullahsoubeih.usingretrofitwihsql.RecyclerViewWithApi.api.Service;
import com.abdullahsoubeih.usingretrofitwihsql.RecyclerViewWithApi.model.Item;
import com.abdullahsoubeih.usingretrofitwihsql.RecyclerViewWithApi.model.ItemResponse;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewWithApiActivity extends AppCompatActivity {

    private List<Item> items;

    private ItemAdapter itemAdapter;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    TextView Dissconnected;
    private Item item;
    ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;






    ////////////////////////////// For RecyclerView with Timer ////////////////////////////////////
    private Timer timer;

    //cursor to track current position of item visible in your Advertisement RecyclerView
    int cursor = 0;

    int itemCountOfAdvertisements;


    class AdvertisementsTimerTask extends TimerTask{

        //this variable in timer task will basically hold count of advertisement images initially and made final to signify that it won't change during the activity screen
        final int count ;

        public AdvertisementsTimerTask(int count) {
            this.count = count;
        }

        @Override
        public void run() {

            if (cursor < count){
                //but since its a different thread thant main thread we should make it run on ui thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //we can scroll to position using        linearLayoutManager.scrollToPosition(cursor);       as well

                        recyclerView.scrollToPosition(cursor);
                        cursor++;
                    }
                });
            }

            //check if last position has reached , then reset it to first position or 0th position
            if (cursor >= count){
                cursor = 0;
            }

        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////


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
        //there is possibility that user might interact with the recyclerView manually , so we need to set cursor variable accordingly for this purpose we need to keep track when user is interacting with recyclerView
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    //there is no method in recyclerView to identify last or first position after scroll
                   cursor =  linearLayoutManager.findLastVisibleItemPosition();
                }

            }
        });

        //for this purpose we would need following linearLayoutManager set to recyclerView
        linearLayoutManager = new LinearLayoutManager(RecyclerViewWithApiActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
       // recyclerView.smoothScrollToPosition(0);

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
                        items = response.body().getItems();

                        if (items != null) {
                            itemAdapter = new ItemAdapter(getApplicationContext(), items);
                            recyclerView.setAdapter(itemAdapter);
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





    ////////////////////////////// For RecyclerView with Timer ////////////////////////////////////
    @Override
    protected void onStart() {
        super.onStart();

        // itemCountOfAdvertisements  = recyclerView.getAdapter().getItemCount();
          // itemCountOfAdvertisements  = recyclerView.getChildCount();

        itemCountOfAdvertisements = 29;

      //  itemCountOfAdvertisements = itemAdapter.getItemCount();

        //create a new instance of Timer when activity is going to start
        timer = new Timer();

        //schedule or start timer , with 0 or no delay , and period of 2000ms = 2sec
        timer.schedule(new AdvertisementsTimerTask(itemCountOfAdvertisements),0,800);


    }

    @Override
    protected void onStop() {
        super.onStop();

        //stop the timer in onStop of your activity
        if (timer != null){
            timer.cancel();
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
}
