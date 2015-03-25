package com.maxstow.unofficialtwucampusmapgame;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class DebugIntentService extends IntentService {

    private static final String TAG = "com.maxstow.unofficialtwucampusmapgame";

    public DebugIntentService() {
        super("DebugIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //This is what the service
        Log.i(TAG, "The service has now started");
    }
}
