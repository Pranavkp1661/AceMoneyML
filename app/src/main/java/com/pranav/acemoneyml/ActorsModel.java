package com.pranav.acemoneyml;

import android.os.Parcel;
import android.os.Parcelable;

public class ActorsModel implements Parcelable {
    private String actorName;
    private String imgUrl;
    private String description;

    public ActorsModel(String actorName, String imgUrl, String description) {
        this.actorName = actorName;
        this.imgUrl = imgUrl;
        this.description = description;
    }

    public static final Creator<ActorsModel> CREATOR = new Creator<ActorsModel>() {
        @Override
        public ActorsModel createFromParcel(Parcel in) {
            return new ActorsModel(in);
        }

        @Override
        public ActorsModel[] newArray(int size) {
            return new ActorsModel[size];
        }
    };

    protected ActorsModel(Parcel in) {
        actorName = in.readString();
        imgUrl = in.readString();
        description = in.readString();
    }

    public ActorsModel() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(actorName);
        dest.writeString(imgUrl);
        dest.writeString(description);
    }
}
