package com.example.amankathed.spotface;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // handle pressing the back to main menu button
        Button back= (Button) findViewById(R.id.about_back_btn);
        back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), MainMenu.class));
                    finish();
                }
        });

        // load the about page to the webview
        WebView view = (WebView) findViewById(R.id.about_webview);
        view.loadUrl("file:///android_asset/about.html");
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(), MainMenu.class));
        finish();
        super.onBackPressed();
    }

}
