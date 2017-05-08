package com.pepo.news.presentation.appviewpresenter.home.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pepo.news.domain.model.NewsFeed;

/**
 * Created by shakti on 5/6/2017.
 */

public class NewsFeedModel  implements Parcelable {
    @SerializedName("TITLE")
    @Expose
    private String title;
    @SerializedName("LINK")
    @Expose
    private String link;
    @SerializedName("IMAGE_LINK")
    @Expose
    private String imageLink;


    /**
     * No args constructor for use in serialization
     */
    public NewsFeedModel() {
    }

    /**
     * @param title
     * @param link
     * @param imageLink
     */
    public NewsFeedModel(String title, String link, String imageLink) {
        super();
        this.title = title;
        this.link = link;
        this.imageLink = imageLink;
    }

    public NewsFeedModel(Parcel in) {
        super();
        this.title = in.readString();
        this.link = in.readString();
        this.imageLink = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NewsFeedEntity {\n");

        sb.append("  title: ").append(title).append("\n");
        sb.append("  link: ").append(link).append("\n");
        sb.append("  imageLink: ").append(imageLink).append("\n");
        sb.append("}\n");
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (title == null ? 0 : title.hashCode());
        result = 31 * result + (link == null ? 0 : link.hashCode());
        result = 31 * result + (imageLink == null ? 0 : imageLink.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof NewsFeedModel) == false) {
            return false;
        }
        NewsFeedModel rhs = ((NewsFeedModel) other);
        return (title == null ? rhs.title == null : title.equals(rhs.title)) &&
                (link == null ? rhs.link == null : link.equals(rhs.link)) &&
                (imageLink == null ? rhs.imageLink == null : imageLink.equals(rhs.imageLink));
    }

    public static final Parcelable.Creator<NewsFeedModel> CREATOR = new Parcelable.Creator<NewsFeedModel>() {
        public NewsFeedModel createFromParcel(Parcel in) {
            return new NewsFeedModel(in);
        }

        public NewsFeedModel[] newArray(int size) {
            return new NewsFeedModel[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(link);
        parcel.writeString(imageLink);
    }

}
