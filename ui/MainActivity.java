package com.example.android.thenextbigthing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.example.android.thenextbigthing.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton newGame,highScore;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGui();
    }

    private void initGui(){
        newGame = (AppCompatButton)findViewById(R.id.btn_new_game);
        highScore = (AppCompatButton)findViewById(R.id.btn_high_score);

        newGame.setOnClickListener(this);
        highScore.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_new_game:intent = new Intent(MainActivity.this,NewGameActivity.class);startActivity(intent);break;
            case R.id.btn_high_score:intent = new Intent(MainActivity.this,HighScoreActivity.class);startActivity(intent);break;
        }
    }
}
