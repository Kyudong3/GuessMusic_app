package com.kyudong.guessmusic;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kyudong.guessmusic.MusicAcitivy.MusicMain;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button musicBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findId();
        setClickListener();

    }

    public void findId() {
        musicBtn = (Button) findViewById(R.id.musicBtn);
    }

    public void setClickListener() {
        musicBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.musicBtn:
                Intent intentMusicActivity = new Intent(getApplicationContext(),MusicMain.class);
                startActivity(intentMusicActivity);
                break;
        }
    }
}
