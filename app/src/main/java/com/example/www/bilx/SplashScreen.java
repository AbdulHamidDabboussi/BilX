package com.example.www.bilx;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;



/**
 * This class displays the splash screen in the app therefore, is the first point of contact for the user.
 * @author Hanzallah Burney
 * @version 1.00 21-03-2018
 */

public class SplashScreen extends AppCompatActivity{
    private static int SPLASH_TIME_OUT = 2000;
    private ImageView logo_view;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        // SVG to animate
        logo_view = findViewById(R.id.logo);

        // Animation Object
        Animation fade = AnimationUtils.loadAnimation(this,R.anim.splash_animation);

        // Start Animation
        logo_view.startAnimation(fade);

        // Run login activity after 2000 ms
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Intent to change activity.
                final Intent login = new Intent(SplashScreen.this, Login_Activity.class);

                // Start the next activity
                startActivity(login);


                // Close this activity
                finish();

                // Transition animation between activities
                overridePendingTransition(R.anim.splash_animation, R.anim.fadeout);
            }
        },SPLASH_TIME_OUT);
    }
}
