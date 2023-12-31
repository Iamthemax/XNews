
package com.rgotechnologies.xnews.xmodels.general;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content implements Serializable, Parcelable
{

    @SerializedName("rendered")
    @Expose
    private String rendered;
    @SerializedName("protected")
    @Expose
    private boolean _protected;
    public final static Parcelable.Creator<Content> CREATOR = new Creator<Content>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Content createFromParcel(Parcel in) {
            return new Content(in);
        }

        public Content[] newArray(int size) {
            return (new Content[size]);
        }

    }
    ;
    private final static long serialVersionUID = 6484256843780391957L;

    protected Content(Parcel in) {
        this.rendered = ((String) in.readValue((String.class.getClassLoader())));
        this._protected = ((boolean) in.readValue((boolean.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Content() {
    }

    /**
     * 
     * @param rendered
     * @param _protected
     */
    public Content(String rendered, boolean _protected) {
        super();
        this.rendered = rendered;
        this._protected = _protected;
    }

    public String getRendered() {
        return rendered;
    }

    public void setRendered(String rendered) {
        this.rendered = rendered;
    }

    public Content withRendered(String rendered) {
        this.rendered = rendered;
        return this;
    }

    public boolean isProtected() {
        return _protected;
    }

    public void setProtected(boolean _protected) {
        this._protected = _protected;
    }

    public Content withProtected(boolean _protected) {
        this._protected = _protected;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(rendered);
        dest.writeValue(_protected);
    }

    public int describeContents() {
        return  0;
    }

}
