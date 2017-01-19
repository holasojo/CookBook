package com.cs3714.sojo.proj;


import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SaveCallback;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();


        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "9b3bEfJK7YSLScX6AK1pavIwNUPzh8xY4GoKSMru", "WiMNdsUFLYzsZCyczbwMVEu8RjuYpUIf4pObmadt");
        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });
    }
}