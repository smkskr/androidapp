package com.example.android.thenextbigthing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.android.thenextbigthing.R;
import com.example.android.thenextbigthing.helper.DataBaseHelper;
import com.example.android.thenextbigthing.utils.Riddles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


public class SplashActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        String jsonString = loadJSONFromAsset();
        dataBaseHelper.deleteTableEntries();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            JSONObject jsonObject = new JSONObject();
            Riddles riddle = new Riddles();
            for(int i = 0;i < jsonArray.length();i++){

                jsonObject = jsonArray.getJSONObject(i);
                riddle.setQuestion(jsonObject.getString("Q"));
                riddle.setAnswer(jsonObject.getString("A"));
                dataBaseHelper.addRiddles(riddle);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

    //read json from assets folder
    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("riddleList.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
