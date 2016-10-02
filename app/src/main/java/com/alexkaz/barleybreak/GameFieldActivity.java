package com.alexkaz.barleybreak;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GameFieldActivity extends AppCompatActivity {

    private FrameLayout gameFieldPanel, indicatorPanel;
    private PercentRelativeLayout allInfo;
    private PercentRelativeLayout stepInfo;

    private TextView txtBestScores, txtScores, txtLimitStep;
    public ProgressBar stepProgressBar;
    private Button restartBtn;
    private GameFieldCanvas gameFieldCanvas;

    private Typeface digitTapeFace;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_field);
        initComp();
        loadFonts();
        initAndStartAnim();
        loadPrefs();
    }


    private void initComp(){
        digitTapeFace = Typeface.createFromAsset(getAssets(),"digital.TTF");
        
        stepInfo = (PercentRelativeLayout)findViewById(R.id.stepInfo);
        txtScores = (TextView)findViewById(R.id.txtScores);
        txtLimitStep = (TextView)findViewById(R.id.txtLimitStep);
        stepProgressBar = (ProgressBar) findViewById(R.id.stepProgressBar);

        restartBtn = (Button)findViewById(R.id.restartBtn);

        allInfo = (PercentRelativeLayout)findViewById(R.id.all_info);
        txtBestScores = (TextView)findViewById(R.id.txtBestScores);
        indicatorPanel = (FrameLayout)findViewById(R.id.indicatorPanel);
        IndicatorView indicatorView = new IndicatorView(this);
        indicatorPanel.addView(indicatorView);

        gameFieldCanvas = new GameFieldCanvas(this);
        gameFieldCanvas.setIndicatorView(indicatorView);
        gameFieldPanel = (FrameLayout)findViewById(R.id.gameFieldPanel);
        gameFieldPanel.addView(gameFieldCanvas);

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
        restartBtn.setTypeface(impactTypeFace);
    }

    private void initAndStartAnim(){
        stepInfo.startAnimation(AnimationUtils.loadAnimation(this,R.anim.scores_anim));
        restartBtn.startAnimation(AnimationUtils.loadAnimation(this,R.anim.restart_btn_anim));
        allInfo.startAnimation(AnimationUtils.loadAnimation(this,R.anim.all_info_anim));
        txtBestScores.startAnimation(AnimationUtils.loadAnimation(this,R.anim.best_scores_anim));
        indicatorPanel.startAnimation(AnimationUtils.loadAnimation(this,R.anim.indicator_anim));
        gameFieldPanel.startAnimation(AnimationUtils.loadAnimation(this,R.anim.gamefield_anim));
    }

    private void loadPrefs() {
        int limitStep = getSharedPreferences(LaunchActivity.MY_SETTING, Activity.MODE_PRIVATE).getInt(LaunchActivity.DIFFICULTY,200);
        txtLimitStep.setText(String.valueOf(getString(R.string.txt_limit_text) + limitStep));
        gameFieldCanvas.setMinStepCount(limitStep);
        stepProgressBar.setMax(limitStep);
    }

    public int loadBestScores(){
        int record = getSharedPreferences(LaunchActivity.MY_SETTING,Activity.MODE_PRIVATE).getInt(LaunchActivity.BEST_RECORD,1);
        if (record == 1000){
            return 0;
        } else {
            return getSharedPreferences(LaunchActivity.MY_SETTING,Activity.MODE_PRIVATE).getInt(LaunchActivity.BEST_RECORD,1);
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
