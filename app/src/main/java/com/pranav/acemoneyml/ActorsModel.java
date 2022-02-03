package com.pranav.acemoneyml;

public class ActorsModel {
    private String actorName;
    private String imgUrl;
    private String description;

    public ActorsModel(String actorName, String imgUrl, String description) {
        this.actorName = actorName;
        this.imgUrl = imgUrl;
        this.description = description;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
