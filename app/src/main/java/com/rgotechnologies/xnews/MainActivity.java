package com.rgotechnologies.xnews;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.rgotechnologies.xnews.xactivities.Activity_Search;
import com.rgotechnologies.xnews.xactivities.CategoriesActivity;
import com.rgotechnologies.xnews.xactivities.FavouritesActivity;
import com.rgotechnologies.xnews.xactivities.OptionsActivity;
import com.rgotechnologies.xnews.xfragments.BusinessFragment;
import com.rgotechnologies.xnews.xfragments.EntertainmentFragment;
import com.rgotechnologies.xnews.xfragments.FashionFragment;
import com.rgotechnologies.xnews.xfragments.RecentNewsFragment;
import com.rgotechnologies.xnews.xlibs.XViewPagerAdapter;

public class MainActivity extends AppCompatActivity implements RecentNewsFragment.OnFragmentInteractionListener {

    MaterialToolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    public static boolean isConnected=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkInternet();
        toolbar=findViewById(R.id.toolbar);
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        setSupportActionBar(toolbar);
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.navigation);
        viewPager=findViewById(R.id.viewPager);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setupViewPager();
        tabLayout.setupWithViewPager(viewPager,false);


    }

    private void checkInternet() {

    }

    private void setupViewPager() {
        XViewPagerAdapter adapter = new XViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.POSITION_NONE);
        adapter.addFragment(new RecentNewsFragment(), getResources().getString(R.string.recent_news));
        adapter.addFragment(new BusinessFragment(), getResources().getString(R.string.business));
        adapter.addFragment(new EntertainmentFragment(), getResources().getString(R.string.entertainment_news));
        adapter.addFragment(new FashionFragment(), getResources().getString(R.string.fashion));
       // adapter.addFragment(new SportsFragement(), getResources().getString(R.string.sports));
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_bottom_home:
                    //mTextMessage.setText(R.string.title_home);
                    try {
                        // viewPager.getAdapter().notifyDataSetChanged();
                        viewPager.setCurrentItem(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                case R.id.action_bottom_categoories:
                    //mTextMessage.setText(R.string.title_dashboard);
                    Intent intent=new Intent(MainActivity.this, CategoriesActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.action_bottom_search:
                    // mTextMessage.setText(R.string.title_notifications);
                    Intent intent1=new Intent(MainActivity.this, Activity_Search.class);
                    startActivity(intent1);
                    System.gc();
                    return true;
                case R.id.action_bottom_options:
                    // mTextMessage.setText(R.string.title_notifications);
                    Intent intent3=new Intent(MainActivity.this, OptionsActivity.class);
                    startActivity(intent3);
                    System.gc();
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home) {
            onBackPressed();
            //  overridePendingTransition(R.anim.left_to_right,R.anim.right_to_left);
            return true;
        }
        if(item.getItemId()==R.id.action_share_home)
        {
            sharePostLink("https://test.rgotechnologies.com/wp/", "XNews Latest News National Politics International Fashion Entertainment Sports");
            return true;
        }
        if(item.getItemId()==R.id.action_favourites_home)
        {
            Intent intent=new Intent(MainActivity.this, FavouritesActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
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
    protected void onResume() {
        super.onResume();
        Menu menu = bottomNavigationView.getMenu();
        menu.getItem(0).setChecked(true);

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private AlertDialog showConfirmDialog() {

        final AlertDialog alertDialog=new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setCancelable(true);
        alertDialog.setTitle(R.string.warn_fav_clear);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes ..", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });

        return alertDialog;
    }

}
