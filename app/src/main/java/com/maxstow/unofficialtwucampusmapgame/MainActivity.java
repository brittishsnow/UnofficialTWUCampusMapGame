package com.maxstow.unofficialtwucampusmapgame;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Layout
        RelativeLayout mainLayout;
        mainLayout = new RelativeLayout(this);
        mainLayout.setBackgroundColor(Color.GRAY);

        //Button
        Button userNameInputButton;
        userNameInputButton = new Button(this);
        userNameInputButton.setText(getString(R.string.userNameInputButtonText));
        userNameInputButton.setBackgroundColor(Color.LTGRAY);

        //Username input
        final EditText userNameInput;
        userNameInput = new EditText(this);

        //Username text instruction
        TextView userNameTextInstruction;
        userNameTextInstruction = new TextView(this);
        userNameTextInstruction.setText(getString(R.string.userNameTextInstructionText));

        //Username text printout
        final TextView userNamePrinted;
        userNamePrinted = new TextView(this);

        //Id allocation from id values given in the ids xml file
        userNameInputButton.setId(R.id.userNameInputButton);
        userNameInput.setId(R.id.userNameInput);
        userNameTextInstruction.setId(R.id.userNameTextInstruction);
        userNamePrinted.setId(R.id.userNamePrinted);

        //Details on the formatting of the Button
        RelativeLayout.LayoutParams userNameInputButtonDetails;
        userNameInputButtonDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        //Details on the formatting of the EditText
        RelativeLayout.LayoutParams userNameInputDetails;
        userNameInputDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        //Details on the formatting of the TextView Above
        RelativeLayout.LayoutParams userNameTextInstructionDetails;
        userNameTextInstructionDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        //Details on the formatting of the TextView Below
        RelativeLayout.LayoutParams userNameTextPrintedDetails;
        userNameTextPrintedDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        //Give rules to position the widgets in the Relative layout

            //Button
        userNameInputButtonDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        userNameInputButtonDetails.addRule(RelativeLayout.CENTER_VERTICAL);


            //EditText
        userNameInputDetails.addRule(RelativeLayout.ABOVE, userNameInputButton.getId());
        userNameInputDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        userNameInputDetails.setMargins(0,0,0,50);


            //Convert DIP to pixels
        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200,
                r.getDisplayMetrics()
        );

        userNameInput.setWidth(px);

        //TextView Above
        userNameTextInstructionDetails.addRule(RelativeLayout.ABOVE, userNameInput.getId());
        userNameTextInstructionDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        userNameTextInstructionDetails.setMargins(0,0,0,50);
        userNameTextInstructionDetails.addRule(RelativeLayout.CENTER_VERTICAL);

        //TextView Below
        userNameTextPrintedDetails.addRule(RelativeLayout.BELOW, userNameInputButton.getId());
        userNameTextPrintedDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        userNameTextPrintedDetails.setMargins(200, 0, 0, 0);
        userNameTextPrintedDetails.addRule(RelativeLayout.CENTER_VERTICAL);


        //Add widget to the layout(button is now a child of the layout)

            //adds TextView to layout
        mainLayout.addView(userNameTextInstruction, userNameTextInstructionDetails);

            //adds EditText to layout
        mainLayout.addView(userNameInput, userNameInputDetails);

            //adds Button to layout
        mainLayout.addView(userNameInputButton, userNameInputButtonDetails);

            //add EditText to layout
        mainLayout.addView(userNamePrinted, userNameTextPrintedDetails);

        //Set this activates
        setContentView(mainLayout);

        //Make the button listen for a click
        userNameInputButton.setOnClickListener(
            new Button.OnClickListener() {
                public void onClick(View view) {
                    userNamePrinted.setText(userNameInput.getText().toString());
                }
            }
        );

    }
}
