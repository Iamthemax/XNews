package com.rgotechnologies.xnews.xactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.NetworkUtils;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.rgotechnologies.xnews.R;
import com.rgotechnologies.xnews.xadapters.GenPostAdapter;
import com.rgotechnologies.xnews.xlibs.XSharedPreferences;
import com.rgotechnologies.xnews.xmodels.general.GenPostResponse;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.ArrayList;
import java.util.List;

public class PostDetailsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Toolbar toolbar;
    TextView textViewPostDate;
    TextView textViewTitle;
    WebView webView;
    GenPostResponse postObj;
    List<GenPostResponse> favouritesList;
    XSharedPreferences sharedPreferences;
    ShineButton shineButton;
    ImageView imageViewToolbar;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        try {
            postObj = getIntent().getParcelableExtra("postObj");
        } catch (Exception e) {
            e.printStackTrace();
        }
        initToolbar();
        initializeAll();
    }
    private void initToolbar() {
        try {
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
            favouritesList=new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void initializeAll() {
        sharedPreferences=new XSharedPreferences();
        imageViewToolbar=findViewById(R.id.imageViewToolbar);
        textViewPostDate = findViewById(R.id.textViewPostDate);
        textViewTitle = findViewById(R.id.textViewPostTitle);
        webView = findViewById(R.id.webView);
        textViewTitle.setText(postObj.getTitle().getRendered());
        textViewPostDate.setText(postObj.getDateGmt());
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.setHorizontalScrollBarEnabled(false);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        webView.clearSslPreferences();
        webView.getSettings().setTextZoom(300);
        shineButton=(ShineButton)findViewById(R.id.shineButton);
        Glide.with(this).load(postObj.getFeaturedImageUrl()).into(imageViewToolbar);
        setContentsToWebView(postObj.getContent().getRendered());
        favouritesList = sharedPreferences.getFavorites(PostDetailsActivity.this);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(false);
        if (favouritesList == null) {
            favouritesList = new ArrayList<>();
        } else {
            if (favouritesList.contains(postObj)) {
                shineButton.setChecked(true);
            } else {
                shineButton.setChecked(false);
            }
        }
        shineButton.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {

                if(checked){
                    sharedPreferences.addFavorite(PostDetailsActivity.this, postObj);
                    Log.d("mytag","likeButton ::Liked ");
                }else{

                    sharedPreferences.removeFavorite(PostDetailsActivity.this, postObj);
                    Log.d("mytag","likeButton ::unLiked ");
                }
            }
        });

    }

    void setContentsToWebView(String content) {
        try {
            webView.loadDataWithBaseURL("https://test.rgotechnologies.com/wp/", content, "text/html", "UTF-8", null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("mytag", "ViewPostActivity => Exception => setContentsToWebView: :");
            Log.d("mytag", "ViewPostActivity => Exception => " + e.getCause());
            Log.d("mytag", "ViewPostActivity => Exception => " + e.getMessage());
            Log.d("mytag", "ViewPostActivity => Exception => " + e.getLocalizedMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.post_details_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_share_details :{

                sharePostLink(postObj.getLink(),postObj.getTitle().getRendered());
                break;
            }
            case R.id.action_open_external :{

                openLinkExternal(postObj.getLink());
                break;
            }
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
    private void openLinkExternal(String link) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
      /*  if(swipeRefreshLayout.isRefreshing())
        {
            swipeRefreshLayout.setRefreshing(false);
        }*/
    }

    @Override
    public void onRefresh() {
        if (NetworkUtils.isConnected()) {
            Glide.with(this).load(postObj.getFeaturedImageUrl()).into(imageViewToolbar);
            setContentsToWebView(postObj.getContent().getRendered());
            textViewTitle.setText(postObj.getTitle().getRendered());
            textViewPostDate.setText(postObj.getDateGmt());
            swipeRefreshLayout.setRefreshing(false);

        } else {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(this, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
        }
    }
}
