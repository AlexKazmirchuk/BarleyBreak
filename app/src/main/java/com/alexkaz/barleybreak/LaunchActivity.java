package com.alexkaz.barleybreak;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;

public class LaunchActivity extends AppCompatActivity {

    public static final String MY_SETTING = "my_setting";
    public static final String BEST_RECORD = "bestRecord";
    public static final String SOUND_SWITCHER = "soundSwitcher";
    public static final String DIFFICULTY = "difficulty";

    private Button newGame, options, exit;
    private FrameLayout gameTitle, gameTitlePicture;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initComp();
        loadFonts();
        setAnim();
        createPrefs();
    }

    private void initComp(){
        gameTitle = (FrameLayout)findViewById(R.id.gameTitle);
        gameTitlePicture = (FrameLayout)findViewById(R.id.game_title_picture);

        newGame = (Button)findViewById(R.id.newGame);
        options = (Button)findViewById(R.id.options);
        exit = (Button)findViewById(R.id.exit);
    }

    private void loadFonts(){
        String lang = getResources().getConfiguration().locale.getLanguage();
        Typeface btnFont;
        if(lang.equals("en")){
            btnFont = Typeface.createFromAsset(getAssets(), "NEURAL2.TTF");
        } else {
            btnFont = Typeface.createFromAsset(getAssets(), "impact.ttf");
        }

        newGame.setTypeface(btnFont);
        options.setTypeface(btnFont);
        exit.setTypeface(btnFont);
    }

    private void setAnim(){
        gameTitle.startAnimation(AnimationUtils.loadAnimation(this,R.anim.game_title_anim));
        gameTitlePicture.startAnimation(AnimationUtils.loadAnimation(this,R.anim.game_title_picture));

        newGame.startAnimation(AnimationUtils.loadAnimation(this,R.anim.new_game_anim));
        options.startAnimation(AnimationUtils.loadAnimation(this,R.anim.options_anim));
        exit.startAnimation(AnimationUtils.loadAnimation(this,R.anim.exit_anim));
    }

    private void createPrefs() {
        SharedPreferences mySettings = getSharedPreferences(MY_SETTING, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySettings.edit();

        if (!mySettings.contains(BEST_RECORD)) {
            editor.putInt(BEST_RECORD,1000);
            editor.apply();
        }

        if (!mySettings.contains(SOUND_SWITCHER)) {
            editor.putBoolean(SOUND_SWITCHER,true);
            editor.apply();
        }

        if (!mySettings.contains(DIFFICULTY)) {
            editor.putInt(DIFFICULTY,200);
            editor.apply();
        }
    }


    public void startGame(View view) {
        Intent intent = new Intent(this,GameFieldActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.second_to_left, R.anim.first_to_left);
    }

    public void startOptions(View view) {
        Intent intent = new Intent(this,PreferencesActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.second_to_left, R.anim.first_to_left);
    }

    public void startExit(View view) {
        finish();
    }
}
