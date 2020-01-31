
package com.rgotechnologies.xnews.xmodels.categories;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoriesResponse implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("count")
    @Expose
    private long count;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("categoryiconurl")
    @Expose
    private String categoryiconurl;
    public final static Parcelable.Creator<CategoriesResponse> CREATOR = new Creator<CategoriesResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CategoriesResponse createFromParcel(Parcel in) {
            return new CategoriesResponse(in);
        }

        public CategoriesResponse[] newArray(int size) {
            return (new CategoriesResponse[size]);
        }

    }
    ;
    private final static long serialVersionUID = -4743827268364117926L;

    protected CategoriesResponse(Parcel in) {
        this.id = ((long) in.readValue((long.class.getClassLoader())));
        this.count = ((long) in.readValue((long.class.getClassLoader())));
        this.link = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.categoryiconurl = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public CategoriesResponse() {
    }

    /**
     * 
     * @param categoryiconurl
     * @param count
     * @param link
     * @param name
     * @param id
     */
    public CategoriesResponse(long id, long count, String link, String name, String categoryiconurl) {
        super();
        this.id = id;
        this.count = count;
        this.link = link;
        this.name = name;
        this.categoryiconurl = categoryiconurl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CategoriesResponse withId(long id) {
        this.id = id;
        return this;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public CategoriesResponse withCount(long count) {
        this.count = count;
        return this;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public CategoriesResponse withLink(String link) {
        this.link = link;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoriesResponse withName(String name) {
        this.name = name;
        return this;
    }

    public String getCategoryiconurl() {
        return categoryiconurl;
    }

    public void setCategoryiconurl(String categoryiconurl) {
        this.categoryiconurl = categoryiconurl;
    }

    public CategoriesResponse withCategoryiconurl(String categoryiconurl) {
        this.categoryiconurl = categoryiconurl;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(count);
        dest.writeValue(link);
        dest.writeValue(name);
        dest.writeValue(categoryiconurl);
    }

    public int describeContents() {
        return  0;
    }

}
