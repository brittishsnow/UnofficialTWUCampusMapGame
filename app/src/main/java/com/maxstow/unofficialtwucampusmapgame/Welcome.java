package com.maxstow.unofficialtwucampusmapgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class Welcome extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Sets this as active layout
        setContentView(R.layout.activity_welcome);


    }

    //When the button is click this method is invoked
    public void onClick(View view) {
        Intent buttonIntent = new Intent(this, StartGame.class);
        startActivity(buttonIntent);
    }

}
