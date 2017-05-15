package com.example.amankathed.spotface.Activities.Authenticate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amankathed.spotface.MainMenu;
import com.example.amankathed.spotface.R;
import com.example.amankathed.spotface.Utils.SharedPrefs;


public class Result extends AppCompatActivity {

    ImageView img;
    Button cancel;
    TextView name,confidence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        img = (ImageView) findViewById(R.id.img_view);
        cancel = (Button) findViewById(R.id.cancel);
        name= (TextView) findViewById(R.id.name);
        confidence= (TextView) findViewById(R.id.conf);
        Bundle extras = getIntent().getExtras();
        final byte[] byteArray = extras.getByteArray("imgg");
        String name_id=extras.getString("name");
        String conf=extras.getString("conf");
        float p = Float.parseFloat(conf);

        // the confidence percentage
        float fp = p*100;


        // check if meets minimum level
        SharedPrefs prefs = new SharedPrefs(this);
        Float minPercentage = Float.parseFloat(prefs.getPer_prefs());
        if (fp < minPercentage){
            name.setText(name_id);
            name.setTextColor(getResources().getColor(R.color.red));
            confidence.setText(String.format("Confidence below threshold! (%.2f%%)", fp));
        } else {
            name.setText("Successfully recognized as: " + name_id);
            confidence.setText(String.format("Confidence: %.2f%%", fp));
        }
        // Toast.makeText(getApplicationContext(),v+" %",Toast.LENGTH_SHORT).show();
        Bitmap bmpp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        Bitmap bmp = Bitmap.createBitmap(bmpp, 0, 0, bmpp.getWidth(), bmpp.getHeight(), null, true);
        img.setImageBitmap(bmp);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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