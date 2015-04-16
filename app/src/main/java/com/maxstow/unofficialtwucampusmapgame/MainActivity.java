package com.maxstow.unofficialtwucampusmapgame;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.content.Intent;

public class MainActivity extends ActionBarActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        Intent i = new Intent(this, Welcome.class);
        startActivity(i);
    }
}