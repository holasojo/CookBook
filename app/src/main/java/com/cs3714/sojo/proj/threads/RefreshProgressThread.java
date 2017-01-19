package com.cs3714.sojo.proj.threads;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.cs3714.sojo.proj.Objects.Result;
import com.cs3714.sojo.proj.tasks.*;
import com.cs3714.sojo.proj.RfragmentInThreadInterface;



import java.util.List;

/**
 * Created by Andrey on 7/19/2015.
 */
public class RefreshProgressThread extends Thread {

    private Handler handler;
    private RfragmentInThreadInterface rf;
    private final String ID;
    private List<Result> result;

    public RefreshProgressThread(RfragmentInThreadInterface rf, String ID) {
        this.rf = rf;
        this.ID = ID;

    }
    @Override
    public void run(){

        try {
            Looper.prepare();

            handler = new Handler();

            Looper.loop();
        }catch (Throwable t) {
            Log.e("hw3", "RefreshProgressThread halted due to an error", t);
        }

    }

    public synchronized void requestStop() {

        handler.post(new Runnable() {
            @Override
            public void run() {

                Log.i("hw3", "RefreshProgressThread loop quitting by request" + ID);

                Looper.myLooper().quit();
            }
        });
    }

    public synchronized void enqueueRefreshProgressTask(final RefreshProgressTask task){

        handler.post(new Runnable() {


            @Override
            public void run() {

                try {
                    task.run();
                    result = task.getResultFromTask();
                }catch(Exception e){

                    Log.e("RefreshProgressThread", "task execution error");
                }
                notifyRetainedFragment();
            }
        });

    }

    private void notifyRetainedFragment(){
        rf.progressUpdated(result);
    }
}
