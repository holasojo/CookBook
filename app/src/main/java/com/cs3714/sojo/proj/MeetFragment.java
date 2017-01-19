package com.cs3714.sojo.proj;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs3714.sojo.proj.Objects.RecipeData;
import com.squareup.picasso.Picasso;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;


public class MeetFragment extends Fragment {

    SharedPreferences pref;
    RecipeData data;
    private static final String API_URL = "https://webknox-recipes.p.mashape.com";
    TextView textName;
    TextView textIngs;
    ImageView imageRecipe;
    TextView textInst;
    String imageUrl;
    BackgroundTask task;
    TextView text5;
    TextView txt;
    TextView text7;
boolean run = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recipe_page_fragment, container, false);
        Log.d("hello", "in Recipe_Page");
        pref = getActivity().getSharedPreferences("recipe", Context.MODE_PRIVATE);

        textName = (TextView) view.findViewById(R.id.recipeTitle);
        textIngs = (TextView) view.findViewById(R.id.recipeIngs);
        textInst = (TextView) view.findViewById(R.id.recipeInstruction);
        imageRecipe = (ImageView) view.findViewById(R.id.recipeImage);
        text5 = (TextView) view.findViewById(R.id.textView5);
        text5.setVisibility(View.GONE);
        text7 = (TextView) view.findViewById(R.id.textView7);
        text7.setVisibility(View.GONE);
        txt = (TextView) view.findViewById(R.id.textView);



        return view;
    }

    public void startTask(){
        String link2 = pref.getString("recipeUrl","");
        imageUrl = pref.getString("imageUrl","");
       new BackgroundTask().execute(link2);
    }

    private class BackgroundTask extends AsyncTask<String, Void,
            RecipeData> {
        RestAdapter restAdapter;
        IApiMethods methods;

        @Override
        protected void onPreExecute() {
            RequestInterceptor requestInterceptor = new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("X-Mashape-Key", "{key}");
                    request.addHeader("Accept", "application/json");
                }
            };


            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(API_URL)
                    .setRequestInterceptor(requestInterceptor)
                    .build();

        }

        @Override
        protected RecipeData doInBackground(String... params) {
            methods = restAdapter.create(IApiMethods.class);
            String url = params[0];


            data = methods.extractRecipe("false", url);



            return data;
//Save everything in shared preference.

        }


        @Override
        protected void onPostExecute(RecipeData results) {
            super.onPostExecute(results);
            textName.setText(results.getTitle());
            textIngs.setText(results.getAllIngsList());
            textInst.setText(results.getRecipeText());
            text5.setVisibility(View.VISIBLE);
txt.setVisibility(View.GONE);
            text7.setVisibility(View.VISIBLE);
            Picasso.with(imageRecipe.getContext()).load(imageUrl).into(imageRecipe);


        }




    }
}

