
package com.rgotechnologies.xnews.xmodels.general;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Title implements Serializable, Parcelable
{

    @SerializedName("rendered")
    @Expose
    private String rendered;
    public final static Parcelable.Creator<Title> CREATOR = new Creator<Title>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Title createFromParcel(Parcel in) {
            return new Title(in);
        }

        public Title[] newArray(int size) {
            return (new Title[size]);
        }

    }
    ;
    private final static long serialVersionUID = -2190137010473285702L;

    protected Title(Parcel in) {
        this.rendered = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Title() {
    }

    /**
     * 
     * @param rendered
     */
    public Title(String rendered) {
        super();
        this.rendered = rendered;
    }

    public String getRendered() {
        return rendered;
    }

    public void setRendered(String rendered) {
        this.rendered = rendered;
    }

    public Title withRendered(String rendered) {
        this.rendered = rendered;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(rendered);
    }

    public int describeContents() {
        return  0;
    }

}
