package com.alexkaz.barleybreak;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class PreferencesActivity extends Activity implements View.OnClickListener {

    private TextView settingTitle;
    private Button diffBtn, musicSwitcherBtn, clearStatsBtn, aboutBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences);
        initComp();
        loadFont();
        setBtnListeners();
        loadPreferences();
        setAnim();
    }

    private void initComp() {
        settingTitle = (TextView)findViewById(R.id.settingTitle);

        diffBtn = (Button)findViewById(R.id.diffBtn);
        musicSwitcherBtn = (Button)findViewById(R.id.soundSwitcherBtn);
        clearStatsBtn = (Button)findViewById(R.id.clearStatsBtn);
        aboutBtn = (Button)findViewById(R.id.aboutBtn);
    }

    private void loadFont(){
        Typeface impactTypeFace = Typeface.createFromAsset(getAssets(), "impact.ttf");
        settingTitle.setTypeface(impactTypeFace);
        diffBtn.setTypeface(impactTypeFace);
        musicSwitcherBtn.setTypeface(impactTypeFace);
        clearStatsBtn.setTypeface(impactTypeFace);
        aboutBtn.setTypeface(impactTypeFace);
    }

    private void setBtnListeners(){
        diffBtn.setOnClickListener(this);
        musicSwitcherBtn.setOnClickListener(this);
        clearStatsBtn.setOnClickListener(this);
        aboutBtn.setOnClickListener(this);
    }

    private void setAnim() {
        settingTitle.startAnimation(AnimationUtils.loadAnimation(this,R.anim.scores_anim));

        diffBtn.startAnimation(AnimationUtils.loadAnimation(this,R.anim.restart_btn_anim));
        musicSwitcherBtn.startAnimation(AnimationUtils.loadAnimation(this,R.anim.all_info_anim));
        clearStatsBtn.startAnimation(AnimationUtils.loadAnimation(this,R.anim.best_scores_anim));
        aboutBtn.startAnimation(AnimationUtils.loadAnimation(this,R.anim.indicator_anim));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.first_to_right, R.anim.second_to_right);
    }

    @Override
    public void onClick(View v) { // TODO clean method
        switch (v.getId()){
            case R.id.diffBtn:
                switch (diffBtn.getText().toString()){
                    case "Difficulty: low":

                        diffBtn.setText(R.string.difficulty_medium);
                        getSharedPreferences(
                                LaunchActivity.MY_SETTING,
                                Activity.MODE_PRIVATE)
                                .edit()
                                .putInt(LaunchActivity.DIFFICULTY,200)
                                .apply();
                        break;
                    case "Difficulty: medium":

                        diffBtn.setText(R.string.difficulty_high);
                        getSharedPreferences(
                                LaunchActivity.MY_SETTING,
                                Activity.MODE_PRIVATE)
                                .edit()
                                .putInt(LaunchActivity.DIFFICULTY,100)
                                .apply();
                        break;
                    case "Difficulty: high":

                        diffBtn.setText(R.string.difficulty_low);
                        getSharedPreferences(
                                LaunchActivity.MY_SETTING,
                                Activity.MODE_PRIVATE)
                                .edit()
                                .putInt(LaunchActivity.DIFFICULTY,300)
                                .apply();
                        break;
                }
                break;
            case R.id.soundSwitcherBtn:
                switch (musicSwitcherBtn.getText().toString()){
                    case "Sound: on":
                        musicSwitcherBtn.setText(R.string.sound_off);
                        getSharedPreferences(
                                LaunchActivity.MY_SETTING,
                                Activity.MODE_PRIVATE)
                                .edit()
                                .putBoolean(LaunchActivity.SOUND_SWITCHER,false)
                                .apply();
                        break;
                    case "Sound: off":
                        musicSwitcherBtn.setText(R.string.sound_on);
                        getSharedPreferences(
                                LaunchActivity.MY_SETTING,
                                Activity.MODE_PRIVATE)
                                .edit()
                                .putBoolean(LaunchActivity.SOUND_SWITCHER,true)
                                .apply();
                        break;
                }
                break;
            case R.id.clearStatsBtn:
                getSharedPreferences(
                        LaunchActivity.MY_SETTING,
                        Activity.MODE_PRIVATE)
                        .edit()
                        .putInt(LaunchActivity.BEST_RECORD, 1000)
                        .apply();
                break;
            case R.id.aboutBtn:
                break;
        }
    }

    private void loadPreferences() { // TODO clean method

        String pref = "";
        switch (getSharedPreferences(LaunchActivity.MY_SETTING,Activity.MODE_PRIVATE).getInt(LaunchActivity.DIFFICULTY,200)){
            case 300:
//                +  getSharedPreferences(LaunchActivity.MY_SETTING,Activity.MODE_PRIVATE).contains(LaunchActivity.BEST_RECORD)
                pref = "low";
                break;
            case 200:
                pref = "medium";
                break;
            case 100:
                pref = "high";
                break;
        }
        diffBtn.setText(String.valueOf("Difficulty: " + pref));


        if (getSharedPreferences(LaunchActivity.MY_SETTING,Activity.MODE_PRIVATE).getBoolean(LaunchActivity.SOUND_SWITCHER,false)){
            musicSwitcherBtn.setText(R.string.sound_on);
        }
        else {
            musicSwitcherBtn.setText(R.string.sound_off);
        }
    }
}
