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
        welcomeLayout.setBackgroundColor(Color.GRAY);

        //Button
        Button continueButton;
        continueButton = new Button(this);
        continueButton.setText(getString(R.string.continueButtonText));
        continueButton.setBackgroundColor(Color.LTGRAY);

        //Button
        Button backButton;
        backButton = new Button(this);
        backButton.setText(getString(R.string.backButtonText));
        backButton.setBackgroundColor(Color.LTGRAY);

        //Username text printout
        final TextView userNamePrinted;
        userNamePrinted = new TextView(this);

        //Id allocation from id values given in the ids xml file
        continueButton.setId(R.id.continueButton);
        backButton.setId(R.id.backButton);
        userNamePrinted.setId(R.id.userNamePrinted);

        //Details on the formatting of the Button
        RelativeLayout.LayoutParams continueButtonDetails;
        continueButtonDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        //Details on the formatting of the Button
        RelativeLayout.LayoutParams backButtonDetails;
        backButtonDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        //Details on the formatting of the TextView userNameTextPrinted
        RelativeLayout.LayoutParams userNameTextPrintedDetails;
        userNameTextPrintedDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        //Button continueButton
        continueButtonDetails.addRule(RelativeLayout.BELOW, backButton.getId());
        continueButtonDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        continueButtonDetails.addRule(RelativeLayout.ALIGN_BOTTOM);

        //Button backButton
        backButtonDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        backButtonDetails.setMargins(0,0,0,50);

        //TextView userNameTextPrinted
        userNameTextPrintedDetails.addRule(RelativeLayout.CENTER_VERTICAL);
        userNameTextPrintedDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        userNameTextPrintedDetails.setMargins(50,50,0,0);

        //adds Button to layout
        welcomeLayout.addView(continueButton, continueButtonDetails);

        //adds Button to layout
        welcomeLayout.addView(backButton, backButtonDetails);

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
        backButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View view) {
                        moveToMain();
                    }
                });
    }
    private void moveToMain() {
        Intent buttonIntent = new Intent(this, MainActivity.class);
        startActivity(buttonIntent);
    }
}
