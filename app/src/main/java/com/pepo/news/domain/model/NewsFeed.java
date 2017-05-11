package com.pepo.news.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by shakti on 5/6/2017.
 */

/**
 * this is a simple POJO class represents the domain layer
 * similar POJO class has also been defined in data  and presentation layer as well.
 * Although in this application there is no difference among NewsFeedEntity from data layer,
 * NewsFeed from
 * domain layer NewsFeedModel from presentation layer. But in ideal case we usually have these
 * three class with slight difference in their stucture as per their uses in their respective layer
 */


public class NewsFeed implements Parcelable {

    @SerializedName("TITLE")
    @Expose
    private String title;

    @SerializedName("LINK")
    @Expose
    private String link;

    @SerializedName("IMAGE_LINK")
    @Expose
    private String imageLink;

    @SerializedName("READ")
    @Expose
    private boolean isRead;

    /**
     * No args constructor for use in serialization
     */
    public NewsFeed() {
    }

    /**
     * @param title
     * @param link
     * @param imageLink
     * @param isRead
     */
    public NewsFeed(String title, String link, String imageLink, boolean isRead) {
        super();
        this.title = title;
        this.link = link;
        this.imageLink = imageLink;
        this.isRead = isRead;
    }

    public NewsFeed(Parcel in) {
        super();
        this.title = in.readString();
        this.link = in.readString();
        this.imageLink = in.readString();
        this.isRead = in.readByte() != 0;

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

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewsFeed)) return false;

        NewsFeed newsFeed = (NewsFeed) o;

        if (!getTitle().equals(newsFeed.getTitle())) return false;
        if (!getLink().equals(newsFeed.getLink())) return false;
        if (!getImageLink().equals(newsFeed.getImageLink())) return false;
        return getIsRead().equals(newsFeed.getIsRead());

    }

    @Override
    public int hashCode() {
        int result = getTitle().hashCode();
        result = 31 * result + getLink().hashCode();
        result = 31 * result + getImageLink().hashCode();
        result = 31 * result + getIsRead().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "NewsFeed{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", read=" + isRead +
                '}';
    }

    public static final Parcelable.Creator<NewsFeed> CREATOR = new Parcelable.Creator<NewsFeed>() {
        public NewsFeed createFromParcel(Parcel in) {
            return new NewsFeed(in);
        }

        public NewsFeed[] newArray(int size) {
            return new NewsFeed[size];
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
        parcel.writeByte((byte) (isRead ? 1 : 0));
    }
}
