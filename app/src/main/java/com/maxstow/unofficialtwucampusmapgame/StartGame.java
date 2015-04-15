package com.maxstow.unofficialtwucampusmapgame;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class StartGame extends Activity implements ConnectionCallbacks,
        OnConnectionFailedListener, LocationListener {

    //Proximity Alert

    private final String PROX_ALERT =
            "com.maxstow.unofficialtwucampusmapgame.intent.action.PROXIMITY_ALERT";
    private ProximityReceiver proxReceiver = null;
    private LocationManager locMgr = null;
    PendingIntent pIntent1 = null;
    PendingIntent pIntent2 = null;
    PendingIntent pIntent3 = null;
    PendingIntent pIntent4 = null;
    PendingIntent pIntent5 = null;
    PendingIntent pIntent6 = null;
    PendingIntent pIntent7 = null;
    PendingIntent pIntent8 = null;
    PendingIntent pIntent9 = null;
    PendingIntent pIntent10 = null;
    PendingIntent pIntent11 = null;
    PendingIntent pIntent12 = null;
    PendingIntent pIntent13 = null;
    PendingIntent pIntent14 = null;
    PendingIntent pIntent15 = null;
    PendingIntent pIntent16 = null;
    PendingIntent pIntent17 = null;
    PendingIntent pIntent18 = null;
    PendingIntent pIntent19 = null;

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

    //Distances
    private TextView distance1, distance2, distance3, distance4,
            distance5, distance6, distance7, distance8, distance9,
            distance10, distance11, distance12, distance13, distance14,
            distance15, distance16, distance17, distance18, distance19;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        lblLocation = (TextView) findViewById(R.id.lblLocation);
        btnShowLocation = (Button) findViewById(R.id.butttonShowLocation);
        btnStartLocationUpdates = (Button) findViewById(R.id.buttonLocationUpdates);

        //Add prox alerts
        addAlerts();

        distance1 = (TextView) findViewById(R.id.distance1);
        distance2 = (TextView) findViewById(R.id.distance2);
        distance3 = (TextView) findViewById(R.id.distance3);
        distance4 = (TextView) findViewById(R.id.distance4);
        distance5 = (TextView) findViewById(R.id.distance5);
        distance6 = (TextView) findViewById(R.id.distance6);
        distance7 = (TextView) findViewById(R.id.distance7);
        distance8 = (TextView) findViewById(R.id.distance8);
        distance9 = (TextView) findViewById(R.id.distance9);
        distance10 = (TextView) findViewById(R.id.distance10);
        distance11 = (TextView) findViewById(R.id.distance11);
        distance12 = (TextView) findViewById(R.id.distance12);
        distance13 = (TextView) findViewById(R.id.distance13);
        distance14 = (TextView) findViewById(R.id.distance14);
        distance15 = (TextView) findViewById(R.id.distance15);
        distance16 = (TextView) findViewById(R.id.distance16);
        distance17 = (TextView) findViewById(R.id.distance17);
        distance18 = (TextView) findViewById(R.id.distance18);
        distance19 = (TextView) findViewById(R.id.distance19);

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
        LocationObject canil_harvest_centre = locationObjectArrayList.get(0);
        LocationObject gym = locationObjectArrayList.get(1);
        LocationObject douglas_centre = locationObjectArrayList.get(2);
        LocationObject douglas_hall = locationObjectArrayList.get(3);
        LocationObject ezra_house = locationObjectArrayList.get(4);
        LocationObject fosmark_centre = locationObjectArrayList.get(5);
        LocationObject fraser_hall = locationObjectArrayList.get(6);
        LocationObject gym_portable = locationObjectArrayList.get(7);
        LocationObject atrium = locationObjectArrayList.get(8);
        LocationObject mattson_centre = locationObjectArrayList.get(9);
        LocationObject mcmillan_hall = locationObjectArrayList.get(10);
        LocationObject music_building = locationObjectArrayList.get(11);
        LocationObject neufeld_science_centre = locationObjectArrayList.get(12);
        LocationObject northwest_building = locationObjectArrayList.get(13);
        LocationObject reimer_student_centre = locationObjectArrayList.get(14);
        LocationObject rnt_building = locationObjectArrayList.get(15);
        LocationObject robson_hall = locationObjectArrayList.get(16);
        LocationObject vernon_strombeck_cetnre = locationObjectArrayList.get(17);
        LocationObject welcome_centre = locationObjectArrayList.get(18);

        double canil_harvest_centreLongitudeValue = canil_harvest_centre.getLongitudeValue();
        double canil_harvest_centreLatitudeValue = canil_harvest_centre.getLatitudeValue();
        String canil_harvest_centreBuildingName = "CANIL Harbest Centre";

        double gymLongitudeValue = gym.getLongitudeValue();
        double gymLatitudeValue = gym.getLatitudeValue();
        String gymBuildingName = "David E. Enarson Gym";

        double douglas_centreLongitudeValue = douglas_centre.getLongitudeValue();
        double douglas_centreLatitudeValue = douglas_centre.getLatitudeValue();
        String douglas_centreBuildingName = "Douglas Centre";

        double douglas_hallLongitudeValue = douglas_hall.getLongitudeValue();
        double douglas_hallLatitudeValue = douglas_hall.getLatitudeValue();
        String douglas_hallBuildingName = "Douglas Hall";

        double ezra_houseLongitudeValue = ezra_house.getLongitudeValue();
        double ezra_houseLatitudeValue = ezra_house.getLatitudeValue();
        String ezra_houseBuildingName = "Ezra House";

        double fosmark_centreLongitudeValue = fosmark_centre.getLongitudeValue();
        double fosmark_centreLatitudeValue = fraser_hall.getLatitudeValue();
        String fosmark_centreBuildingName = "Fosmark Centre";

        double fraser_hallLongitudeValue = fraser_hall.getLongitudeValue();
        double fraser_hallLatitudeValue = fraser_hall.getLatitudeValue();
        String fraser_hallBuildingName = "Fraser Hall";

        double gym_portableLongitudeValue = gym_portable.getLongitudeValue();
        double gym_portableLatitudeValue = gym_portable.getLatitudeValue();
        String gym_portableBuildingName = "Gym Portable";

        double atriumLongitudeValue = atrium.getLongitudeValue();
        double atriumLatitudeValue = atrium.getLatitudeValue();
        String atriumBuildingName = "Larsen Atrium";

        double mattson_centreLongitudeValue = mattson_centre.getLongitudeValue();
        double mattson_centreLatitudeValue = mattson_centre.getLatitudeValue();
        String mattson_centreBuildingName = "Mattson Centre";

        double mcmillan_hallLongitudeValue = mcmillan_hall.getLongitudeValue();
        double mcmillan_hallLatitudeValue = mcmillan_hall.getLatitudeValue();
        String mcmillan_hallBuildingName = "McMillan Hall";

        double music_buildingLongitudeValue = music_building.getLongitudeValue();
        double music_buildingLatitudeValue = music_building.getLatitudeValue();
        String music_buildingBuildingName = "Music Building";

        double neufeld_science_centreLongitudeValue = neufeld_science_centre.getLongitudeValue();
        double neufeld_science_centreLatitudeValue = neufeld_science_centre.getLatitudeValue();
        String neufeld_science_centreBuildingName = "Nefueld Science Centre";

        double northwest_buildingLongitudeValue = northwest_building.getLongitudeValue();
        double northwest_buildingLatitudeValue = northwest_building.getLatitudeValue();
        String northwest_buildingBuildingName = "Northwest Building";

        double reimer_student_centreLongitudeValue = reimer_student_centre.getLongitudeValue();
        double reimer_student_centreLatitudeValue = reimer_student_centre.getLatitudeValue();
        String reimer_student_centreBuildingName = "Reimer Student Centre";

        double rnt_buildingLongitudeValue = rnt_building.getLongitudeValue();
        double rnt_buildingLatitudeValue = rnt_building.getLatitudeValue();
        String rnt_buildingBuildingName = "Robert N. Thompson Building";

        double robson_hallLongitudeValue = robson_hall.getLongitudeValue();
        double robson_hallLatitudeValue = robson_hall.getLatitudeValue();
        String robson_hallBuildingName = "Robson Hall";

        double vernon_strombeck_cetnreLongitudeValue = vernon_strombeck_cetnre.getLongitudeValue();
        double vernon_strombeck_cetnreLatitudeValue = vernon_strombeck_cetnre.getLatitudeValue();
        String vernon_strombeck_cetnreBuildingName = "Vernon Strombeck Centre";

        double welcome_centreLongitudeValue = welcome_centre.getLongitudeValue();
        double welcome_centreLatitudeValue = welcome_centre.getLatitudeValue();
        String welcome_centreBuildingName = "Welcome Centre";

        double latitude;
        double longitude;
        if (mLastLocation != null) {

            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();

            double distanceTocanil_harvest_centre = calculateDistance( latitude, longitude, canil_harvest_centreLatitudeValue, canil_harvest_centreLongitudeValue);
            String distanceTocanil_harvest_centreString = canil_harvest_centreBuildingName + ": " + round(distanceTocanil_harvest_centre, 2) + " meters" ;

            distance1.setText(distanceTocanil_harvest_centreString);


            double distanceTogym = calculateDistance( latitude, longitude, gymLatitudeValue, gymLongitudeValue);
            String distanceTogymString = gymBuildingName + ": " + round(distanceTogym, 2) + " meters" ;

            distance2.setText(distanceTogymString);


            double distanceTodouglas_centre = calculateDistance( latitude, longitude, douglas_centreLatitudeValue, douglas_centreLongitudeValue);
            String distanceTodouglas_centreString = douglas_centreBuildingName + ": " + round(distanceTodouglas_centre, 2) + " meters" ;

            distance3.setText(distanceTodouglas_centreString);


            double distanceTodouglas_hall = calculateDistance( latitude, longitude, douglas_hallLatitudeValue, douglas_hallLongitudeValue);
            String distanceTodouglas_hallString = douglas_hallBuildingName + ": " + round(distanceTodouglas_hall, 2) + " meters" ;

            distance4.setText(distanceTodouglas_hallString);


            double distanceToezra_house = calculateDistance( latitude, longitude, ezra_houseLatitudeValue, ezra_houseLongitudeValue);
            String distanceToezra_houseString = ezra_houseBuildingName + ": " + round(distanceToezra_house, 2) + " meters" ;

            distance5.setText(distanceToezra_houseString);


            double distanceTofosamrk_centre = calculateDistance( latitude, longitude, fosmark_centreLatitudeValue, fosmark_centreLongitudeValue);
            String distanceTofosmark_centreString = fosmark_centreBuildingName + ": " + round(distanceTofosamrk_centre, 2) + " meters" ;

            distance6.setText(distanceTofosmark_centreString);

            double distanceTofraser_hall = calculateDistance( latitude, longitude, fraser_hallLatitudeValue, fraser_hallLongitudeValue);
            String distanceTofraser_hallString = fraser_hallBuildingName + ": " + round(distanceTofraser_hall, 2) + " meters" ;

            distance7.setText(distanceTofraser_hallString);


            double distanceTogym_portable = calculateDistance( latitude, longitude, gym_portableLatitudeValue, gym_portableLongitudeValue);
            String distanceTogym_portableString = gym_portableBuildingName + ": " + round(distanceTogym_portable, 2) + " meters" ;

            distance8.setText(distanceTogym_portableString);


            double distanceToatrium = calculateDistance( latitude, longitude, atriumLatitudeValue, atriumLongitudeValue);
            String distanceToatriumString = atriumBuildingName + ": " + round(distanceToatrium, 2) + " meters" ;

            distance9.setText(distanceToatriumString);


            double distanceTomattson_centre = calculateDistance( latitude, longitude, mattson_centreLatitudeValue, mattson_centreLongitudeValue);
            String distanceTomattson_centreString = mattson_centreBuildingName + ": " + round(distanceTomattson_centre, 2) + " meters" ;

            distance10.setText(distanceTomattson_centreString);


            double distanceTomcmillan_hall = calculateDistance( latitude, longitude, mcmillan_hallLatitudeValue, mcmillan_hallLongitudeValue);
            String distanceTomcmillan_hallString = mcmillan_hallBuildingName + ": " + round(distanceTomcmillan_hall, 2) + " meters" ;

            distance11.setText(distanceTomcmillan_hallString);


            double distanceTomusic_building = calculateDistance( latitude, longitude, music_buildingLatitudeValue, music_buildingLongitudeValue);
            String distanceTomusic_buildingString = music_buildingBuildingName + ": " + round(distanceTomusic_building, 2) + " meters" ;

            distance12.setText(distanceTomusic_buildingString);


            double distanceToneufeld_science_centre = calculateDistance( latitude, longitude, neufeld_science_centreLatitudeValue, neufeld_science_centreLongitudeValue);
            String distanceToneufeld_science_centreString = neufeld_science_centreBuildingName + ": " + round(distanceToneufeld_science_centre, 2) + " meters";

            distance13.setText(distanceToneufeld_science_centreString);


            double distanceTonorthwest_building = calculateDistance( latitude, longitude, northwest_buildingLatitudeValue, northwest_buildingLongitudeValue);
            String distanceTonorthwest_buildingString = northwest_buildingBuildingName + ": " + round(distanceTonorthwest_building, 2) + " meters" ;

            distance14.setText(distanceTonorthwest_buildingString);


            double distanceToreimer_student_centre = calculateDistance( latitude, longitude, reimer_student_centreLatitudeValue, reimer_student_centreLongitudeValue);
            String distanceToreimer_student_centreString = reimer_student_centreBuildingName + ": " + round(distanceToreimer_student_centre, 2) + " meters" ;

            distance15.setText(distanceToreimer_student_centreString);


            double distanceTornt_building = calculateDistance( latitude, longitude, rnt_buildingLatitudeValue, rnt_buildingLongitudeValue);
            String distanceTornt_buildingString = rnt_buildingBuildingName + ": " + round(distanceTornt_building, 2) + " meters" ;

            distance16.setText(distanceTornt_buildingString);


            double distanceTorobson_hall = calculateDistance( latitude, longitude, robson_hallLatitudeValue, robson_hallLongitudeValue);
            String distanceTorobson_hallString = robson_hallBuildingName + ": " + round(distanceTorobson_hall, 2) + " meters" ;

            distance17.setText(distanceTorobson_hallString);


            double distanceTovernon_strombeck_cetnre = calculateDistance( latitude, longitude, vernon_strombeck_cetnreLatitudeValue, vernon_strombeck_cetnreLongitudeValue);
            String distanceTovernon_strombeck_cetnreString = vernon_strombeck_cetnreBuildingName + ": " + round(distanceTovernon_strombeck_cetnre, 2) + " meters" ;

            distance18.setText(distanceTovernon_strombeck_cetnreString);


            double distanceTowelcome_centre = calculateDistance( latitude, longitude, welcome_centreLatitudeValue, welcome_centreLongitudeValue);
            String distanceTowelcome_centreString = welcome_centreBuildingName + ": " + round(distanceTowelcome_centre, 2) + " meters" ;

            distance19.setText(distanceTowelcome_centreString);

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

    /**
     * A method to implement the Haversine formula from the site: http://www.movable-type.co.uk/scripts/latlong.html
     * @param latitude1
     * @param longitude1
     * @param latitude2
     * @param longitude2
     * @return
     */
    public static double calculateDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        double toRad = (Math.PI/180);

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

    /**
     * For rounding decimal values
     * found on StackOverflow: link http://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
     * @param value
     * @param places
     * @return
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * For the setupProxAlert method
     * @param lat //destination latitude
     * @param lon //destination longitude
     * @return
     */
    public static String setGeo(double lat, double lon) {
        String geo = "geo:" + lat + "," + lon;
        return geo;
    }

    /**
     * setup with the first proximity alert
     * @param lat
     * @param lon
     * @param radius
     * @param message
     */
    public void setupProxAlert1(double lat, double lon, float radius, String message) {
        //Proximity Code

        locMgr = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        String geo = setGeo(lat, lon);

        Intent intent = new Intent(PROX_ALERT, Uri.parse(geo));
        intent.putExtra("message", message);
        pIntent1 =
                PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        locMgr.addProximityAlert(lat,lon, radius, -1L, pIntent1);
    }

    /**
     * setup the second proximity alert
     * @param lat
     * @param lon
     * @param radius
     * @param message
     */
    public void setupProxAlert2(double lat, double lon, float radius, String message) {
        //Proximity Code

        locMgr = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        String geo = setGeo(lat, lon);

        Intent intent = new Intent(PROX_ALERT, Uri.parse(geo));
        intent.putExtra("message", message);
        pIntent2 =
                PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        locMgr.addProximityAlert(lat,lon, radius, -1L, pIntent2);
    }

    /**
     * setup the third proximity alert
     * @param lat
     * @param lon
     * @param radius
     * @param message
     */
    public void setupProxAlert3(double lat, double lon, float radius, String message) {
        //Proximity Code

        locMgr = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        String geo = setGeo(lat, lon);

        Intent intent = new Intent(PROX_ALERT, Uri.parse(geo));
        intent.putExtra("message", message);
        pIntent3 =
                PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        locMgr.addProximityAlert(lat,lon, radius, -1L, pIntent3);
    }

    /**
     * setup the fourth proximity alert
     * @param lat
     * @param lon
     * @param radius
     * @param message
     */
    public void setupProxAlert4(double lat, double lon, float radius, String message) {
        //Proximity Code

        locMgr = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        String geo = setGeo(lat, lon);

        Intent intent = new Intent(PROX_ALERT, Uri.parse(geo));
        intent.putExtra("message", message);
        pIntent4 =
                PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        locMgr.addProximityAlert(lat,lon, radius, -1L, pIntent4);
    }
    /**
     * setup the fifth proximity alert
     * @param lat
     * @param lon
     * @param radius
     * @param message
     */
    public void setupProxAlert5(double lat, double lon, float radius, String message) {
        //Proximity Code

        locMgr = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        String geo = setGeo(lat, lon);

        Intent intent = new Intent(PROX_ALERT, Uri.parse(geo));
        intent.putExtra("message", message);
        pIntent5 =
                PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        locMgr.addProximityAlert(lat,lon, radius, -1L, pIntent5);
    }

    /**
     * setup the sixth proximity alert
     * @param lat
     * @param lon
     * @param radius
     * @param message
     */
    public void setupProxAlert6(double lat, double lon, float radius, String message) {
        //Proximity Code

        locMgr = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        String geo = setGeo(lat, lon);

        Intent intent = new Intent(PROX_ALERT, Uri.parse(geo));
        intent.putExtra("message", message);
        pIntent6 =
                PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        locMgr.addProximityAlert(lat,lon, radius, -1L, pIntent6);
    }

    /**
     * setup the seventh proximity alert
     * @param lat
     * @param lon
     * @param radius
     * @param message
     */
    public void setupProxAlert7(double lat, double lon, float radius, String message) {
        //Proximity Code

        locMgr = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        String geo = setGeo(lat, lon);

        Intent intent = new Intent(PROX_ALERT, Uri.parse(geo));
        intent.putExtra("message", message);
        pIntent7 =
                PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        locMgr.addProximityAlert(lat, lon, radius, -1L, pIntent7);
    }

    /**
     * setup the eighth proximity alert
     * @param lat
     * @param lon
     * @param radius
     * @param message
     */
    public void setupProxAlert8(double lat, double lon, float radius, String message) {
        //Proximity Code

        locMgr = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        String geo = setGeo(lat, lon);

        Intent intent = new Intent(PROX_ALERT, Uri.parse(geo));
        intent.putExtra("message", message);
        pIntent8 =
                PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        locMgr.addProximityAlert(lat,lon, radius, -1L, pIntent8);
    }

    /**
     * setup the ninth proximity alert
     * @param lat
     * @param lon
     * @param radius
     * @param message
     */
    public void setupProxAlert9(double lat, double lon, float radius, String message) {
        //Proximity Code

        locMgr = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        String geo = setGeo(lat, lon);

        Intent intent = new Intent(PROX_ALERT, Uri.parse(geo));
        intent.putExtra("message", message);
        pIntent9 =
                PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        locMgr.addProximityAlert(lat,lon, radius, -1L, pIntent9);
    }

    /**
     * setup the tenth proximity alert
     * @param lat
     * @param lon
     * @param radius
     * @param message
     */
    public void setupProxAlert10(double lat, double lon, float radius, String message) {
        //Proximity Code

        locMgr = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        String geo = setGeo(lat, lon);

        Intent intent = new Intent(PROX_ALERT, Uri.parse(geo));
        intent.putExtra("message", message);
        pIntent10 =
                PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        locMgr.addProximityAlert(lat,lon, radius, -1L, pIntent10);
    }

    /**
     * setup the eleventh proximity alert
     * @param lat
     * @param lon
     * @param radius
     * @param message
     */
    public void setupProxAlert11(double lat, double lon, float radius, String message) {
        //Proximity Code

        locMgr = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        String geo = setGeo(lat, lon);

        Intent intent = new Intent(PROX_ALERT, Uri.parse(geo));
        intent.putExtra("message", message);
        pIntent11 =
                PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        locMgr.addProximityAlert(lat,lon, radius, -1L, pIntent11);
    }

    /**
     * setup the twelfth proximity alert
     * @param lat
     * @param lon
     * @param radius
     * @param message
     */
    public void setupProxAlert12(double lat, double lon, float radius, String message) {
        //Proximity Code

        locMgr = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        String geo = setGeo(lat, lon);

        Intent intent = new Intent(PROX_ALERT, Uri.parse(geo));
        intent.putExtra("message", message);
        pIntent12 =
                PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        locMgr.addProximityAlert(lat,lon, radius, -1L, pIntent12);
    }

    /**
     * setup the thirteenth proximity alert
     * @param lat
     * @param lon
     * @param radius
     * @param message
     */
    public void setupProxAlert13(double lat, double lon, float radius, String message) {
        //Proximity Code

        locMgr = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        String geo = setGeo(lat, lon);

        Intent intent = new Intent(PROX_ALERT, Uri.parse(geo));
        intent.putExtra("message", message);
        pIntent13 =
                PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        locMgr.addProximityAlert(lat,lon, radius, -1L, pIntent13);
    }

    /**
     * setup the fourteenth proximity alert
     * @param lat
     * @param lon
     * @param radius
     * @param message
     */
    public void setupProxAlert14(double lat, double lon, float radius, String message) {
        //Proximity Code

        locMgr = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        String geo = setGeo(lat, lon);

        Intent intent = new Intent(PROX_ALERT, Uri.parse(geo));
        intent.putExtra("message", message);
        pIntent14 =
                PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        locMgr.addProximityAlert(lat,lon, radius, -1L, pIntent14);
    }

    /**
     * setup the fifteenth proximity alert
     * @param lat
     * @param lon
     * @param radius
     * @param message
     */
    public void setupProxAlert15(double lat, double lon, float radius, String message) {
        //Proximity Code

        locMgr = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        String geo = setGeo(lat, lon);

        Intent intent = new Intent(PROX_ALERT, Uri.parse(geo));
        intent.putExtra("message", message);
        pIntent15 =
                PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        locMgr.addProximityAlert(lat,lon, radius, -1L, pIntent15);
    }

    /**
     * setup the sixteenth proximity alert
     * @param lat
     * @param lon
     * @param radius
     * @param message
     */
    public void setupProxAlert16(double lat, double lon, float radius, String message) {
        //Proximity Code

        locMgr = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        String geo = setGeo(lat, lon);

        Intent intent = new Intent(PROX_ALERT, Uri.parse(geo));
        intent.putExtra("message", message);
        pIntent16 =
                PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        locMgr.addProximityAlert(lat,lon, radius, -1L, pIntent16);
    }

    /**
     * setup the seventeenth proximity alert
     * @param lat
     * @param lon
     * @param radius
     * @param message
     */
    public void setupProxAlert17(double lat, double lon, float radius, String message) {
        //Proximity Code

        locMgr = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        String geo = setGeo(lat, lon);

        Intent intent = new Intent(PROX_ALERT, Uri.parse(geo));
        intent.putExtra("message", message);
        pIntent17 =
                PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        locMgr.addProximityAlert(lat,lon, radius, -1L, pIntent17);
    }

    /**
     * setup the eighteenth proximity alert
     * @param lat
     * @param lon
     * @param radius
     * @param message
     */
    public void setupProxAlert18(double lat, double lon, float radius, String message) {
        //Proximity Code

        locMgr = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        String geo = setGeo(lat, lon);

        Intent intent = new Intent(PROX_ALERT, Uri.parse(geo));
        intent.putExtra("message", message);
        pIntent18 =
                PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        locMgr.addProximityAlert(lat,lon, radius, -1L, pIntent18);
    }

    /**
     * setup the nineteenth proximity alert
     * @param lat
     * @param lon
     * @param radius
     * @param message
     */
    public void setupProxAlert19(double lat, double lon, float radius, String message) {
        //Proximity Code

        locMgr = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        String geo = setGeo(lat, lon);

        Intent intent = new Intent(PROX_ALERT, Uri.parse(geo));
        intent.putExtra("message", message);
        pIntent19 =
                PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        locMgr.addProximityAlert(lat,lon, radius, -1L, pIntent19);
    }
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(proxReceiver);
        locMgr.removeProximityAlert(pIntent1);
        locMgr.removeProximityAlert(pIntent2);
    }

    /**
     * A method to add the proximity alerts
     */
    public void addAlerts() {
        ArrayList<LocationObject> locationObjectArrayList = populateLocationData();
        LocationObject canil_harvest_centre = locationObjectArrayList.get(0);
        LocationObject gym = locationObjectArrayList.get(1);
        LocationObject douglas_centre = locationObjectArrayList.get(2);
        LocationObject douglas_hall = locationObjectArrayList.get(3);
        LocationObject ezra_house = locationObjectArrayList.get(4);
        LocationObject fosmark_centre = locationObjectArrayList.get(5);
        LocationObject fraser_hall = locationObjectArrayList.get(6);
        LocationObject gym_portable = locationObjectArrayList.get(7);
        LocationObject atrium = locationObjectArrayList.get(8);
        LocationObject mattson_centre = locationObjectArrayList.get(9);
        LocationObject mcmillan_hall = locationObjectArrayList.get(10);
        LocationObject music_building = locationObjectArrayList.get(11);
        LocationObject neufeld_science_centre = locationObjectArrayList.get(12);
        LocationObject northwest_building = locationObjectArrayList.get(13);
        LocationObject reimer_student_centre = locationObjectArrayList.get(14);
        LocationObject rnt_building = locationObjectArrayList.get(15);
        LocationObject robson_hall = locationObjectArrayList.get(16);
        LocationObject vernon_strombeck_cetnre = locationObjectArrayList.get(17);
        LocationObject welcome_centre = locationObjectArrayList.get(18);

        double canil_harvest_centreLongitudeValue = canil_harvest_centre.getLongitudeValue();
        double canil_harvest_centreLatitudeValue = canil_harvest_centre.getLatitudeValue();
        String canil_harvest_centreBuildingName = "CANIL Harbest Centre";

        double gymLongitudeValue = gym.getLongitudeValue();
        double gymLatitudeValue = gym.getLatitudeValue();
        String gymBuildingName = "David E. Enarson Gym";

        double douglas_centreLongitudeValue = douglas_centre.getLongitudeValue();
        double douglas_centreLatitudeValue = douglas_centre.getLatitudeValue();
        String douglas_centreBuildingName = "Douglas Centre";

        double douglas_hallLongitudeValue = douglas_hall.getLongitudeValue();
        double douglas_hallLatitudeValue = douglas_hall.getLatitudeValue();
        String douglas_hallBuildingName = "Douglas Hall";

        double ezra_houseLongitudeValue = ezra_house.getLongitudeValue();
        double ezra_houseLatitudeValue = ezra_house.getLatitudeValue();
        String ezra_houseBuildingName = "Ezra House";

        double fosmark_centreLongitudeValue = fosmark_centre.getLongitudeValue();
        double fosmark_centreLatitudeValue = fraser_hall.getLatitudeValue();
        String fosmark_centreBuildingName = "Fosmark Centre";

        double fraser_hallLongitudeValue = fraser_hall.getLongitudeValue();
        double fraser_hallLatitudeValue = fraser_hall.getLatitudeValue();
        String fraser_hallBuildingName = "Fraser Hall";

        double gym_portableLongitudeValue = gym_portable.getLongitudeValue();
        double gym_portableLatitudeValue = gym_portable.getLatitudeValue();
        String gym_portableBuildingName = "Gym Portable";

        double atriumLongitudeValue = atrium.getLongitudeValue();
        double atriumLatitudeValue = atrium.getLatitudeValue();
        String atriumBuildingName = "Larsen Atrium";

        double mattson_centreLongitudeValue = mattson_centre.getLongitudeValue();
        double mattson_centreLatitudeValue = mattson_centre.getLatitudeValue();
        String mattson_centreBuildingName = "Mattson Centre";

        double mcmillan_hallLongitudeValue = mcmillan_hall.getLongitudeValue();
        double mcmillan_hallLatitudeValue = mcmillan_hall.getLatitudeValue();
        String mcmillan_hallBuildingName = "McMillan Hall";

        double music_buildingLongitudeValue = music_building.getLongitudeValue();
        double music_buildingLatitudeValue = music_building.getLatitudeValue();
        String music_buildingBuildingName = "Music Building";

        double neufeld_science_centreLongitudeValue = neufeld_science_centre.getLongitudeValue();
        double neufeld_science_centreLatitudeValue = neufeld_science_centre.getLatitudeValue();
        String neufeld_science_centreBuildingName = "Nefueld Science Centre";

        double northwest_buildingLongitudeValue = northwest_building.getLongitudeValue();
        double northwest_buildingLatitudeValue = northwest_building.getLatitudeValue();
        String northwest_buildingBuildingName = "Northwest Building";

        double reimer_student_centreLongitudeValue = reimer_student_centre.getLongitudeValue();
        double reimer_student_centreLatitudeValue = reimer_student_centre.getLatitudeValue();
        String reimer_student_centreBuildingName = "Reimer Student Centre";

        double rnt_buildingLongitudeValue = rnt_building.getLongitudeValue();
        double rnt_buildingLatitudeValue = rnt_building.getLatitudeValue();
        String rnt_buildingBuildingName = "Robert N. Thompson Building";

        double robson_hallLongitudeValue = robson_hall.getLongitudeValue();
        double robson_hallLatitudeValue = robson_hall.getLatitudeValue();
        String robson_hallBuildingName = "Robson Hall";

        double vernon_strombeck_cetnreLongitudeValue = vernon_strombeck_cetnre.getLongitudeValue();
        double vernon_strombeck_cetnreLatitudeValue = vernon_strombeck_cetnre.getLatitudeValue();
        String vernon_strombeck_cetnreBuildingName = "Vernon Strombeck Centre";

        double welcome_centreLongitudeValue = welcome_centre.getLongitudeValue();
        double welcome_centreLatitudeValue = welcome_centre.getLatitudeValue();
        String welcome_centreBuildingName = "Welcome Centre";

        setupProxAlert1(atriumLatitudeValue, atriumLongitudeValue, 10, "Atrium");
        setupProxAlert2(canil_harvest_centreLatitudeValue, canil_harvest_centreLongitudeValue, 10, "CANIL Harvest Centre");
    }
}