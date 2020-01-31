
package com.rgotechnologies.xnews.xmodels.general;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.apache.commons.lang3.builder.EqualsBuilder;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenPostResponse implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("date_gmt")
    @Expose
    private String dateGmt;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("title")
    @Expose
    private Title title;
    @SerializedName("content")
    @Expose
    private Content content;
    @SerializedName("featured_media")
    @Expose
    private long featuredMedia;
    @SerializedName("categories")
    @Expose
    private List<Long> categories = new ArrayList<Long>();
    @SerializedName("featured_image_url")
    @Expose
    private String featuredImageUrl;
    public final static Parcelable.Creator<GenPostResponse> CREATOR = new Creator<GenPostResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public GenPostResponse createFromParcel(Parcel in) {
            return new GenPostResponse(in);
        }

        public GenPostResponse[] newArray(int size) {
            return (new GenPostResponse[size]);
        }

    }
    ;
    private final static long serialVersionUID = 4365433230236660631L;

    protected GenPostResponse(Parcel in) {
        this.id = ((long) in.readValue((long.class.getClassLoader())));
        this.date = ((String) in.readValue((String.class.getClassLoader())));
        this.dateGmt = ((String) in.readValue((String.class.getClassLoader())));
        this.link = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((Title) in.readValue((Title.class.getClassLoader())));
        this.content = ((Content) in.readValue((Content.class.getClassLoader())));
        this.featuredMedia = ((long) in.readValue((long.class.getClassLoader())));
        in.readList(this.categories, (java.lang.Long.class.getClassLoader()));
        this.featuredImageUrl = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public GenPostResponse() {
    }

    /**
     * 
     * @param date
     * @param featuredImageUrl
     * @param dateGmt
     * @param link
     * @param id
     * @param featuredMedia
     * @param categories
     * @param title
     * @param content
     */
    public GenPostResponse(long id, String date, String dateGmt, String link, Title title, Content content, long featuredMedia, List<Long> categories, String featuredImageUrl) {
        super();
        this.id = id;
        this.date = date;
        this.dateGmt = dateGmt;
        this.link = link;
        this.title = title;
        this.content = content;
        this.featuredMedia = featuredMedia;
        this.categories = categories;
        this.featuredImageUrl = featuredImageUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public GenPostResponse withId(long id) {
        this.id = id;
        return this;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public GenPostResponse withDate(String date) {
        this.date = date;
        return this;
    }

    public String getDateGmt() {
        return dateGmt;
    }

    public void setDateGmt(String dateGmt) {
        this.dateGmt = dateGmt;
    }

    public GenPostResponse withDateGmt(String dateGmt) {
        this.dateGmt = dateGmt;
        return this;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public GenPostResponse withLink(String link) {
        this.link = link;
        return this;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public GenPostResponse withTitle(Title title) {
        this.title = title;
        return this;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public GenPostResponse withContent(Content content) {
        this.content = content;
        return this;
    }

    public long getFeaturedMedia() {
        return featuredMedia;
    }

    public void setFeaturedMedia(long featuredMedia) {
        this.featuredMedia = featuredMedia;
    }

    public GenPostResponse withFeaturedMedia(long featuredMedia) {
        this.featuredMedia = featuredMedia;
        return this;
    }

    public List<Long> getCategories() {
        return categories;
    }

    public void setCategories(List<Long> categories) {
        this.categories = categories;
    }

    public GenPostResponse withCategories(List<Long> categories) {
        this.categories = categories;
        return this;
    }

    public String getFeaturedImageUrl() {
        return featuredImageUrl;
    }

    public void setFeaturedImageUrl(String featuredImageUrl) {
        this.featuredImageUrl = featuredImageUrl;
    }

    public GenPostResponse withFeaturedImageUrl(String featuredImageUrl) {
        this.featuredImageUrl = featuredImageUrl;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(date);
        dest.writeValue(dateGmt);
        dest.writeValue(link);
        dest.writeValue(title);
        dest.writeValue(content);
        dest.writeValue(featuredMedia);
        dest.writeList(categories);
        dest.writeValue(featuredImageUrl);
    }

    public int describeContents() {
        return  0;
    }
    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof GenPostResponse))
            return false;
        if (obj == this)
            return true;
        GenPostResponse rhs = (GenPostResponse) obj;
        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                        append(getId(), rhs.getId()).
                        isEquals();
    }
}
