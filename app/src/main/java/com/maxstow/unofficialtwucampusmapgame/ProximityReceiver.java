package com.maxstow.unofficialtwucampusmapgame;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import static android.support.v4.app.ActivityCompat.startActivity;

public class ProximityReceiver extends BroadcastReceiver {

    private static final String TAG = "ProximityReceiver";
    private final Context context;

    public ProximityReceiver(Context context) {
        this.context = context;
    }

    @Override
    public void onReceive(Context arg0, Intent intent) {
        Log.v(TAG, "Got intent");

        if(intent.getData() != null) {
            Log.v(TAG, intent.getData().toString());
        }

        Bundle extras = intent.getExtras();
        boolean entering = extras.getBoolean(LocationManager.KEY_PROXIMITY_ENTERING);

        if (extras != null) {
            if( entering == true) {
                Intent proximityIntent;
                proximityIntent = new Intent(context, FieldTrip.class);
                context.startActivity(proximityIntent);

            }
            Log.v(TAG, "Message: " +
                    extras.get("message"));
            Log.v(TAG, "Entering" +
                    extras.getBoolean(LocationManager.KEY_PROXIMITY_ENTERING));

        }
    }

}