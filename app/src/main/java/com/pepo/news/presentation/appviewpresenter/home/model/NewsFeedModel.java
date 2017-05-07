package com.pepo.news.presentation.appviewpresenter.home.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pepo.news.domain.model.NewsFeed;

/**
 * Created by shakti on 5/6/2017.
 */

public class NewsFeedModel {
    @SerializedName("TITLE")
    @Expose
    private String title;
    @SerializedName("LINK")
    @Expose
    private String link;
    @SerializedName("DESCRIPTION")
    @Expose
    private String description;


    /**
     * No args constructor for use in serialization
     */
    public NewsFeedModel() {
    }

    /**
     * @param title
     * @param link
     * @param description
     */
    public NewsFeedModel(String title, String link, String description) {
        super();
        this.title = title;
        this.link = link;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NewsFeedEntity {\n");

        sb.append("  title: ").append(title).append("\n");
        sb.append("  link: ").append(link).append("\n");
        sb.append("  description: ").append(description).append("\n");
        sb.append("}\n");
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (title == null ? 0 : title.hashCode());
        result = 31 * result + (link == null ? 0 : link.hashCode());
        result = 31 * result + (description == null ? 0 : description.hashCode());
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
                (description == null ? rhs.description == null : description.equals(rhs.description));
    }

}
