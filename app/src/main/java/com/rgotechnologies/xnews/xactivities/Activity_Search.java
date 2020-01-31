package com.rgotechnologies.xnews.xactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.rgotechnologies.xnews.R;
import com.rgotechnologies.xnews.xadapters.GenPostAdapter;
import com.rgotechnologies.xnews.xconfig.XConfig;
import com.rgotechnologies.xnews.xlibs.CustomProgressbar;
import com.rgotechnologies.xnews.xmodels.general.GenPostResponse;
import com.rgotechnologies.xnews.xretro.ApiClient;
import com.rgotechnologies.xnews.xretro.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Search extends AppCompatActivity {

    SearchView searchView;
    RecyclerView recyclerView;
    List<GenPostResponse> responseList;
    MaterialToolbar toolbar;
    GenPostAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initToolbar();
        initializeAll();
    }
    private void initToolbar() {
        try {
            toolbar=findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_searchzx)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.performClick();
        searchView.setIconified(false);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                finish();
                return true;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s.length() > 0) {
                    callSearchAPI(searchView.getQuery().toString());
                } else {
                    Toast.makeText(Activity_Search.this, "" + getResources().getString(R.string.search), Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        searchManager.setOnCancelListener(new SearchManager.OnCancelListener() {
            @Override
            public void onCancel() {
                Toast.makeText(Activity_Search.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                finish();
                //overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                return true;
            }
        });
        MenuItem.OnActionExpandListener listener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                finish();
                return true;
            }
        };
        return true;
    }

    private void callSearchAPI(String searchQuery) {
        Log.d(XConfig.TAG, "onResponse : ViewPostsByCategoriesActivity : getPostsFromServer");
        CustomProgressbar.showProgressBar(Activity_Search.this,false);
        ApiService apiService= ApiClient.getApiClient().create(ApiService.class);
        Call<List<GenPostResponse>> responseCall=apiService.getSearch(searchQuery,"100");
        responseCall.enqueue(new Callback<List<GenPostResponse>>() {
            @Override
            public void onResponse(Call<List<GenPostResponse>> call, Response<List<GenPostResponse>> response) {
                CustomProgressbar.hideProgressBar();
                if(response.isSuccessful()) {
                    if (response.body().size() > 0) {
                        responseList=response.body();
                        adapter=new GenPostAdapter(responseList,Activity_Search.this);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
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
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
