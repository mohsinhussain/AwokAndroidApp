package com.awok.moshin.awok.NetworkLayer;

/**
 * Created by mohsin on 9/9/2015.
 */
public abstract class AsyncCallback {
    public abstract void onTaskComplete(String response);
    public abstract void onTaskCancelled();
    public abstract void onPreExecute();
}
