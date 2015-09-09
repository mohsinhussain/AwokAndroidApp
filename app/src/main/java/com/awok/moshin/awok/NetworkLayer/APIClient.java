package com.awok.moshin.awok.NetworkLayer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.SyncStateContract;
import android.util.Log;

import com.awok.moshin.awok.Util.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mohsin on 9/9/2015.
 */
public class APIClient {
    private String TAG = "APIClient";
    private AsyncTaskWithDialog mTask;
    private Context mContext;
    private Activity mActivity;
    private AsyncCallback mCallback;
    private int CONTEXT_INDEX = 0;
    private int URI_INDEX = 1;
    private int METHOD_INDEX = 2;
    private int PARAMS_INDEX = 3;

    public APIClient(Activity activity, Context context, AsyncCallback callback) {
        mContext = context;
        mActivity = activity;
        mCallback = callback;

    }

    public void testAPICall() {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, Constants.API_SERVER_URL, "GET", null);
    }



    public class AsyncTaskWithDialog extends AsyncTask<Object, Void, String> {

        @Override
        protected void onPreExecute() {
            mCallback.onPreExecute();
        }

        @SuppressWarnings("unchecked")
        @Override
        protected String doInBackground(Object... parameters) {
            String postResponse = "";
            try {
                Context context = (Context) parameters[CONTEXT_INDEX];
                String url = (String) parameters[URI_INDEX];
                HTTPClient client = new HTTPClient(
                        context.getSharedPreferences(Constants.PREFS_NAME, 0));

                Map<String, String> params = null;
                if (parameters[PARAMS_INDEX] instanceof HashMap) {
                    params = (HashMap<String, String>) parameters[PARAMS_INDEX];

                }

                if (parameters[METHOD_INDEX] == "POST") {
                    if (parameters[PARAMS_INDEX] instanceof HashMap) {
                        postResponse = client.post(url, params);

                    }
                    return postResponse;

                } else if (parameters[METHOD_INDEX] == "GET")
                    return client.get(url, params);
                else if (parameters[METHOD_INDEX] == "PUT")
                    return client.put(url, params);
                else if (parameters[METHOD_INDEX] == "DELETE")
                    return client.delete(url, params);

                return Constants.Success.toString();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "EXCEPTION: " + e.toString());
            }
            return Constants.Exception.toString();
        }

        @Override
        protected void onPostExecute(final String response) {
            mTask = null;
            mCallback.onTaskComplete(response);
        }

        @Override
        protected void onCancelled() {
            mTask = null;
            mCallback.onTaskCancelled();
        }
    }

}
