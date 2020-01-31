package com.rgotechnologies.xnews.xlibs;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
import com.rgotechnologies.xnews.xmodels.general.GenPostResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XSharedPreferences {public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "GenPostResponse_Favorite";

    public XSharedPreferences() {
        super();
    }
    public void saveFavorites(Context context, List<GenPostResponse> favorites) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);
        editor.apply();
        Log.d("mytag","saveFavorites:: ");
    }

    public void addFavorite(Context context, GenPostResponse posts) {
        List<GenPostResponse> favorites = getFavorites(context);
        if (favorites == null) {

            favorites = new ArrayList<GenPostResponse>();
        }
        else{
            if (favorites.contains(posts)) {
                Toast.makeText(context, "Already Liked this", Toast.LENGTH_SHORT).show();
            } else {
                favorites.add(posts);
                saveFavorites(context, favorites);
            }
        }


    }
    public void removeFavorite(Context context, GenPostResponse posts) {
        Log.d("mytag","Inside removeFavorite :: ");
        ArrayList<GenPostResponse> favorites = null;
        try {
            favorites = getFavorites(context);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("mytag"," Exception Inside removeFavorite ::getFavorites ");
        }
        Log.d("mytag","Inside removeFavorite :: step 1");
        Log.d("mytag","Inside removeFavorite :: Size"+favorites.size());
        Log.d("mytag","Inside removeFavorite :: ID "+favorites.get(0).getId());
        Log.d("mytag","Inside removeFavorite :: ID "+posts.getId());
        if (favorites != null) {
            if(favorites.contains(posts))
            {
                favorites.remove(posts);
                saveFavorites(context, favorites);
                Log.d("mytag","removeFavorite::called ");
            }else{
                Log.d("mytag","removeFavorite:: Contains False ");
            }
        }else{
            Log.d("mytag","removeFavorite ArrayList NULL:: ");
        }
    }
    public ArrayList<GenPostResponse> getFavorites(Context context) {
        Log.d("mytag","Inside getFavorites :: ");
        SharedPreferences settings;
        List<GenPostResponse> favorites;
        List<GenPostResponse> apiList;
        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            Log.d("mytag","Inside getFavorites :: 1");
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            GenPostResponse[] favoriteItems = gson.fromJson(jsonFavorites,
                    GenPostResponse[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<GenPostResponse>(favorites);
        } else
        {
            apiList=new ArrayList<>();
            favorites=apiList;
            Log.d("mytag","Inside getFavorites :: contains False");
        }

        return (ArrayList<GenPostResponse>) favorites;
    }

    public  void saveAuthStatus(Context context,boolean result){
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putBoolean("authstatus",result);
        editor.commit();
        editor.apply();
    }
    public  boolean getAuthStatus(Context context){
        SharedPreferences settings;
        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        return settings.getBoolean("authstatus",false);

    }

    public void clearAllFavourites(Context context) {
        SharedPreferences preferences;
        SharedPreferences.Editor editor;

        preferences = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.remove(FAVORITES);
        editor.commit();
        editor.apply();
    }
}
