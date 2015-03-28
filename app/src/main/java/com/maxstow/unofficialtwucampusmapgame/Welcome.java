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

        //Layout
        RelativeLayout welcomeLayout;
        welcomeLayout = new RelativeLayout(this);
        welcomeLayout.setBackgroundColor(Color.DKGRAY);

        //Button
        Button continueButton;
        continueButton = new Button(this);
        continueButton.setText(getString(R.string.continueButtonText));
        continueButton.setBackgroundColor(Color.LTGRAY);

        //Button
        Button backWelcomeButton;
        backWelcomeButton = new Button(this);
        backWelcomeButton.setText(getString(R.string.backButtonText));
        backWelcomeButton.setBackgroundColor(Color.LTGRAY);

        //Username text printout
        final TextView userNamePrinted;
        userNamePrinted = new TextView(this);
        userNamePrinted.setTextColor(Color.WHITE);

        //Id allocation from id values given in the ids xml file
        continueButton.setId(R.id.continueButton);
        backWelcomeButton.setId(R.id.backWelcomeButton);
        userNamePrinted.setId(R.id.userNamePrinted);

        //Details on the formatting of the Button
        RelativeLayout.LayoutParams continueButtonDetails;
        continueButtonDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        //Details on the formatting of the Button
        RelativeLayout.LayoutParams backWelcomeButtonDetails;
        backWelcomeButtonDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        //Details on the formatting of the TextView userNameTextPrinted
        RelativeLayout.LayoutParams userNameTextPrintedDetails;
        userNameTextPrintedDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        //Button continueButton

        continueButtonDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);

        //Button backButton
        backWelcomeButtonDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        backWelcomeButtonDetails.addRule(RelativeLayout.LEFT_OF, continueButton.getId());
        backWelcomeButtonDetails.setMargins(0,0,50,0);

        //TextView userNameTextPrinted
        userNameTextPrintedDetails.addRule(RelativeLayout.CENTER_VERTICAL);
        userNameTextPrintedDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        userNameTextPrintedDetails.setMargins(50,50,0,0);

        //adds Button to layout
        welcomeLayout.addView(continueButton, continueButtonDetails);

        //adds Button to layout
        welcomeLayout.addView(backWelcomeButton, backWelcomeButtonDetails);

        //adds TextView to layout
        welcomeLayout.addView(userNamePrinted, userNameTextPrintedDetails);

        //Sets this as active layout
        setContentView(welcomeLayout);

        Bundle mainData = getIntent().getExtras();
        if (mainData == null) {
            return;
        }
        String mainMessage = mainData.getString("mainMessage");
        userNamePrinted.setText(mainMessage);
        //Make the button listen for a click
        continueButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View view) {
                        moveToStartGame();
                    }
                });


        //Make the button listen for a click
        backWelcomeButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View view) {
                        moveToMain();
                    }
                });
    }

    private void moveToStartGame() {
        Intent buttonIntent = new Intent(this, StartGame.class);
        startActivity(buttonIntent);
    }

    private void moveToMain() {
        Intent buttonIntent = new Intent(this, MainActivity.class);
        startActivity(buttonIntent);
    }
}
