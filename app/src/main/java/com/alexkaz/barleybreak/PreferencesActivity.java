package com.alexkaz.barleybreak;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class PreferencesActivity extends Activity implements View.OnClickListener{
    private TextView settingTitle;

    private Button diffBtn, musicSwitcherBtn, clearStatsBtn, aboutBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences);
        initComp();
        loadPreferences();
        setAnim();
    }



    private void initComp() {
        settingTitle = (TextView)findViewById(R.id.settingTitle);

        diffBtn = (Button)findViewById(R.id.diffBtn);
        musicSwitcherBtn = (Button)findViewById(R.id.soundSwitherBtn);
        clearStatsBtn = (Button)findViewById(R.id.clearStatsBtn);
        aboutBtn = (Button)findViewById(R.id.aboutBtn);

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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.diffBtn:
                switch (diffBtn.getText().toString()){
                    case "Difficulty: low":

                        diffBtn.setText("Difficulty: medium");
                        getSharedPreferences(
                                LaunchActivity.MY_SETTING,
                                Activity.MODE_PRIVATE)
                                .edit()
                                .putInt(LaunchActivity.DIFFICULTY,1)
                                .apply();
                        break;
                    case "Difficulty: medium":

                        diffBtn.setText("Difficulty: high");
                        getSharedPreferences(
                                LaunchActivity.MY_SETTING,
                                Activity.MODE_PRIVATE)
                                .edit()
                                .putInt(LaunchActivity.DIFFICULTY,2)
                                .apply();
                        break;
                    case "Difficulty: high":

                        diffBtn.setText("Difficulty: low");
                        getSharedPreferences(
                                LaunchActivity.MY_SETTING,
                                Activity.MODE_PRIVATE)
                                .edit()
                                .putInt(LaunchActivity.DIFFICULTY,0)
                                .apply();
                        break;
                }
                break;
            case R.id.soundSwitherBtn:
                switch (musicSwitcherBtn.getText().toString()){
                    case "Sound: on":
                        musicSwitcherBtn.setText("Sound: off");
                        getSharedPreferences(
                                LaunchActivity.MY_SETTING,
                                Activity.MODE_PRIVATE)
                                .edit()
                                .putBoolean(LaunchActivity.SOUND_SWITCHER,false)
                                .apply();
                        break;
                    case "Sound: off":
                        musicSwitcherBtn.setText("Sound: on");
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

    private void loadPreferences() {

        String pref = "";
        switch (getSharedPreferences(LaunchActivity.MY_SETTING,Activity.MODE_PRIVATE).getInt(LaunchActivity.DIFFICULTY,0)){
            case 0:
//                +  getSharedPreferences(LaunchActivity.MY_SETTING,Activity.MODE_PRIVATE).contains(LaunchActivity.BEST_RECORD)
                pref = "low";
                break;
            case 1:
                pref = "medium";
                break;
            case 2:
                pref = "high";
                break;
        }
        diffBtn.setText("Difficulty: " + pref);


        if (getSharedPreferences(LaunchActivity.MY_SETTING,Activity.MODE_PRIVATE).getBoolean(LaunchActivity.SOUND_SWITCHER,false)){
            musicSwitcherBtn.setText("Sound: on");
        }
        else {
            musicSwitcherBtn.setText("Sound: off");
        }
    }
}
