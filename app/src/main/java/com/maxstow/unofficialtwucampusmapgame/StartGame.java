package com.maxstow.unofficialtwucampusmapgame;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

public class StartGame extends Activity implements ConnectionCallbacks,
        OnConnectionFailedListener, LocationListener {

    // LogCat tag
    private static final String TAG = MainActivity.class.getSimpleName();

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private Location mLastLocation;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;

    private LocationRequest mLocationRequest;

    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 100; // 0.1 sec
    private static int FATEST_INTERVAL = 500; // 0.5 sec
    private static int DISPLACEMENT = 1; // 1 meter

    // UI elements
    private TextView lblLocation;
    private Button btnShowLocation, btnStartLocationUpdates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        lblLocation = (TextView) findViewById(R.id.lblLocation);
        btnShowLocation = (Button) findViewById(R.id.butttonShowLocation);
        btnStartLocationUpdates = (Button) findViewById(R.id.buttonLocationUpdates);



        // First we need to check availability of play services
        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();

            createLocationRequest();
        }

        // Show location button click listener
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                displayLocation();
            }
        });

        // Toggling the periodic location updates
        btnStartLocationUpdates.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                togglePeriodicLocationUpdates();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkPlayServices();

        // Resuming the periodic location updates
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    /**
     * Method to display the location on UI
     */
    private void displayLocation() {

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        LocationManager aLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria c = new Criteria();
        c.setAccuracy(Criteria.ACCURACY_FINE);
        String providerName = aLocationManager.getBestProvider(c, true);

        ArrayList<LocationObject> locationObjectArrayList = populateLocationData();
        LocationObject Canil = locationObjectArrayList.get(0);
        LocationObject Gym = locationObjectArrayList.get(1);

        double canil_longitude = Canil.getLongitudeValue();
        double canil_latitude = Canil.getLatitudeValue();
        String canil_Name = Canil.getBuildingName();


        double latitude;
        double longitude;
        if (mLastLocation != null) {

            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();



            lblLocation.setText(latitude + ", " + longitude);

        } else {

            lblLocation
                    .setText("(Couldn't get the location. Make sure location is enabled on the device)");
        }

    }

    /**
     * Method to toggle periodic location updates
     */
    private void togglePeriodicLocationUpdates() {
        if (!mRequestingLocationUpdates) {
            // Changing the button text
            btnStartLocationUpdates
                    .setText(getString(R.string.btn_stop_location_updates));

            mRequestingLocationUpdates = true;

            // Starting the location updates
            startLocationUpdates();

            Log.d(TAG, "Periodic location updates started!");

        } else {
            // Changing the button text
            btnStartLocationUpdates
                    .setText(getString(R.string.btn_start_location_updates));

            mRequestingLocationUpdates = false;

            // Stopping the location updates
            stopLocationUpdates();

            Log.d(TAG, "Periodic location updates stopped!");
        }
    }

    /**
     * Creating google api client object
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Creating location request object
     */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    /**
     * Method to verify google play services on the device
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    /**
     * Starting the location updates
     */
    protected void startLocationUpdates() {

        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

    }

    /**
     * Stopping location updates
     */
    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        displayLocation();

        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        // Assign the new location
        mLastLocation = location;

        Toast.makeText(getApplicationContext(), "Location changed!",
                Toast.LENGTH_SHORT).show();

        // Displaying the new location on UI
        displayLocation();
    }
    public static ArrayList<LocationObject> populateLocationData() {

        ArrayList<LocationObject> locationArray = new ArrayList<>();

        //In alphabetical order according to string value
        String canil_harvest_centre = String.valueOf((R.string.canil_harvest_centre));
        String gym = String.valueOf(R.string.gym);
        String douglas_centre = String.valueOf(R.string.douglas_centre);
        String douglas_hall = String.valueOf(R.string.douglas_hall);
        String ezra_house = String.valueOf(R.string.ezra_house);
        String fosmark_centre = String.valueOf(R.string.fosmark_centre);
        String fraser_hall = String.valueOf(R.string.fraser_hall);
        String gym_portable = String.valueOf(R.string.gym_portable);
        String atrium = String.valueOf(R.string.atrium);
        String mattson_centre = String.valueOf(R.string.mattson_centre);
        String mcmillan_hall = String.valueOf(R.string.mcmillan_hall);
        String music_building = String.valueOf(R.string.music_building);
        String neufeld_science_centre = String.valueOf(R.string.neufeld_science_centre);
        String northwest_building = String.valueOf(R.string.northwest_building);
        String reimer_student_centre = String.valueOf(R.string.reimer_student_centre);
        String rnt_building = String.valueOf(R.string.rnt_building);
        String robson_hall = String.valueOf(R.string.robson_hall);
        String vernon_strombeck_cetnre = String.valueOf(R.string.vernon_strombeck_cetnre);
        String welcome_centre = String.valueOf(R.string.welcome_centre);

        locationArray.add(new LocationObject(canil_harvest_centre, 49.139901, -122.596444));
        locationArray.add(new LocationObject(gym, 49.141383, -122.597380));
        locationArray.add(new LocationObject(douglas_centre, 49.140162, -122.600496));
        locationArray.add(new LocationObject(douglas_hall, 49.139861, -122.600813));
        locationArray.add(new LocationObject(ezra_house, 49.139995, -122.599627));
        locationArray.add(new LocationObject(fosmark_centre, 49.140201, -122.596666));
        locationArray.add(new LocationObject(fraser_hall, 49.140837, -122.598774));
        locationArray.add(new LocationObject(gym_portable, 49.141040, -122.597825));
        locationArray.add(new LocationObject(atrium, 49.140455, -122.601419));
        locationArray.add(new LocationObject(mattson_centre, 49.141388, -122.600244));
        locationArray.add(new LocationObject(mcmillan_hall, 49.141050, -122.599445));
        locationArray.add(new LocationObject(music_building, 49.139276, -122.603289));
        locationArray.add(new LocationObject(neufeld_science_centre, 49.140030, -122.597380));
        locationArray.add(new LocationObject(northwest_building, 49.140849, -122.595781));
        locationArray.add(new LocationObject(reimer_student_centre, 49.140817, -122.600837));
        locationArray.add(new LocationObject(rnt_building, 49.139777, -122.597380));
        locationArray.add(new LocationObject(robson_hall, 49.140316, -122.599732));
        locationArray.add(new LocationObject(vernon_strombeck_cetnre, 49.139435, -122.605327));
        locationArray.add(new LocationObject(welcome_centre, 49.141827, -122.600987));


        return locationArray;
    }
    public static double calculateDistance(double latitude1, double longitude1, double latitude2, double longitude2, String buildingName) {
        double toRad = (Math.PI/180);
        //double latitude = gps.getLatitude();
        //double longitude = gps.getLongitude();

        //double latitude = 49.1702665;
        //double longitude = -122.6078454;

        double radius = 6371000;
        double lat1 = latitude1 * toRad;
        double lon1 = longitude1 * toRad;

        double lat2 = latitude2 * toRad;
        double lon2 = longitude2 * toRad;

        double deltaLat = (latitude2 - latitude1) * toRad;
        double deltaLon = (longitude2 - longitude1) * toRad;

        double aHaversine = Math.sin(deltaLat/2) * Math.sin(deltaLat/2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(deltaLon/2) * Math.sin(deltaLon/2);

        double cHaversine = 2 * Math.atan2(Math.sqrt(aHaversine), Math.sqrt(1-aHaversine));

        double distanceHaversine = radius * cHaversine;

        return distanceHaversine;
    }
}