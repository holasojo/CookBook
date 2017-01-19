package com.cs3714.sojo.proj.tasks;

import android.util.JsonReader;
import android.util.Log;

import com.cs3714.sojo.proj.Objects.Result;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Andrey on 7/19/2015.
 */
public class RefreshProgressTask implements Runnable {
    private int n = -1;
    private List<Result> entries;

    public String type="progress_task";

    String cookie = "";
    String myurl = "";
    String user = "";

    public RefreshProgressTask(String cookie, String user, String myurl) {
        this.cookie = cookie;
        this.myurl = myurl;
        this.user = user;
    }


    @Override
    public void run() {
        try {
            loadUserProgress(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public final List<Result> getResultFromTask() {

        return entries;
    }

//loads user's progress.
    private Boolean loadUserProgress(String user) throws IOException, JSONException {


        InputStream is = null;

        try {
            HttpURLConnection conn = (HttpURLConnection) ((new URL(myurl + "?username=" + user).openConnection()));
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestProperty("Cookie", cookie);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("GET");
            conn.connect();


            // handling the response
            StringBuilder sb = new StringBuilder();
            int HttpResult = conn.getResponseCode();
            is = conn.getResponseCode() >= 400 ? conn.getErrorStream() : conn.getInputStream();


            //handle response
            JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
            entries = new ArrayList<>();
            reader.beginArray();
            while (reader.hasNext()) {
              //  entries.add(convertToProgress(reader));
            }

            reader.endArray();

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } catch (Exception e) {

            Log.d("vt", " and the exception is " + e);
        } finally {
            if (is != null) {
                is.close();
            }
        }


        return false;
    }

//    private Result convertToProgress(JsonReader reader) throws IOException {
//        String _id = null;
//        String date = "";
//        double miles = -1;
//        int steps = -1;
//        int minutes = -1;
//        double cups = -1;
//
//        reader.beginObject();
//        while (reader.hasNext()) {
//            String name = reader.nextName();
//            if (name.equals("cups")) {
//                cups = reader.nextDouble();
//            } else if (name.equals("miles")) {
//                miles = reader.nextDouble();
//            } else if (name.equals("steps")) {
//                steps = reader.nextInt();
//            } else if (name.equals("duration")) {
//                minutes = reader.nextInt();
//            } else if (name.equals("_id")) {
//                _id = reader.nextString();
//            } else if (name.equals("date")) {
//                date = reader.nextString();
//            }
//
//        }
//        reader.endObject();
//        if (_id != null) {
//
//            return new ProgressEntry(steps, (float) miles, minutes, (float) cups, _id, date);
//        }
//        return null;
//    }


}
