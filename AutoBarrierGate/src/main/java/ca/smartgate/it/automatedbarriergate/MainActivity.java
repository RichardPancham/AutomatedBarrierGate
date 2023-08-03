//Jaspreet Heer n01315290, Richard Pancham n01373454, Section 0NA

package ca.smartgate.it.automatedbarriergate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import android.Manifest;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements LocationFragment.OnParkingSpotSelectedListener,PaymentFragment.OnPaymentSelectedListener{
    private Fragment paymentFragment;
    private Fragment locationFragment;
    private Fragment aboutFragment;

    private  Fragment settingsFragment;
    private  Fragment barrierFragment;
    private  Fragment reviewsFragment;
    private  Fragment selectParkingOption;
    private  Fragment selectPayment;

    private boolean isOptionSelected = false;

    private static final int SPLASH_DELAY = 2000; // Time in milliseconds
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean OptionSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paymentFragment = new PaymentFragment();
        locationFragment = new LocationFragment();
        barrierFragment = new BarrierOpenAndClose();
        settingsFragment = new SettingsFragment();
        reviewsFragment = new ReviewsFragment();
        aboutFragment = new AboutFragment();
        selectParkingOption = new SelectParkingFragment();
        selectPayment = new selectPayment();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.payment:
                        if (isOptionSelected) {
                            switchFragment(paymentFragment);
                        } else {
                            // If a parking spot is not selected, show a blank fragment or a message fragment
                            // For now, we will display the locationFragment again as a placeholder
                            switchFragment(selectParkingOption);
                        }
                        return true;
                    case R.id.location:
                        switchFragment(locationFragment);
                        return true;
                    case R.id.open_barrier:

                        if (OptionSelected) {
                            switchFragment(barrierFragment);
                        } else {

                            switchFragment(selectPayment);
                        }
                        return true;


                }
                return false;
            }
        });




        // Set the initial fragment
        switchFragment(locationFragment);




    }

    // Inflate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        // Handle menu item clicks here
        switch (item.getItemId()) {



                    case R.id.settings:
                        switchFragment(settingsFragment);
                        return true;
                    case R.id.web:
                        String url = "https://parklio.com/en/blog/top-9-best-automatic-parking-barriers"; // Replace with your desired website URL

                        Uri webpage = Uri.parse(url);
                        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                        startActivity(webIntent);
                        // Handle menu item 2 click
                        return true;
                    case R.id.dialer:

                        // Check if the CALL_PHONE permission is granted
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                            // Permission already granted, proceed with dialing
                            dialPhoneNumber();
                        } else {
                            // Request permission
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, LOCATION_PERMISSION_REQUEST_CODE);
                        }
                        return true;
                    case R.id.about:
                        switchFragment(aboutFragment);
                        return true;
                    case R.id.reviews:
                         switchFragment(reviewsFragment);
                         return  true;
                    default:
                        return super.onOptionsItemSelected(item);


            // Handle your menu items here


    } }

    public void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.ExitPrompt);
        builder.setCancelable(false);

        //set image for AlertDialog
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.android);
        builder.setView(imageView);
        builder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Exit the app
                finish();
            }
        });

        builder.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Stay on the app
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    @Override
    public void onParkingSpotSelected(boolean isSelected) {
        isOptionSelected = isSelected;
    }


    @Override
    public void onPaymentSelected(boolean isSelected) {
        OptionSelected = isSelected;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Load the appropriate layout based on the new configuration
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with dialing
                dialPhoneNumber();
            } else {
                // Permission denied, show a message or take appropriate action
                Toast.makeText(this, "Permission denied. Cannot make a call.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void dialPhoneNumber() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        String phoneNumber = "1234567890"; // Replace with the desired phone number
        Uri phoneUri = Uri.parse("tel:" + phoneNumber);
        intent.setData(phoneUri);
        startActivity(intent);
    }
}