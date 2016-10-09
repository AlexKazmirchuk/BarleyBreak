package com.alexkaz.barleybreak;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GameFieldActivity extends AppCompatActivity {

    private FrameLayout gameFieldWrapper, indicatorWrapper;
    private PercentRelativeLayout allInfo;
    private PercentRelativeLayout stepInfo;

    private TextView txtBestScores, txtScores, txtLimitStep;
    public ProgressBar stepProgressBar;
    private ImageButton restartBtn;
    private GameFieldCanvas gameFieldCanvas;

    private Typeface digitTapeFace;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_field);
        initComp();
        loadFonts();
        initViewAnim();
        loadPrefs();
    }


    private void initComp(){
        digitTapeFace = Typeface.createFromAsset(getAssets(),"digital.TTF");
        
        stepInfo = (PercentRelativeLayout)findViewById(R.id.stepInfo);
        txtScores = (TextView)findViewById(R.id.txtScores);
        txtLimitStep = (TextView)findViewById(R.id.txtLimitStep);
        stepProgressBar = (ProgressBar) findViewById(R.id.stepProgressBar);

        restartBtn = (ImageButton)findViewById(R.id.restartBtn);

        allInfo = (PercentRelativeLayout)findViewById(R.id.all_info);
        txtBestScores = (TextView)findViewById(R.id.txtBestScores);
        indicatorWrapper = (FrameLayout)findViewById(R.id.indicatorPanel);
        IndicatorView indicatorView = new IndicatorView(this);
        indicatorWrapper.addView(indicatorView);

        gameFieldCanvas = new GameFieldCanvas(this);
        gameFieldCanvas.setIndicatorView(indicatorView);
        gameFieldWrapper = (FrameLayout)findViewById(R.id.gameFieldPanel);
        gameFieldWrapper.addView(gameFieldCanvas);

        txtBestScores.setText(String.valueOf(loadBestScores()));
        gameFieldCanvas.setTxtScores(txtScores);
        gameFieldCanvas.setTxtBestScores(txtBestScores);
    }

    private void loadFonts(){
        Typeface impactTypeFace = Typeface.createFromAsset(getAssets(), "impact.ttf");
        ((TextView)findViewById(R.id.allInfoTitle)).setTypeface(impactTypeFace);
        ((TextView)findViewById(R.id.bestScorePanelTitle)).setTypeface(impactTypeFace);
        ((TextView)findViewById(R.id.rightCollectedTitle)).setTypeface(impactTypeFace);
        txtBestScores.setTypeface(digitTapeFace);
        txtScores.setTypeface(impactTypeFace);
        txtLimitStep.setTypeface(impactTypeFace);
    }

    private void initViewAnim(){
        stepInfo.startAnimation(AnimationUtils.loadAnimation(this,R.anim.scores_anim));
        restartBtn.startAnimation(AnimationUtils.loadAnimation(this,R.anim.restart_btn_anim));
        allInfo.startAnimation(AnimationUtils.loadAnimation(this,R.anim.all_info_anim));
        txtBestScores.startAnimation(AnimationUtils.loadAnimation(this,R.anim.best_scores_anim));
        indicatorWrapper.startAnimation(AnimationUtils.loadAnimation(this,R.anim.indicator_anim));
        gameFieldWrapper.startAnimation(AnimationUtils.loadAnimation(this,R.anim.gamefield_anim));
    }

    private void loadPrefs() {
        int limitStep = getSharedPreferences(PreferencesActivity.MY_SETTING, Activity.MODE_PRIVATE).getInt(PreferencesActivity.DIFFICULTY,PreferencesActivity.MEDIUM);
        txtLimitStep.setText(String.valueOf(getString(R.string.txt_limit_text) + limitStep));
        gameFieldCanvas.setMinStepCount(limitStep);
        stepProgressBar.setMax(limitStep);
    }

    public int loadBestScores(){
        int record = getSharedPreferences(PreferencesActivity.MY_SETTING,Activity.MODE_PRIVATE).getInt(PreferencesActivity.BEST_RECORD,PreferencesActivity.BEST_RECORD_DEFAULT_VALUE);
        if (record == PreferencesActivity.BEST_RECORD_DEFAULT_VALUE){
            return 0;
        } else {
            return getSharedPreferences(PreferencesActivity.MY_SETTING,Activity.MODE_PRIVATE).getInt(PreferencesActivity.BEST_RECORD,PreferencesActivity.BEST_RECORD_DEFAULT_VALUE);
        }
    }

    public void onRestartBtnClick(View view) {
        txtScores.setText(R.string.scores);
        gameFieldCanvas.restartGame();
        stepProgressBar.setProgress(0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.first_to_right, R.anim.second_to_right);
    }

    public Typeface getDigitTapeFace(){
        return digitTapeFace;
    }
}
