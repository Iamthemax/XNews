package com.rgotechnologies.xnews.xactivities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.rgotechnologies.xnews.R;
import com.rgotechnologies.xnews.xadapters.FavouritesListAdapter;
import com.rgotechnologies.xnews.xadapters.GenPostAdapter;
import com.rgotechnologies.xnews.xlibs.XSharedPreferences;
import com.rgotechnologies.xnews.xmodels.general.GenPostResponse;

import java.util.ArrayList;
import java.util.List;

public class FavouritesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<GenPostResponse> responseList;
    MaterialToolbar toolbar;
    FavouritesListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        initToolbar();
        initializeAll();
    }

    private void initToolbar() {
        try {
            toolbar=findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Saved Posts");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeAll() {
        recyclerView=findViewById(R.id.recyclerView);
        responseList=new ArrayList<>();

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        /*adapter=new GenPostAdapter(responseList,this);
        recyclerView.setAdapter(adapter);
        getPostsFromServer(catId);*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        XSharedPreferences sharedPreferencesList =new XSharedPreferences();
        responseList= sharedPreferencesList.getFavorites(FavouritesActivity.this);
        adapter=new FavouritesListAdapter(responseList,FavouritesActivity.this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_delete, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home) {
            onBackPressed();
          //  overridePendingTransition(R.anim.left_to_right,R.anim.right_to_left);
            return true;
        }
        if(item.getItemId()==R.id.action_delete_favourite)
        {
            showConfirmDialog();
        }
        return false;
    }

    private void showConfirmDialog() {

        final AlertDialog alertDialog=new AlertDialog.Builder(FavouritesActivity.this).create();
        alertDialog.setCancelable(true);
        alertDialog.setTitle(R.string.warn_fav_clear);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes ..", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                XSharedPreferences sharedPreferencesList =new XSharedPreferences();
                sharedPreferencesList.clearAllFavourites(FavouritesActivity.this);
                responseList.clear();
                adapter.notifyDataSetChanged();
                alertDialog.dismiss();


            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

}
