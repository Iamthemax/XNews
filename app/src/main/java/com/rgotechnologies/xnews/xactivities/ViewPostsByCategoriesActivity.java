package com.rgotechnologies.xnews.xactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.blankj.utilcode.util.NetworkUtils;
import com.google.android.material.appbar.MaterialToolbar;
import com.rgotechnologies.xnews.R;
import com.rgotechnologies.xnews.xadapters.CategoriesAdapter;
import com.rgotechnologies.xnews.xadapters.GenPostAdapter;
import com.rgotechnologies.xnews.xconfig.XConfig;
import com.rgotechnologies.xnews.xlibs.CustomProgressbar;
import com.rgotechnologies.xnews.xmodels.categories.CategoriesResponse;
import com.rgotechnologies.xnews.xmodels.general.GenPostResponse;
import com.rgotechnologies.xnews.xretro.ApiClient;
import com.rgotechnologies.xnews.xretro.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewPostsByCategoriesActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    List<GenPostResponse> responseList;
    MaterialToolbar toolbar;
    GenPostAdapter adapter;
    String catId="",catName="";
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_posts_by_categories);
        try {
            catId=getIntent().getStringExtra("catId");
            catName=getIntent().getStringExtra("catName");
        } catch (Exception e) {
            e.printStackTrace();
        }
        initToolbar();
        initializeAll();
    }

    private void initToolbar() {
        try {
            toolbar=findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(catName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeAll() {
        recyclerView=findViewById(R.id.recyclerView);
        responseList=new ArrayList<>();
        adapter=new GenPostAdapter(responseList,this);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        getPostsFromServer(catId);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(false);
    }

    private void getPostsFromServer(String catId) {
        Log.d(XConfig.TAG, "onResponse : ViewPostsByCategoriesActivity : getPostsFromServer");
        CustomProgressbar.showProgressBar(ViewPostsByCategoriesActivity.this,false);
        ApiService apiService= ApiClient.getApiClient().create(ApiService.class);
        Call<List<GenPostResponse>> responseCall=apiService.getAllPostsByCategory(catId,"100");
        responseCall.enqueue(new Callback<List<GenPostResponse>>() {
            @Override
            public void onResponse(Call<List<GenPostResponse>> call, Response<List<GenPostResponse>> response) {
                CustomProgressbar.hideProgressBar();
                if(response.isSuccessful()) {
                    if (response.body().size() > 0) {
                        responseList=response.body();
                        adapter=new GenPostAdapter(responseList,ViewPostsByCategoriesActivity.this);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    } else {
                        Log.d(XConfig.TAG, "onResponse : No records found ");
                    }
                }else{
                    Log.d(XConfig.TAG, "onResponse : unsuccessful response");
                }
            }

            @Override
            public void onFailure(Call<List<GenPostResponse>> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
                Log.d(XConfig.TAG,"onFailure : "+t.getMessage());
                Log.d(XConfig.TAG,"onFailure : "+t.getCause());
                Log.d(XConfig.TAG,"onFailure : "+t.getLocalizedMessage());
                t.printStackTrace();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case  android.R.id.home:{
                onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onRefresh() {
        if (NetworkUtils.isConnected()) {
           getPostsFromServer(catId);
        } else {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(this, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
