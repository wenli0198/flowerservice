package com.test.aoner.fanow.test.bean_flower.user_upload_info_flower;

import com.test.aoner.fanow.test.util_flower.StringUtil_flower;
import com.google.gson.Gson;

public class FeedbackInfo_flower {

    private String type,opinion,images;

    public FeedbackInfo_flower(String type, String opinion, String images) {
        this.type = type;
        this.opinion = opinion;
        this.images = images;
    }

    public String getType() {
        return StringUtil_flower.getSafeString(type);
    }

    public String getOpinion() {
        return StringUtil_flower.getSafeString(opinion);
    }

    public String getImages() {
        return StringUtil_flower.getSafeString(images);
    }

    public String toJsonString(){
        return new Gson().toJson(this);
    }
}
