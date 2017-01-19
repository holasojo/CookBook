package com.cs3714.sojo.proj.Objects;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SOJO on 8/13/15.
 */
public class RecipeData {

    String title;
    String servings;
    @SerializedName("extendedIngredients")
    List<ExtendedIngredients> ings = new ArrayList<ExtendedIngredients>();

   public class ExtendedIngredients{
        String originalString;

       public String getOriginalString(){
            return originalString;
        }


    }

   public String text;

    public String getTitle(){
        return title;
    }

    public String getServings(){
        return servings;
    }
    public String getRecipeText(){
        text = text.replace("<ol>","");
        text = text.replace("<li>","\n");
        text = text.replace("</li>","\n");
        text = text.replace("</ol>","");



        return text;
    }
    public List<ExtendedIngredients> getIngsList(){
        return ings;
    }
    public String getAllIngsList(){
        String str="";
        for(int i =0;i<ings.size();i++){
            str += ings.get(i).getOriginalString()+"\n";
        }
        return str;
    }


}
