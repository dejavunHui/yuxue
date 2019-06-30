package com.example.androidmvp.mvp.entity.weather;

import com.example.androidmvp.mvp.entity.show.ImageResult;

public final class WealthBackgroundResult extends ImageResult {
    private int width;
    private int height;
    private String wealth;
    private String color;
    private String jijie;
    private String theme;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getWealth() {
        return wealth;
    }

    public void setWealth(String wealth) {
        this.wealth = wealth;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getJijie() {
        return jijie;
    }

    public void setJijie(String jijie) {
        this.jijie = jijie;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
