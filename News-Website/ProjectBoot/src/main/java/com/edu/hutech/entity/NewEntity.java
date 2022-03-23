package com.edu.hutech.entity;

import javax.persistence.*;

@Entity
@Table(name = "new")
public class NewEntity extends BaseEntity{
    @Column
    private String title;

    @Column
    private String thumbnail;

    @Column(name = "shortdescription")
    private String shortDescription;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categories;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CategoryEntity getCategories() {
        return categories;
    }

    public void setCategories(CategoryEntity categories) {
        this.categories = categories;
    }
}
