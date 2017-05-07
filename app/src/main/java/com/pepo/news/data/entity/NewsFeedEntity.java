
package com.pepo.news.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsFeedEntity {

    @SerializedName("ID")
    @Expose
    private int id;

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
    public NewsFeedEntity() {
    }

    /**
     * @param title
     * @param link
     * @param description
     */
    public NewsFeedEntity(int id, String title, String link, String description) {
        super();
        this.id = id;
        this.title = title;
        this.link = link;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

        sb.append("  id: ").append(id).append("\n");
        sb.append("  title: ").append(title).append("\n");
        sb.append("  link: ").append(link).append("\n");
        sb.append("  description: ").append(description).append("\n");
        sb.append("}\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewsFeedEntity)) return false;

        NewsFeedEntity entity = (NewsFeedEntity) o;

        if (getId() != entity.getId()) return false;
        if (!getTitle().equals(entity.getTitle())) return false;
        if (!getLink().equals(entity.getLink())) return false;
        return getDescription().equals(entity.getDescription());

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + getLink().hashCode();
        result = 31 * result + getDescription().hashCode();
        return result;
    }
}
