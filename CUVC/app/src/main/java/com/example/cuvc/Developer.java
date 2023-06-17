package com.example.cuvc;

public class Developer {
    private String name;
    private String description;
    private int imageResId;
    private String twitterLink;
    private String linkedinLink;

    public Developer(String name, String description, int imageResId, String twitterLink, String linkedinLink) {
        this.name = name;
        this.description = description;
        this.imageResId = imageResId;
        this.twitterLink = twitterLink;
        this.linkedinLink = linkedinLink;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getTwitterLink() {
        return twitterLink;
    }

    public String getLinkedinLink() {
        return linkedinLink;
    }
}
