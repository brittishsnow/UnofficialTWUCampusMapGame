package com.maxstow.unofficialtwucampusmapgame;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.content.Intent;

public class MainActivity extends ActionBarActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Sets this as the active layout
        setContentView(R.layout.activity_main);
    }

    //When the button is clicked this method is invoked
    public void onClick(View view) {
        Intent i = new Intent(this, Welcome.class);
        startActivity(i);
    }
}