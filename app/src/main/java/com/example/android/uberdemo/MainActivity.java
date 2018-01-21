package com.example.android.uberdemo;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.SaveCallback;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import java.text.ParseException;

public class MainActivity extends AppCompatActivity {

    Switch farmerOrTractor;

    public void getStarted(View view) {

        String farmerOrTractor = "farmer";
        if (farmerOrTractor.isChecked()) {
            farmerOrTractor = "tractor";
        }
        ParseUser.getCurrentUser().put("farmerOrTractor", farmerOrTractor);
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
//                    Log.i("MyApp", "User signed up");
                    redirectUser();
                }

            }
        });

    }


    public void redirectUser(){
        if (ParseUser.getCurrentUser().get("farmerOrTractor").equals("farmer")){
            Intent intent = new Intent(getApplicationContext(), YourLocation.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        farmerOrTractor = (Switch) findViewById(R.id.farmerOrTractor);


        if (ParseUser.getCurrentUser() == null) {
            ParseAnonymousUtils.logIn(new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e != null) {
                        Log.d("MyApp", "Anonymous login failed.");
                    } else {
                        Log.d("MyApp", "Anonymous user logged in.");
                    }

                }
            });
        } else {
            if (ParseUser.getCurrentUser().get("farmerOrTractor") != null) {
//                Log.i("MyApp", "Redirect User");
                redirectUser();
            }
        }

    }
}
