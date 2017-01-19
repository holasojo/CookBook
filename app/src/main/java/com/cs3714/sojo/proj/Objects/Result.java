package com.cs3714.sojo.proj.Objects;

import java.io.Serializable;

/**
 * Created by SOJO on 8/11/15.
 */
public class Result implements Serializable {


    private String title;
    private String image;
    private String id;
    private int usedIngredientCount;
    private int missedIngredientCount;
    private int likes;
    private String recipeUrl;
    private String summary;

    public Result() {
    }

    public String getId() {
        return id;
    }

    public int getUsedIngredientCount() {
        return usedIngredientCount;
    }

    public int getMissedIngredientCount() {
        return missedIngredientCount;
    }

    public int getLikes() {
        return likes;
    }

    public String getTitle() {
        return title;
    }

    public String getImg_url() {
        return image;
    }

    public void setRecipeUrl(String url) {
        this.recipeUrl = url;
    }

    public void setSummary(String summary) {
        summary = summary.replace("<b>","");
        summary = summary.replace("</b>","");

        this.summary = summary;
    }
    public String getRecipeUrl(){
        return recipeUrl;
    }
    public String getSummary(){

        return summary;
    }

}
