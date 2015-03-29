package com.maxstow.unofficialtwucampusmapgame;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Welcome extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Sets this as active layout
        setContentView(R.layout.activity_welcome);

        Bundle mainData = getIntent().getExtras();
        if (mainData == null) {
            return;
        }

        String mainMessage = mainData.getString("mainMessage");
        final TextView userNamePrinted = (TextView) findViewById(R.id.userNamePrinted);
        userNamePrinted.setText(mainMessage);
    }

    public void onClick(View view) {
        Intent buttonIntent = new Intent(this, StartGame.class);
        startActivity(buttonIntent);
    }

}
