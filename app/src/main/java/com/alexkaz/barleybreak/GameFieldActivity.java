package com.alexkaz.barleybreak;


import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameFieldActivity extends AppCompatActivity {
    private FrameLayout gameFieldPanel, indicatorPanel;
    private LinearLayout allInfo;
    private RelativeLayout stepInfo;

    private TextView txtBestScores, txtScores, txtLimitStep;
    private Button restartBtn;

    public ProgressBar stepProgressBar;

    private GameFieldCanvas gameFieldCanvas;
    private IndicatorView indicatorView;

    private Typeface digitTapeFace;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_field);

        initComp();
        setAnim();
        loadPrefs();
    }




    private void initComp(){
        digitTapeFace = Typeface.createFromAsset(getAssets(),"digital.TTF");
        gameFieldPanel = (FrameLayout)findViewById(R.id.gameFieldPanel);
        indicatorPanel = (FrameLayout)findViewById(R.id.indicatorPanel);
        allInfo = (LinearLayout)findViewById(R.id.all_info);
        stepInfo = (RelativeLayout)findViewById(R.id.stepInfo);

        txtScores = (TextView)findViewById(R.id.txtScores);
        txtBestScores = (TextView)findViewById(R.id.txtBestScores);
        txtLimitStep = (TextView)findViewById(R.id.txtLimitStep);

        stepProgressBar = (ProgressBar) findViewById(R.id.stepProgressBar);

        txtBestScores.setText(String.valueOf(loadBestScores()));

        restartBtn = (Button)findViewById(R.id.restartBtn);

        gameFieldCanvas = new GameFieldCanvas(this);
        gameFieldCanvas.setTxtScores(txtScores);
        gameFieldCanvas.setTxtBestScores(txtBestScores);

        indicatorView = new IndicatorView(this);
        gameFieldCanvas.setIndicatorView(indicatorView);

        gameFieldPanel.addView(gameFieldCanvas);
        indicatorPanel.addView(indicatorView);
    }

    private void setAnim(){
        stepInfo.startAnimation(AnimationUtils.loadAnimation(this,R.anim.scores_anim));
        restartBtn.startAnimation(AnimationUtils.loadAnimation(this,R.anim.restart_btn_anim));
        allInfo.startAnimation(AnimationUtils.loadAnimation(this,R.anim.all_info_anim));
        txtBestScores.startAnimation(AnimationUtils.loadAnimation(this,R.anim.best_scores_anim));
        indicatorPanel.startAnimation(AnimationUtils.loadAnimation(this,R.anim.indicator_anim));
        gameFieldPanel.startAnimation(AnimationUtils.loadAnimation(this,R.anim.gamefield_anim));
    }

    public void onRestart(View view) {
        txtScores.setText(R.string.scores);
        gameFieldCanvas.restartGame();
        stepProgressBar.setProgress(0);
    }


    private void loadPrefs() {
        int limitStep = 0;
        switch (getSharedPreferences(LaunchActivity.MY_SETTING, Activity.MODE_PRIVATE).getInt(LaunchActivity.DIFFICULTY,0)){
            case 0:
                limitStep = 800;
                break;
            case 1:
                limitStep = 500;
                break;
            case 2:
                limitStep = 300;
                break;
        }
        txtLimitStep.setText("STEPS:" + limitStep);
        gameFieldCanvas.setMinStepCount(limitStep);
        stepProgressBar.setMax(limitStep);

    }

    private int loadBestScores(){
        return getSharedPreferences(LaunchActivity.MY_SETTING,Activity.MODE_PRIVATE).getInt(LaunchActivity.BEST_RECORD,1);
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
