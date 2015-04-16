package com.maxstow.unofficialtwucampusmapgame;

//All the imports from the android os
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//All the imports from the google API
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

//All the java imports
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class StartGame extends Activity implements ConnectionCallbacks,
        OnConnectionFailedListener, LocationListener {

    //For the notifications
    NotificationManager notificationManagerFeildTrip;

    /**
     * Location tracking based off of tutorial on http://www.androidhive.info/2012/07/android-gps-location-manager-tutorial/
     */
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

    //Radius for Proximity Alert
    double RADIUS = 5.0; //5 meters

    //When the activity is created it goes through the following code
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Sets this as the active layout
        setContentView(R.layout.activity_start_game);

        //Associates the elements from the layout as variables for use in the StartGame java class
        lblLocation = (TextView) findViewById(R.id.lblLocation);
        btnShowLocation = (Button) findViewById(R.id.butttonShowLocation);
        btnStartLocationUpdates = (Button) findViewById(R.id.buttonLocationUpdates);

        //All the distance elements from the layout
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

            //Creates a location request
            createLocationRequest();
        }

        // Show location button click listener
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            //When the button is clicked it force refreshes the information through this method
            @Override
            public void onClick(View v) {
                displayLocation();


            }
        });

        // Toggling the periodic location updates
        btnStartLocationUpdates.setOnClickListener(new View.OnClickListener() {

            //This turns the locations updates on or off through a toggle
            @Override
            public void onClick(View v) {
                togglePeriodicLocationUpdates();
            }
        });

    }

    //When the activity is started it checks for the GoogleApiClient and connects
    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    //When the activity is resumed it checks for the GoogleApiClient
    @Override
    protected void onResume() {
        super.onResume();

        checkPlayServices();

        // Resuming the periodic location updates
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    //When the activity is stopped it disconnects the GoogleApiClient
    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    //When the activity is paused the locationUpdates are suspended
    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    /**
     * Method to display the location on UI and the Distances
     */
    private void displayLocation() {

        //Creates a variable to receive the location inportmation implementing Google's FusedLocationApi
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        //Sets an ArrayList named locationObjectArrayList of type LocationObject to values in the populateLocationData method
        ArrayList<LocationObject> locationObjectArrayList = populateLocationData();

        //Retrieves the locationObjects from the ArrayList using the indexes
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

        //Retrieves the data for the CANIL Harvest Centre and sets it to the following variables
        double canil_harvest_centreLongitudeValue = canil_harvest_centre.getLongitudeValue();
        double canil_harvest_centreLatitudeValue = canil_harvest_centre.getLatitudeValue();
        String canil_harvest_centreBuildingName = "CANIL Harvest Centre";

        //Retrieves the data for the David E. Enarson Gym and sets it to the following variables
        double gymLongitudeValue = gym.getLongitudeValue();
        double gymLatitudeValue = gym.getLatitudeValue();
        String gymBuildingName = "David E. Enarson Gym";

        //Retrieves the data for the Douglas Centre and sets it to the following variables
        double douglas_centreLongitudeValue = douglas_centre.getLongitudeValue();
        double douglas_centreLatitudeValue = douglas_centre.getLatitudeValue();
        String douglas_centreBuildingName = "Douglas Centre";

        //Retrieves the data for the Douglas Hall and sets it to the following variables
        double douglas_hallLongitudeValue = douglas_hall.getLongitudeValue();
        double douglas_hallLatitudeValue = douglas_hall.getLatitudeValue();
        String douglas_hallBuildingName = "Douglas Hall";

        //Retrieves the data for the Ezra House and sets it to the following variables
        double ezra_houseLongitudeValue = ezra_house.getLongitudeValue();
        double ezra_houseLatitudeValue = ezra_house.getLatitudeValue();
        String ezra_houseBuildingName = "Ezra House";

        //Retrieves the data for the Fosmark Centre and sets it to the following variables
        double fosmark_centreLongitudeValue = fosmark_centre.getLongitudeValue();
        double fosmark_centreLatitudeValue = fraser_hall.getLatitudeValue();
        String fosmark_centreBuildingName = "Fosmark Centre";

        //Retrieves the data for the Fraser Hall and sets it to the following variables
        double fraser_hallLongitudeValue = fraser_hall.getLongitudeValue();
        double fraser_hallLatitudeValue = fraser_hall.getLatitudeValue();
        String fraser_hallBuildingName = "Fraser Hall";

        //Retrieves the data for the Gym Portable and sets it to the following variables
        double gym_portableLongitudeValue = gym_portable.getLongitudeValue();
        double gym_portableLatitudeValue = gym_portable.getLatitudeValue();
        String gym_portableBuildingName = "Gym Portable";

        //Retrieves the data for the Larsen Atrium and sets it to the following variables
        double atriumLongitudeValue = atrium.getLongitudeValue();
        double atriumLatitudeValue = atrium.getLatitudeValue();
        String atriumBuildingName = "Larsen Atrium";

        //Retrieves the data for the Mattson Centre and sets it to the following variables
        double mattson_centreLongitudeValue = mattson_centre.getLongitudeValue();
        double mattson_centreLatitudeValue = mattson_centre.getLatitudeValue();
        String mattson_centreBuildingName = "Mattson Centre";

        //Retrieves the data for the McMillan Hall and sets it to the following variables
        double mcmillan_hallLongitudeValue = mcmillan_hall.getLongitudeValue();
        double mcmillan_hallLatitudeValue = mcmillan_hall.getLatitudeValue();
        String mcmillan_hallBuildingName = "McMillan Hall";

        //Retrieves the data for the Music Building and sets it to the following variables
        double music_buildingLongitudeValue = music_building.getLongitudeValue();
        double music_buildingLatitudeValue = music_building.getLatitudeValue();
        String music_buildingBuildingName = "Music Building";

        //Retrieves the data for the Neufeld Science Centre and sets it to the following variables
        double neufeld_science_centreLongitudeValue = neufeld_science_centre.getLongitudeValue();
        double neufeld_science_centreLatitudeValue = neufeld_science_centre.getLatitudeValue();
        String neufeld_science_centreBuildingName = "Nefueld Science Centre";

        //Retrieves the data for the Northwest Building and sets it to the following variables
        double northwest_buildingLongitudeValue = northwest_building.getLongitudeValue();
        double northwest_buildingLatitudeValue = northwest_building.getLatitudeValue();
        String northwest_buildingBuildingName = "Northwest Building";

        //Retrieves the data for the Reimer Student Centre and sets it to the following variables
        double reimer_student_centreLongitudeValue = reimer_student_centre.getLongitudeValue();
        double reimer_student_centreLatitudeValue = reimer_student_centre.getLatitudeValue();
        String reimer_student_centreBuildingName = "Reimer Student Centre";

        //Retrieves the data for the Robert N. Thompson Building and sets it to the following variables
        double rnt_buildingLongitudeValue = rnt_building.getLongitudeValue();
        double rnt_buildingLatitudeValue = rnt_building.getLatitudeValue();
        String rnt_buildingBuildingName = "Robert N. Thompson Building";

        //Retrieves the data for the Robson Hall and sets it to the following variables
        double robson_hallLongitudeValue = robson_hall.getLongitudeValue();
        double robson_hallLatitudeValue = robson_hall.getLatitudeValue();
        String robson_hallBuildingName = "Robson Hall";

        //Retrieves the data for the Vernon Strombeck Centre and sets it to the following variables
        double vernon_strombeck_cetnreLongitudeValue = vernon_strombeck_cetnre.getLongitudeValue();
        double vernon_strombeck_cetnreLatitudeValue = vernon_strombeck_cetnre.getLatitudeValue();
        String vernon_strombeck_cetnreBuildingName = "Vernon Strombeck Centre";

        //Retrieves the data for the Welcome Centre and sets it to the following variables
        double welcome_centreLongitudeValue = welcome_centre.getLongitudeValue();
        double welcome_centreLatitudeValue = welcome_centre.getLatitudeValue();
        String welcome_centreBuildingName = "Welcome Centre";

        //Declares the variable latitude and longitude to use with the lastLocation variable to retrieve the users last latitude and longitude
        double latitude;
        double longitude;

        //If the phone has the location tracking enabled then the following if statement will be executed
        if (mLastLocation != null) {

            //Sets the longitude and latitude to the values given by the GPS
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();

            //Calculates the distance to the CANIL Harvest Centre as a double
            double distanceTocanil_harvest_centre = calculateDistance( latitude, longitude, canil_harvest_centreLatitudeValue, canil_harvest_centreLongitudeValue);

            //Sets the distance as a formatted string
            String distanceTocanil_harvest_centreString = canil_harvest_centreBuildingName + ": " + round(distanceTocanil_harvest_centre, 2) + " meters" ;

            //Sets the TextView distance1 as the string above
            distance1.setText(distanceTocanil_harvest_centreString);

            //If the distance is less than the predetermined radius, in our case 5 meters, this will be executed
            if (distanceTocanil_harvest_centre <= RADIUS) {
                //Brings up a notification
                notify(getString(R.string.twuFieldTrip), getString(R.string.twuFieldTrip), getString(R.string.welcomeTextCanil));

                //Goes to a new activity that has the information about that building
                Intent distanceIntent = new Intent(this, FieldTrip.class);
                startActivity(distanceIntent);
            }

            //Calculates the distance to the Gym as a double
            double distanceTogym = calculateDistance( latitude, longitude, gymLatitudeValue, gymLongitudeValue);

            //Sets the distance as a formatted string
            String distanceTogymString = gymBuildingName + ": " + round(distanceTogym, 2) + " meters" ;

            //Sets the TextView distance2 as the string above
            distance2.setText(distanceTogymString);

            //If the distance is less than the predetermined radius, in our case 5 meters, this will be executed
            if (distanceTogym <= RADIUS) {
                //Brings up a notification
                notify(getString(R.string.twuFieldTrip), getString(R.string.twuFieldTrip), getString(R.string.welcomeTextGym));

                //Goes to a new activity that has the information about that building
                Intent distanceIntent = new Intent(this, FieldTrip2.class);
                startActivity(distanceIntent);
            }

            //Calculates the distance to the Douglas Centre as a double
            double distanceTodouglas_centre = calculateDistance( latitude, longitude, douglas_centreLatitudeValue, douglas_centreLongitudeValue);

            //Sets the distance as a formatted string
            String distanceTodouglas_centreString = douglas_centreBuildingName + ": " + round(distanceTodouglas_centre, 2) + " meters" ;

            //Sets the TextView distance3 as the string above
            distance3.setText(distanceTodouglas_centreString);

            //If the distance is less than the predetermined radius, in our case 5 meters, this will be executed
            if (distanceTodouglas_centre <= RADIUS) {

                //Brings up a notification
                notify(getString(R.string.twuFieldTrip), getString(R.string.twuFieldTrip), getString(R.string.welcomeTextDouglasCentre));

                //Goes to a new activity that has the information about that building
                Intent distanceIntent = new Intent(this, FieldTrip3.class);
                startActivity(distanceIntent);
            }

            //Calculates the distance to the Douglas Hall as a double
            double distanceTodouglas_hall = calculateDistance( latitude, longitude, douglas_hallLatitudeValue, douglas_hallLongitudeValue);

            //Sets the distance as a formatted string
            String distanceTodouglas_hallString = douglas_hallBuildingName + ": " + round(distanceTodouglas_hall, 2) + " meters" ;

            //Sets the TextView distance4 as the string above
            distance4.setText(distanceTodouglas_hallString);

            //If the distance is less than the predetermined radius, in our case 5 meters, this will be executed
            if (distanceTodouglas_hall <= RADIUS) {

                //Brings up a notification
                notify(getString(R.string.twuFieldTrip), getString(R.string.twuFieldTrip), getString(R.string.welcomeTextDouglasHall));

                //Goes to a new activity that has the information about that building
                Intent distanceIntent = new Intent(this, FieldTrip4.class);
                startActivity(distanceIntent);
            }

            //Calculates the distance to the Ezra House as a double
            double distanceToezra_house = calculateDistance( latitude, longitude, ezra_houseLatitudeValue, ezra_houseLongitudeValue);

            //Sets the distance as a formatted string
            String distanceToezra_houseString = ezra_houseBuildingName + ": " + round(distanceToezra_house, 2) + " meters" ;

            //Sets the TextView distance5 as the string above
            distance5.setText(distanceToezra_houseString);

            //If the distance is less than the predetermined radius, in our case 5 meters, this will be executed
            if (distanceToezra_house <= RADIUS) {

                //Brings up a notification
                notify(getString(R.string.twuFieldTrip), getString(R.string.twuFieldTrip), getString(R.string.welcomeTextEzraHouse));

                //Goes to a new activity that has the information about that building
                Intent distanceIntent = new Intent(this, FieldTrip5.class);
                startActivity(distanceIntent);
            }

            //Calculates the distance to the Fosmark Centre as a double
            double distanceTofosamrk_centre = calculateDistance( latitude, longitude, fosmark_centreLatitudeValue, fosmark_centreLongitudeValue);

            //Sets the distance as a formatted string
            String distanceTofosmark_centreString = fosmark_centreBuildingName + ": " + round(distanceTofosamrk_centre, 2) + " meters" ;

            //Sets the TextView distance6 as the string above
            distance6.setText(distanceTofosmark_centreString);

            //If the distance is less than the predetermined radius, in our case 5 meters, this will be executed
            if (distanceTofosamrk_centre <= RADIUS) {

                //Brings up a notification
                notify(getString(R.string.twuFieldTrip), getString(R.string.twuFieldTrip), getString(R.string.welcomeTextFosmark));

                //Goes to a new activity that has the information about that building
                Intent distanceIntent = new Intent(this, FieldTrip6.class);
                startActivity(distanceIntent);
            }

            //Calculates the distance to the Fraser Hall as a double
            double distanceTofraser_hall = calculateDistance( latitude, longitude, fraser_hallLatitudeValue, fraser_hallLongitudeValue);

            //Sets the distance as a formatted string
            String distanceTofraser_hallString = fraser_hallBuildingName + ": " + round(distanceTofraser_hall, 2) + " meters" ;

            //Sets the TextView distance7 as the string above
            distance7.setText(distanceTofraser_hallString);

            //If the distance is less than the predetermined radius, in our case 5 meters, this will be executed
            if (distanceTofraser_hall <= RADIUS) {

                //Brings up a notification
                notify(getString(R.string.twuFieldTrip), getString(R.string.twuFieldTrip), getString(R.string.welcomeTextFraserHall));

                //Goes to a new activity that has the information about that building
                Intent distanceIntent = new Intent(this, FieldTrip7.class);
                startActivity(distanceIntent);
            }

            //Calculates the distance to the Gym Portable as a double
            double distanceTogym_portable = calculateDistance( latitude, longitude, gym_portableLatitudeValue, gym_portableLongitudeValue);

            //Sets the distance as a formatted string
            String distanceTogym_portableString = gym_portableBuildingName + ": " + round(distanceTogym_portable, 2) + " meters" ;

            //Sets the TextView distance8 as the string above
            distance8.setText(distanceTogym_portableString);

            //If the distance is less than the predetermined radius, in our case 5 meters, this will be executed
            if (distanceTogym_portable <= RADIUS) {

                //Brings up a notification
                notify(getString(R.string.twuFieldTrip), getString(R.string.twuFieldTrip), getString(R.string.welcomeTextGymPortable));

                //Goes to a new activity that has the information about that building
                Intent distanceIntent = new Intent(this, FieldTrip8.class);
                startActivity(distanceIntent);
            }

            //Calculates the distance to the Larsen Atrium as a double
            double distanceToatrium = calculateDistance( latitude, longitude, atriumLatitudeValue, atriumLongitudeValue);

            //Sets the distance as a formatted string
            String distanceToatriumString = atriumBuildingName + ": " + round(distanceToatrium, 2) + " meters" ;

            //Sets the TextView distance9 as the string above
            distance9.setText(distanceToatriumString);

            //If the distance is less than the predetermined radius, in our case 5 meters, this will be executed
            if (distanceToatrium <= RADIUS) {

                //Brings up a notification
                notify(getString(R.string.twuFieldTrip), getString(R.string.twuFieldTrip), getString(R.string.welcomeTextAtrium));

                //Goes to a new activity that has the information about that building
                Intent distanceIntent = new Intent(this, FieldTrip9.class);
                startActivity(distanceIntent);
            }

            //Calculates the distance to the Mattson Centre as a double
            double distanceTomattson_centre = calculateDistance( latitude, longitude, mattson_centreLatitudeValue, mattson_centreLongitudeValue);

            //Sets the distance as a formatted string
            String distanceTomattson_centreString = mattson_centreBuildingName + ": " + round(distanceTomattson_centre, 2) + " meters" ;

            //Sets the TextView distance10 as the string above
            distance10.setText(distanceTomattson_centreString);

            //If the distance is less than the predetermined radius, in our case 5 meters, this will be executed
            if (distanceTomattson_centre <= RADIUS) {

                //Brings up a notification
                notify(getString(R.string.twuFieldTrip), getString(R.string.twuFieldTrip), getString(R.string.welcomeTextMattsonCentre));

                //Goes to a new activity that has the information about that building
                Intent distanceIntent = new Intent(this, FieldTrip10.class);
                startActivity(distanceIntent);
            }

            //Calculates the distance to the McMillan Hall as a double
            double distanceTomcmillan_hall = calculateDistance( latitude, longitude, mcmillan_hallLatitudeValue, mcmillan_hallLongitudeValue);

            //Sets the distance as a formatted string
            String distanceTomcmillan_hallString = mcmillan_hallBuildingName + ": " + round(distanceTomcmillan_hall, 2) + " meters" ;

            //Sets the TextView distance11 as the string above
            distance11.setText(distanceTomcmillan_hallString);

            //If the distance is less than the predetermined radius, in our case 5 meters, this will be executed
            if (distanceTomcmillan_hall <= RADIUS) {

                //Brings up a notification
                notify(getString(R.string.twuFieldTrip), getString(R.string.twuFieldTrip), getString(R.string.welcomeTextMcmillanHall));

                //Goes to a new activity that has the information about that building
                Intent distanceIntent = new Intent(this, FieldTrip11.class);
                startActivity(distanceIntent);
            }

            //Calculates the distance to the Music Building as a double
            double distanceTomusic_building = calculateDistance( latitude, longitude, music_buildingLatitudeValue, music_buildingLongitudeValue);

            //Sets the distance as a formatted string
            String distanceTomusic_buildingString = music_buildingBuildingName + ": " + round(distanceTomusic_building, 2) + " meters" ;

            //Sets the TextView distance12 as the string above
            distance12.setText(distanceTomusic_buildingString);

            //If the distance is less than the predetermined radius, in our case 5 meters, this will be executed
            if (distanceTomusic_building <= RADIUS) {

                //Brings up a notification
                notify(getString(R.string.twuFieldTrip), getString(R.string.twuFieldTrip), getString(R.string.welcomeTextMusicBuilding));

                //Goes to a new activity that has the information about that building
                Intent distanceIntent = new Intent(this, FieldTrip12.class);
                startActivity(distanceIntent);
            }

            //Calculates the distance to the Neufeld Science Centre as a double
            double distanceToneufeld_science_centre = calculateDistance( latitude, longitude, neufeld_science_centreLatitudeValue, neufeld_science_centreLongitudeValue);

            //Sets the distance as a formatted string
            String distanceToneufeld_science_centreString = neufeld_science_centreBuildingName + ": " + round(distanceToneufeld_science_centre, 2) + " meters";

            //Sets the TextView distance13 as the string above
            distance13.setText(distanceToneufeld_science_centreString);

            //If the distance is less than the predetermined radius, in our case 5 meters, this will be executed
            if (distanceToneufeld_science_centre <= RADIUS) {

                //Brings up a notification
                notify(getString(R.string.twuFieldTrip), getString(R.string.twuFieldTrip), getString(R.string.welcomeTextNefeldCentre));

                //Goes to a new activity that has the information about that building
                Intent distanceIntent = new Intent(this, FieldTrip13.class);
                startActivity(distanceIntent);
            }

            //Calculates the distance to the Northwest Building as a double
            double distanceTonorthwest_building = calculateDistance( latitude, longitude, northwest_buildingLatitudeValue, northwest_buildingLongitudeValue);

            //Sets the distance as a formatted string
            String distanceTonorthwest_buildingString = northwest_buildingBuildingName + ": " + round(distanceTonorthwest_building, 2) + " meters" ;

            //Sets the TextView distance14 as the string above
            distance14.setText(distanceTonorthwest_buildingString);

            //If the distance is less than the predetermined radius, in our case 5 meters, this will be executed
            if (distanceTonorthwest_building <= RADIUS) {

                //Brings up a notification
                notify(getString(R.string.twuFieldTrip), getString(R.string.twuFieldTrip), getString(R.string.welcomeTextNorthwestBuilding));

                //Goes to a new activity that has the information about that building
                Intent distanceIntent = new Intent(this, FieldTrip14.class);
                startActivity(distanceIntent);
            }

            //Calculates the distance to the Northwest Building as a double
            double distanceToreimer_student_centre = calculateDistance( latitude, longitude, reimer_student_centreLatitudeValue, reimer_student_centreLongitudeValue);

            //Sets the distance as a formatted string
            String distanceToreimer_student_centreString = reimer_student_centreBuildingName + ": " + round(distanceToreimer_student_centre, 2) + " meters" ;

            //Sets the TextView distance15 as the string above
            distance15.setText(distanceToreimer_student_centreString);

            //If the distance is less than the predetermined radius, in our case 5 meters, this will be executed
            if (distanceToreimer_student_centre <= RADIUS) {

                //Brings up a notification
                notify(getString(R.string.twuFieldTrip), getString(R.string.twuFieldTrip), getString(R.string.welcomeTextReimerCentre));

                //Goes to a new activity that has the information about that building
                Intent distanceIntent = new Intent(this, FieldTrip15.class);
                startActivity(distanceIntent);
            }

            double distanceTornt_building = calculateDistance( latitude, longitude, rnt_buildingLatitudeValue, rnt_buildingLongitudeValue);
            String distanceTornt_buildingString = rnt_buildingBuildingName + ": " + round(distanceTornt_building, 2) + " meters" ;

            distance16.setText(distanceTornt_buildingString);

            if (distanceTornt_building <= RADIUS) {
                notify(getString(R.string.twuFieldTrip), getString(R.string.twuFieldTrip), getString(R.string.welcomeTextRNTBuilding));
                Intent distanceIntent = new Intent(this, FieldTrip16.class);
                startActivity(distanceIntent);
            }

            double distanceTorobson_hall = calculateDistance( latitude, longitude, robson_hallLatitudeValue, robson_hallLongitudeValue);
            String distanceTorobson_hallString = robson_hallBuildingName + ": " + round(distanceTorobson_hall, 2) + " meters" ;

            distance17.setText(distanceTorobson_hallString);

            if (distanceTorobson_hall <= RADIUS) {
                notify(getString(R.string.twuFieldTrip), getString(R.string.twuFieldTrip), getString(R.string.welcomeTextRobsonHall));
                Intent distanceIntent = new Intent(this, FieldTrip17.class);
                startActivity(distanceIntent);
            }

            double distanceTovernon_strombeck_cetnre = calculateDistance( latitude, longitude, vernon_strombeck_cetnreLatitudeValue, vernon_strombeck_cetnreLongitudeValue);
            String distanceTovernon_strombeck_cetnreString = vernon_strombeck_cetnreBuildingName + ": " + round(distanceTovernon_strombeck_cetnre, 2) + " meters" ;

            distance18.setText(distanceTovernon_strombeck_cetnreString);

            if (distanceTovernon_strombeck_cetnre <= RADIUS) {
                notify(getString(R.string.twuFieldTrip), getString(R.string.twuFieldTrip), getString(R.string.welcomeTextVernonStrombeckCentre));
                Intent distanceIntent = new Intent(this, FieldTrip18.class);
                startActivity(distanceIntent);
            }

            double distanceTowelcome_centre = calculateDistance( latitude, longitude, welcome_centreLatitudeValue, welcome_centreLongitudeValue);
            String distanceTowelcome_centreString = welcome_centreBuildingName + ": " + round(distanceTowelcome_centre, 2) + " meters" ;

            distance19.setText(distanceTowelcome_centreString);

            if (distanceTowelcome_centre <= RADIUS) {
                notify(getString(R.string.twuFieldTrip), getString(R.string.twuFieldTrip), getString(R.string.welcomeTextWelcomeCentre));
                Intent distanceIntent = new Intent(this, FieldTrip19.class);
                startActivity(distanceIntent);
            }

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
     * A method to implement the Haversine formula from the site; It was in javaScript so I translated it with some modifications to fit my code and do what I want: http://www.movable-type.co.uk/scripts/latlong.html
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
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Based off of tutorial from http://www.tutorialspoint.com/android/android_push_notification.htm
     * @param title
     * @param subject
     * @param body
     */
    @SuppressWarnings("deprecation")
    public void notify(String title, String subject, String body){
        notificationManagerFeildTrip=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify=new Notification(android.R.drawable.
                stat_notify_more,title,System.currentTimeMillis());
        PendingIntent pending = PendingIntent.getActivity(
                getApplicationContext(),0, new Intent(),0);
        notify.setLatestEventInfo(getApplicationContext(),subject,body,pending);
        notificationManagerFeildTrip.notify(0, notify);
    }
 }