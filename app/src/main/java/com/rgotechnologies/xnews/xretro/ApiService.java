package com.rgotechnologies.xnews.xretro;

import com.rgotechnologies.xnews.xmodels.categories.CategoriesResponse;
import com.rgotechnologies.xnews.xmodels.general.GenPostResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("posts?fields=id,date,date_gmt,link,title,content,categories,featured_media,featured_image_url&per_page=100")
    Call<List<GenPostResponse>> getRecentPosts();
    @GET("categories?fields=categoryiconurl,name,id,link,count&per_page=100")
    Call<List<CategoriesResponse>> getCategories();
    @GET("posts")
    Call<List<GenPostResponse>> getAllPostsByCategory(@Query("categories")String categoryId, @Query("per_page")String per_page);
    @GET("posts")
    Call<List<GenPostResponse>> getSearch(@Query("search")String categoryId, @Query("per_page")String per_page);;
}
