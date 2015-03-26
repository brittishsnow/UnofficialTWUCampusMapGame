package com.maxstow.unofficialtwucampusmapgame;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Intent;

public class StartGame extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Layout
        RelativeLayout startGameLayout;
        startGameLayout = new RelativeLayout(this);
        startGameLayout.setBackgroundColor(Color.DKGRAY);

        //Button
        Button backStartGameButton;
        backStartGameButton = new Button(this);
        backStartGameButton.setText(getString(R.string.backButtonText));
        backStartGameButton.setBackgroundColor(Color.LTGRAY);

        //Button
        Button startGameButton;
        startGameButton = new Button(this);
        startGameButton.setText(getString(R.string.startGameButtonText));
        startGameButton.setBackgroundColor(Color.LTGRAY);

        //startGame TextView
        final TextView startGameText;
        startGameText = new TextView(this);
        startGameText.setTextColor(Color.WHITE);

        //Id allocation from id values given in the ids xml file
        startGameButton.setId(R.id.startGameButton);
        backStartGameButton.setId(R.id.backStartGameButton);
        startGameText.setId(R.id.startGameText);

        //Details on the formatting of the Button
        RelativeLayout.LayoutParams backStartGameButtonDetails;
        backStartGameButtonDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        //Details on the formatting of the Button
        RelativeLayout.LayoutParams startGameButtonDetails;
        startGameButtonDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        //Details on the formatting of the TextView userNameTextPrinted
        RelativeLayout.LayoutParams startGameTextDetails;
        startGameTextDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        //Button startGameButton
        startGameButtonDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        startGameButtonDetails.addRule(RelativeLayout.CENTER_VERTICAL);


        //Button backStartGameButton
        backStartGameButtonDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        backStartGameButtonDetails.addRule(RelativeLayout.LEFT_OF, startGameButton.getId());
        backStartGameButtonDetails.setMargins(0,0,50,0);

        //TextView startGameText
        startGameTextDetails.addRule(RelativeLayout.CENTER_VERTICAL);
        startGameTextDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        startGameTextDetails.setMargins(50,0,0,0);

        //adds Button to layout
        startGameLayout.addView(startGameButton, startGameButtonDetails);

        //adds Button to layout
        startGameLayout.addView(backStartGameButton, backStartGameButtonDetails);

        //adds TextView to layout
        startGameLayout.addView(startGameText, startGameTextDetails);

        //Sets this as active layout
        setContentView(startGameLayout);



        //Make the button listen for a click
        backStartGameButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View view) {
                        moveToWelcome();
                    }
                });
    }

    private void moveToWelcome() {
        Intent buttonIntent = new Intent(this, Welcome.class);
        startActivity(buttonIntent);
    }


}
