package com.example.amankathed.spotface;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.amankathed.spotface.Activities.Authenticate.Recognition;
import com.example.amankathed.spotface.Activities.Register.Capture;
import com.example.amankathed.spotface.Activities.Settings.Settings;
import com.example.amankathed.spotface.Activities.ViewAll.ViewAll;
import com.example.amankathed.spotface.Utils.SharedPrefs;


public class MainMenu extends AppCompatActivity {

    private static final int PERMISSIONS_CAMERA = 1;
    Button auth,register,settings,view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        auth= (Button) findViewById(R.id.auth_with_sp_btn);
        register= (Button) findViewById(R.id.register_face_btn);
        settings = (Button) findViewById(R.id.settings_btn);
        view= (Button) findViewById(R.id.view_btn);
        Button about = (Button) findViewById(R.id.about_btn);
        auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Recognition.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Capture.class));
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Settings.class));
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewAll.class));
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(getApplicationContext(), About.class));
                                    }
                                });

        // ensure user agrees to terms
        SharedPreferences prefs = getSharedPreferences("first_run", MODE_PRIVATE);
        if (!prefs.getBoolean("hasRanBefore", false)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainMenu.this);
            builder.setTitle("First Run");
            builder.setMessage("Please note that this app stores data with Kairos.com, and by using this app you agree to their privacy policy, and terms and conditions." +
                    " For more information, see the About page.");
            builder.setIcon(R.drawable.lotte);
            builder.setPositiveButton("OK", null);
            builder.setCancelable(false);
            AlertDialog alert = builder.create();
            alert.show();

            // set the preference so it won't run again
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("hasRanBefore", true);
            editor.commit();
        }


        // get camera permissions

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSIONS_CAMERA);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // all good
                } else {

                    // permission denied, display a message and exit

                    Context context = getApplicationContext();
                    CharSequence text = "Camera permission is required to run this app. The app will now exit.";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 2000);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onBackPressed(){
        finish();
        super.onBackPressed();
    }
}
