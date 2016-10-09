package com.alexkaz.barleybreak;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
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

    private SoundPool soundPool;   //rename
    private SoundPool soundPool2;  //rename
    private SoundPool soundPool3;  //rename


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
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        soundPool.load(context,R.raw.click, 1);

        soundPool2 = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        soundPool2.load(context,R.raw.win_sound, 1);

        soundPool3 = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        soundPool3.load(context,R.raw.game_over_sound, 1);
    }

    private void setOwnParameters() { // TODO clean method
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                switch (e.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if (cellPositionManager.exchangeCells( Math.round(e.getX()), Math.round(e.getY()))){

                            if(context.getSharedPreferences(LaunchActivity.MY_SETTING, Activity.MODE_PRIVATE).getBoolean(LaunchActivity.SOUND_SWITCHER,false)){
                                soundPool.play(1,1,1,0,0,0);
                            }
                            stepCount++;
                        }
                        invalidate();

                        boolean isSorted =  cellPositionManager.verifyOrder();

                        if (isSorted){
                            checkRecord(stepCount);
                            youWonDialog();
                            boolean isSoundOn = context.getSharedPreferences(LaunchActivity.MY_SETTING,Activity.MODE_PRIVATE).getBoolean(LaunchActivity.SOUND_SWITCHER,false);
                            if(isSoundOn){
                                soundPool2.play(1,1,1,0,0,0);
                            }
                            stepCount = 0;
                            txtBestScores.setText(String.valueOf(context.loadBestScores()));
                            txtScores.setText(String.valueOf(context.getString(R.string.txt_scores_text) + 0));
                            context.stepProgressBar.setProgress(0);
                        }
                        else {
                            txtScores.setText(String.valueOf(context.getString(R.string.txt_scores_text) + stepCount));
                            context.stepProgressBar.setProgress(stepCount);
                        }

                        // дописати
                        if (stepCount == minStepCount){
                            youLoseDialog();
                            boolean isSoundOn = context.getSharedPreferences(LaunchActivity.MY_SETTING,Activity.MODE_PRIVATE).getBoolean(LaunchActivity.SOUND_SWITCHER,false);
                            if(isSoundOn){
                                soundPool3.play(1,1,1,0,0,0);
                            }


                            stepCount = 0;
//                            txtBestScores.setText(String.valueOf(context.getSharedPreferences(LaunchActivity.MY_SETTING,Activity.MODE_PRIVATE).getInt(LaunchActivity.BEST_RECORD,1000)));
                            txtBestScores.setText(String.valueOf(context.loadBestScores()));
                            txtScores.setText(String.valueOf(context.getString(R.string.txt_scores_text) + 0));
                            context.stepProgressBar.setProgress(0);
                        }

                        getOrderedCellsCount();
                        indicatorView.setRightOrder(rightOrder);
                        indicatorView.invalidate();
                        break;
                }
                return true;
            }
        });
    }

    private void checkRecord(int realCount) {
        int bestRecord = context.getSharedPreferences(LaunchActivity.MY_SETTING, Activity.MODE_PRIVATE).getInt(LaunchActivity.BEST_RECORD,1000);
        if (bestRecord >= realCount){
            context.getSharedPreferences(
                    LaunchActivity.MY_SETTING,
                    Activity.MODE_PRIVATE)
                    .edit()
                    .putInt(LaunchActivity.BEST_RECORD,realCount)
                    .apply();
        }
    }

    private void getOrderedCellsCount(){
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
        this.rightOrder = orderedCount;
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
                soundPool2.pause(1);
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
}
