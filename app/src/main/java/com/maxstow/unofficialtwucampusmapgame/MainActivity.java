package com.maxstow.unofficialtwucampusmapgame;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Button;
import android.graphics.Color;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Layout
        RelativeLayout mainLayout = new RelativeLayout(this);
        mainLayout.setBackgroundColor(Color.DKGRAY);

        //Button
        Button nameInputButton = new Button(this);
        nameInputButton.setText("Confirm");
        nameInputButton.setBackgroundColor(Color.LTGRAY);

        //Details on the formatting of the button
        RelativeLayout.LayoutParams nameInputButtonDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        //Adds rules to the details
        nameInputButtonDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        nameInputButtonDetails.addRule(RelativeLayout.CENTER_VERTICAL);


        //Add widget to the layout(button is now a child of the layout)
        mainLayout.addView(nameInputButton, nameInputButtonDetails);

        //Set this activates
        setContentView(mainLayout);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
