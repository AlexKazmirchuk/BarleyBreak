package com.alexkaz.barleybreak;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alexkaz.barleybreak.raws.CellPositionManager;

public class GameFieldCanvas extends View {

    //Свойства
    private CellPositionManager cellPositionManager;

    private GameFieldActivity context;
    private TextView txtScores;
    private TextView txtBestScores;
    private IndicatorView indicatorView;
    private Typeface impactFont;

    private SoundPool clickSound;
    private SoundPool gameWinSound;
    private SoundPool gameOverSound;


    private int rightOrder;
    private int stepCount;
    private int minStepCount = 0;

    public GameFieldCanvas(Context context) {
        super(context);
        this.context = (GameFieldActivity) context;

        setOwnParameters();
        initComponents();
    }

    //Методы
    private void initComponents() {
        cellPositionManager = new CellPositionManager(context);
        impactFont = Typeface.createFromAsset(context.getAssets(), "impact.ttf");
        initSoundPools();
    }

    @SuppressWarnings("deprecation")
    private void initSoundPools(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            clickSound = new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .build();
            gameWinSound = new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .build();
            gameOverSound = new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .build();
        } else {
            clickSound = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
            gameWinSound = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
            gameOverSound = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        }

        clickSound.load(context,R.raw.click, 1);
        gameWinSound.load(context,R.raw.win_sound, 1);
        gameOverSound.load(context,R.raw.game_over_sound, 1);
    }

    private void checkRecord(int realCount) {
        int bestRecord = context.getSharedPreferences(PreferencesActivity.MY_SETTING, Activity.MODE_PRIVATE).getInt(PreferencesActivity.BEST_RECORD,PreferencesActivity.BEST_RECORD_DEFAULT_VALUE);
        if (bestRecord >= realCount){
            context.getSharedPreferences(
                    PreferencesActivity.MY_SETTING,
                    Activity.MODE_PRIVATE)
                    .edit()
                    .putInt(PreferencesActivity.BEST_RECORD,realCount)
                    .apply();
        }
    }

    private int getOrderedCellsCount(){
        int count  = 1;
        int orderedCount = 0;

        for (int i = 0; i < cellPositionManager.cells.length; i++) {
            for (int j = 0; j < cellPositionManager.cells[i].length; j++) {
                int id = cellPositionManager.cells[i][j].id;
                int buf = id/cellPositionManager.cells[i].length;
                if(id%cellPositionManager.cells[i].length == 0){
                    buf -=1;
                }
                if(id == count && i == buf){
                    orderedCount += 1;
                    count++;
                }
                else {
                    break;
                }
            }
        }
        return orderedCount;
    }

    public void restartGame(){
        cellPositionManager.initComponents();
        stepCount = 0;
        rightOrder = 0;
        indicatorView.setRightOrder(rightOrder);
        indicatorView.invalidate();
        invalidate();
    }

    public void setTxtScores(TextView txtScores) {
        this.txtScores = txtScores;
    }

    public void setTxtBestScores(TextView txtBestScores) {
        this.txtBestScores = txtBestScores;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        cellPositionManager.draw(canvas);
    }

    private OnClickListener createOnClickListener(final Dialog myDialog){
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.cancel();
                restartGame();
                gameWinSound.pause(1);
                Toast.makeText(context,"Game restarted",Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void youWonDialog(){
        View alertView = context.getLayoutInflater().inflate(R.layout.win_dialog, new LinearLayout(context));

        final Dialog myDialog = new Dialog(context, R.style.CustomAlertDialog);
        myDialog.setContentView(alertView);

        Button alertBtn = (Button) alertView.findViewById(R.id.alertBtn);
        alertBtn.setOnClickListener(createOnClickListener(myDialog));
        alertBtn.setTypeface(impactFont);
        myDialog.show();
    }

    private  void youLoseDialog(){
        View alertView = context.getLayoutInflater().inflate(R.layout.lose_dialog, new LinearLayout(context));

        final Dialog myDialog = new Dialog(context, R.style.CustomAlertDialog);
        myDialog.setContentView(alertView);

        Button alertBtn = (Button) alertView.findViewById(R.id.alertLoseBtn);
        alertBtn.setOnClickListener(createOnClickListener(myDialog));
        alertBtn.setTypeface(impactFont);
        myDialog.show();
    }

    public void setIndicatorView(IndicatorView indicatorView) {
        this.indicatorView = indicatorView;
    }

    public void setMinStepCount(int minStepCount) {
        this.minStepCount = minStepCount;
    }

    private void setOwnParameters() {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                if(e.getAction() == MotionEvent.ACTION_DOWN){
                    boolean isCellsSwapped = cellPositionManager.exchangeCells( Math.round(e.getX()), Math.round(e.getY()));
                    playClickSound(isCellsSwapped);
                    invalidate();
                    checkIfSorted();
                    checkIfStepsEnd();
                    refreshIndicator();
                }
                return true;
            }
        });
    }

    private void playClickSound(boolean isCellsSwapped){
        if (isCellsSwapped){
            if(context.getSharedPreferences(PreferencesActivity.MY_SETTING, Activity.MODE_PRIVATE).getBoolean(PreferencesActivity.SOUND_SWITCHER,false)){
                clickSound.play(1,1,1,0,0,0);
            }
            stepCount++;
        }
    }

    private void checkIfSorted(){
        boolean isSorted = cellPositionManager.verifyOrder();
        if (isSorted){
            checkRecord(stepCount);
            youWonDialog();
            boolean isSoundOn = context.getSharedPreferences(PreferencesActivity.MY_SETTING,Activity.MODE_PRIVATE).getBoolean(PreferencesActivity.SOUND_SWITCHER,false);
            if(isSoundOn){
                gameWinSound.play(1,1,1,0,0,0);
            }
            resetScores();
        }
        else {
            txtScores.setText(String.valueOf(context.getString(R.string.txt_scores_text) + stepCount));
            context.stepProgressBar.setProgress(stepCount);
        }
    }

    private void checkIfStepsEnd(){
        if (stepCount == minStepCount){
            youLoseDialog();
            boolean isSoundOn = context.getSharedPreferences(PreferencesActivity.MY_SETTING,Activity.MODE_PRIVATE).getBoolean(PreferencesActivity.SOUND_SWITCHER,false);
            if(isSoundOn){
                gameOverSound.play(1,1,1,0,0,0);
            }
            resetScores();
        }
    }

    private void refreshIndicator(){
        rightOrder = getOrderedCellsCount();
        indicatorView.setRightOrder(rightOrder);
        indicatorView.invalidate();
    }

    private void resetScores(){
        stepCount = 0;
        txtBestScores.setText(String.valueOf(context.loadBestScores()));
        txtScores.setText(String.valueOf(context.getString(R.string.txt_scores_text) + 0));
        context.stepProgressBar.setProgress(0);
    }
}
