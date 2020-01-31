package com.rgotechnologies.xnews.xactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.blankj.utilcode.util.NetworkUtils;
import com.google.android.material.appbar.MaterialToolbar;
import com.rgotechnologies.xnews.R;
import com.rgotechnologies.xnews.xconfig.XConfig;
import com.rgotechnologies.xnews.xadapters.CategoriesAdapter;
import com.rgotechnologies.xnews.xlibs.CustomProgressbar;
import com.rgotechnologies.xnews.xmodels.categories.CategoriesResponse;
import com.rgotechnologies.xnews.xretro.ApiClient;
import com.rgotechnologies.xnews.xretro.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    CategoriesAdapter adapter;
    List<CategoriesResponse> responseList;
    MaterialToolbar toolbar;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Categories");
        recyclerView=findViewById(R.id.recyclerViewCategories);
        responseList=new ArrayList<>();
        adapter=new CategoriesAdapter(responseList,this);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,2,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        /*recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));*/
        recyclerView.setAdapter(adapter);
        getCategoriesFromServer();
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(false);
    }

    private void getCategoriesFromServer() {
        CustomProgressbar.showProgressBar(this,false);
        ApiService apiService= ApiClient.getApiClient().create(ApiService.class);
        Call<List<CategoriesResponse>> responseCall=apiService.getCategories();
        responseCall.enqueue(new Callback<List<CategoriesResponse>>() {
            @Override
            public void onResponse(Call<List<CategoriesResponse>> call, Response<List<CategoriesResponse>> response) {
                CustomProgressbar.hideProgressBar();
                Log.d(XConfig.TAG,"onResponse :CategoriesActivity ");
                if(response.isSuccessful()){
                    if(response.body().size()>0){
                        responseList=response.body();
                        adapter=new CategoriesAdapter(responseList,CategoriesActivity.this);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);

                    }else{

                    }
                }else{
                    Log.d(XConfig.TAG,"onResponse : un sucessfull response ");
                }
            }

            @Override
            public void onFailure(Call<List<CategoriesResponse>> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
                Log.d(XConfig.TAG,"onFailure :CategoriesActivity "+t.getMessage());
                Log.d(XConfig.TAG,"onFailure :CategoriesActivity "+t.getCause());
                Log.d(XConfig.TAG,"onFailure :CategoriesActivity"+t.getLocalizedMessage());
                t.printStackTrace();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  android.R.id.home :{
                onBackPressed();
                //finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        if (NetworkUtils.isConnected()) {
            getCategoriesFromServer();
        } else {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(this, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
        }
    }
}
