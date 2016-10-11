package com.alexkaz.barleybreak;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class PreferencesActivity extends Activity implements View.OnClickListener {

    public static final String MY_SETTING = "my_setting";
    public static final String BEST_RECORD = "bestRecord";
    public static final String SOUND_SWITCHER = "soundSwitcher";
    public static final String DIFFICULTY = "difficulty";

    public static final int LOW = 300;
    public static final int MEDIUM = 200;
    public static final int HIGH = 100;
    public static final int BEST_RECORD_DEFAULT_VALUE = 1000;

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
        aboutBtn.startAnimation(AnimationUtils.loadAnimation(this,R.anim.right_collected_panel_anim));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.first_to_right, R.anim.second_to_right);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == diffBtn.getId()){
            int difficulty = getDifficulty();
            if (difficulty == LOW){
                diffBtn.setText(R.string.difficulty_medium);
                setDifficulty(MEDIUM);
            } else if (difficulty == MEDIUM){
                diffBtn.setText(R.string.difficulty_high);
                setDifficulty(HIGH);
            } else if (difficulty == HIGH){
                diffBtn.setText(R.string.difficulty_low);
                setDifficulty(LOW);
            }
        } else if(v.getId() == musicSwitcherBtn.getId()){
            boolean musicMode = getMusicMode();
            if (musicMode){
                musicSwitcherBtn.setText(R.string.sound_off);
                setMusicMode(false);
            } else {
                musicSwitcherBtn.setText(R.string.sound_on);
                setMusicMode(true);
            }
        } else if(v.getId() == clearStatsBtn.getId()){
            getSharedPreferences(MY_SETTING,Activity.MODE_PRIVATE)
                    .edit()
                    .putInt(BEST_RECORD, BEST_RECORD_DEFAULT_VALUE)
                    .apply();
        } else if(v.getId() == aboutBtn.getId()){
            Log.d("myLog","About button pressed");
        }
    }

    private void loadPreferences() {
        int difficulty = getDifficulty();
        if (difficulty == LOW){
            diffBtn.setText(R.string.difficulty_low);
        } else if (difficulty == MEDIUM){
            diffBtn.setText(R.string.difficulty_medium);
        } else if (difficulty == HIGH){
            diffBtn.setText(R.string.difficulty_high);
        }

        boolean musicMode = getMusicMode();
        if (musicMode){
            musicSwitcherBtn.setText(R.string.sound_on);
        } else {
            musicSwitcherBtn.setText(R.string.sound_off);
        }
    }

    private void setDifficulty(int difficulty){
        getSharedPreferences(MY_SETTING,Activity.MODE_PRIVATE)
                .edit()
                .putInt(DIFFICULTY,difficulty)
                .apply();
    }

    private int getDifficulty(){
        return getSharedPreferences(MY_SETTING,Activity.MODE_PRIVATE).getInt(DIFFICULTY,MEDIUM);
    }

    private void setMusicMode(boolean mode){
        getSharedPreferences(MY_SETTING,Activity.MODE_PRIVATE)
                .edit()
                .putBoolean(SOUND_SWITCHER,mode)
                .apply();
    }

    private boolean getMusicMode(){
        return getSharedPreferences(MY_SETTING,Activity.MODE_PRIVATE).getBoolean(SOUND_SWITCHER,true);
    }
}
