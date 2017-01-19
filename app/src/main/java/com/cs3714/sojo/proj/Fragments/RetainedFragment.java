package com.cs3714.sojo.proj.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cs3714.sojo.proj.ActivityInTheRFFragmentInterface;
import com.cs3714.sojo.proj.IApiMethods;
import com.cs3714.sojo.proj.Objects.RecipeUrl;
import com.cs3714.sojo.proj.Objects.Result;
import com.cs3714.sojo.proj.Objects.SummaryInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by SOJO on 8/12/15.
 */
public class RetainedFragment extends Fragment {
    private static final String API_URL = "https://webknox-recipes.p.mashape.com";
    private static final String API_KEY = "5Eio4uusntmshqF2l79BIdmY57wtp1oKPeYjsnK0VW1kO5YXxK";
    private TextView textView;
    private TextView result_title;
    private TextView result_summary;
    public volatile RecipeUrl info;
    public SummaryInfo sumInfo;
    public static List<Result> curators;
    private ListView entries;
    private ImageView imgView;
    SharedPreferences pref;
    private Handler handler;
    ActivityInTheRFFragmentInterface activityInterface;
    SharedPreferences.Editor prefEdit;

    List<String> lists;

    String[] strArray;

    public RetainedFragment() {

    }

    /**
     * Hold a reference to the parent Activity so we can report the
     * task's current progress and results. The Android framework
     * will pass us a reference to the newly created Activity after
     * each configuration change.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityInterface = (ActivityInTheRFFragmentInterface) activity;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
        lists = activityInterface.getList();


        Log.d("hw3", "retained fragment received request to refresh");

    }

    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }

    public void runIt(ArrayList<String> strList) {


        strArray = new String[strList.size()];
        strArray = strList.toArray(strArray);
        new BackgroundTask(strArray).execute();


    }

    /**
     * Set the callback to null so we don't accidentally leak the
     * Activity instance.
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }

    private class BackgroundTask extends AsyncTask<Void, Void,
            List<Result>> {
        RestAdapter restAdapter;
        IApiMethods methods;
        String[] listArray;

        public BackgroundTask(String[] strArray) {

            listArray = strArray;
        }

        @Override
        protected void onPreExecute() {
            RequestInterceptor requestInterceptor = new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("X-Mashape-Key", "5Eio4uusntmshqF2l79BIdmY57wtp1oKPeYjsnK0VW1kO5YXxK");
                    request.addHeader("Accept", "application/json");
                }
            };


            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(API_URL)
                    .setRequestInterceptor(requestInterceptor)
                    .build();

        }

        @Override
        protected List<Result> doInBackground(Void... params) {
            methods = restAdapter.create(IApiMethods.class);
            String in = converter(listArray);

            if (curators != null) {
                curators = null;
            }
            curators = methods.getCurators(in, "15");

            for (int i = 0; i < curators.size(); i++) {
                String id_now = curators.get(i).getId();
                info = methods.getRecipeUrl(id_now);
                sumInfo = methods.getSummary(id_now);
                curators.get(i).setRecipeUrl(info.getSourceUrl());
                curators.get(i).setSummary(sumInfo.getSummary());


            }

            //   Log.d("summary", curators.get(2).getSummary());


            return curators;
//Save everything in shared preference.

        }


        @Override
        protected void onPostExecute(List<Result> results) {
            super.onPostExecute(results);
            Log.d("is there one", Integer.toString(results.size()));
            activityInterface.doneProcessing(true);
            Log.d("send result", Integer.toString(results.size()));

            activityInterface.sendResults(results);


        }

        public String converter(String[] strArray) {

            String input = "";
            if (strArray.length > 0) {
                int size = strArray.length - 1;
                for (int i = 0; i < size; i++) {
                    strArray[i] = strArray[i].replace(" ", "+");
                    input += strArray[i] + "%2C";
                }
                strArray[size] = strArray[size].replace(" ", "+");
                input += strArray[size];

            }
            return input;
        }

    }

}
