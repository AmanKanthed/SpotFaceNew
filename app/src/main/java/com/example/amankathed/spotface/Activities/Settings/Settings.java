package com.example.amankathed.spotface.Activities.Settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import com.example.amankathed.spotface.MainMenu;
import com.example.amankathed.spotface.R;
import com.example.amankathed.spotface.Utils.Connection;
import com.example.amankathed.spotface.Utils.Constants;
import okhttp3.OkHttpClient;

public class Settings extends AppCompatActivity {

    Button reset,back;
    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build();
    LoadToast loadToast;
    Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        reset= (Button) findViewById(R.id.reset_btn);
        back= (Button) findViewById(R.id.back);
        loadToast=new LoadToast(this);
        loadToast.setText("Resetting...");
        connection=new Connection(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainMenu.class));
                finish();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                builder.setTitle(String.format("%1$s","RESET TRAIN DATA"));
                builder.setMessage("This action will delete all the trained data !\n" +
                        "Proceed ?\n" +
                        "Recommended: No");
                builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            if (connection.isInternet()){
                                resettraindata("errorlabs-spotface");
                            }else {
                                Toast.makeText(getApplicationContext(),"No Internet",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNegativeButton("No",null);
                builder.setIcon(R.drawable.lotte);
                AlertDialog welcomeAlert = builder.create();
                welcomeAlert.show();
                ((TextView) welcomeAlert.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
            }
        });
    }

    public void resettraindata(String galleryname) throws JSONException {
        loadToast.show();
        JSONObject obj = new JSONObject();
        obj.put("gallery_name",galleryname);
        AndroidNetworking.post(Constants.reset)
                .addHeaders("Content-Type","application/json")
                .addHeaders("app_id", Constants.app_id)
                .addHeaders("app_key",Constants.app_key)
                .addJSONObjectBody(obj)
                .setPriority(Priority.HIGH)
                .setOkHttpClient(okHttpClient)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String status = null;
                        try {
                            if (!response.has("Errors")){
                                status = response.getString("status");
                                if (status.equals("Complete")){
                                    Toast.makeText(getApplicationContext(),"Reset Successfull",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),MainMenu.class));
                                    finish();
                                }else {
                                    Toast.makeText(getApplicationContext(),"Reset Failed",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),MainMenu.class));
                                    finish();
                                }
                            }else {
                                JSONArray errors =null;
                                JSONObject ind=null;
                                try {
                                    errors=response.getJSONArray("Errors");
                                    ind = errors.getJSONObject(0);
                                    String errorcode=ind.getString("ErrCode");
                                    if (errorcode.equals("5004")){
                                        Toast.makeText(getApplicationContext(),"Reset Successfull",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),MainMenu.class));
                                        finish();
                                    }else {
                                        Toast.makeText(getApplicationContext(), "Try again later", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), MainMenu.class));
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        loadToast.error();
                        Toast.makeText(getApplicationContext(),"Reset failed, Try again later",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainMenu.class));
                        finish();
                    }
                });
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(),MainMenu.class));
        finish();
        super.onBackPressed();
    }
}