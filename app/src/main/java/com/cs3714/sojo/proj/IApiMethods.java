package com.cs3714.sojo.proj;

import com.cs3714.sojo.proj.Objects.RecipeData;
import com.cs3714.sojo.proj.Objects.RecipeUrl;
import com.cs3714.sojo.proj.Objects.Result;
import com.cs3714.sojo.proj.Objects.SummaryInfo;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;


public interface IApiMethods {

    @GET("/recipes/findByIngredients")
    List<Result> getCurators(
            @Query("ingredients") String ing,
            @Query("number") String num
    );


    @GET("/recipes/{id}/information")
    RecipeUrl getRecipeUrl(
            @Path("id") String id

    );

    @GET("/recipes/{id}/summary")
    SummaryInfo getSummary(
            @Path("id") String id
    );

    @GET("/recipes/extract")
    RecipeData extractRecipe(
            @Query("forceExtraction") String bool,
            @Query("url") String url

    );
}