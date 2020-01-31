package com.rgotechnologies.xnews.xactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.rgotechnologies.xnews.R;

public class OptionsActivity extends AppCompatActivity implements View.OnClickListener {
    MaterialToolbar toolbar;
    LinearLayout shareAppLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        initToolbar();
        shareAppLayout=findViewById(R.id.shareAppLayout);
        shareAppLayout.setOnClickListener(this);
    }
    private void initToolbar() {
        try {
            toolbar=findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Options");

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    protected void sharePostLink(String postURL, String postTitle) {
        try {
            int applicationNameId = this.getApplicationInfo().labelRes;
            final String appPackageName = getPackageName();
            String appLink = "https://play.google.com/store/apps/details?id=" + appPackageName;
            Intent shareIntent = new Intent();
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/*");
            shareIntent.putExtra(Intent.EXTRA_TEXT, postTitle + "\n\n" + appLink);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, postTitle);
            startActivity(Intent.createChooser(shareIntent, "Share  to.."));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.shareAppLayout){
            sharePostLink("","XNews");
        }
    }
}
