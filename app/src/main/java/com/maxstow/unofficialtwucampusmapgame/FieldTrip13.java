package com.maxstow.unofficialtwucampusmapgame;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class FieldTrip13 extends ActionBarActivity {

    /**
     * Defines variables for global use in the class
     */
    private Button backButton;
    private final Context context = null;
    private TextView welcomeText, thingsToDo, thingsToDoThere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_trip);

        welcomeText = (TextView) findViewById(R.id.welcomeText);
        thingsToDo = (TextView) findViewById(R.id.thingsToDo);
        thingsToDoThere = (TextView) findViewById(R.id.thingsToDoThere);

        welcomeText.setText(R.string.welcomeTextNefeldCentre);
        thingsToDo.setText(R.string.thingsToDoHere);
        thingsToDoThere.setText(R.string.thingsToDoAtNeufeldCentre);

        backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent proximityIntent;
                proximityIntent = new Intent(context, StartGame.class);
                context.startActivity(proximityIntent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_field_trip, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
