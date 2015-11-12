package com.awok.moshin.awok.Util;

import android.content.Context;
import android.content.Intent;
import android.provider.SyncStateContract;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by mohsin on 9/9/2015.
 */
public class Utilities {
    public static final String DISPLAY_MESSAGE_ACTION =
            "com.awok.moshin.awok.DISPLAYMESSAGE";
    public static final String TAG = "Utilities";

//    static void displayMessage(Context context, String message) {
//        Intent intent = new Intent();
//        intent.putExtra(SyncStateContract.Constants.GCM_EXTRA_MESSAGE, message);
//        context.sendBroadcast(intent);
//    }

    public static String buildParams(Map<String, Object> params)
    {
        if (params == null)
            return "";
        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> param = iterator.next();
            bodyBuilder.append(param.getKey()).append('=')
                    .append(param.getValue());
            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }
        }
        return bodyBuilder.toString();
    }

}
