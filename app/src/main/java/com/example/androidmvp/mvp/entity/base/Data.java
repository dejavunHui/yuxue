package com.example.androidmvp.mvp.entity.base;

import java.util.Date;
import java.util.List;

public class Data {
    private String id;
    private String content;
    private int popularity;
    private Origin origin;
    private List<String> matchTags;
    private String recommendedReason;
    private String cacheAt;
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
    public int getPopularity() {
        return popularity;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }
    public Origin getOrigin() {
        return origin;
    }

    public void setMatchTags(List<String> matchTags) {
        this.matchTags = matchTags;
    }
    public List<String> getMatchTags() {
        return matchTags;
    }

    public void setRecommendedReason(String recommendedReason) {
        this.recommendedReason = recommendedReason;
    }
    public String getRecommendedReason() {
        return recommendedReason;
    }

    public String getCacheAt() {
        return cacheAt;
    }

    public void setCacheAt(String cacheAt) {
        this.cacheAt = cacheAt;
    }
}
