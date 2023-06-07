package ca.smartgate.it.automatedbarriergate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DELAY = 4000; // Time in milliseconds
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Create a handler to delay the splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the MainActivity after the splash delay
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close the splash screen activity
            }
        }, SPLASH_DELAY);
    }
}