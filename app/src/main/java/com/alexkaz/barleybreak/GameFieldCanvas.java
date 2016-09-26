package com.alexkaz.barleybreak;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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

    private SoundPool soundPool;
    private SoundPool soundPool2;
    private SoundPool soundPool3;


    private int rightOrder;
    private int stepCount;
    private int minStepCount = 0;

//    int posX = 156;
//    int posY = 134;

    public GameFieldCanvas(GameFieldActivity context) {
        super(context);
        this.context = context;

        setOwnParameters();
        initComponents();
    }

    //Методы
    private void initComponents() {
        cellPositionManager = new CellPositionManager(context);

        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        soundPool.load(context,R.raw.click, 1);

        soundPool2 = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        soundPool2.load(context,R.raw.win_sound, 1);

        soundPool3 = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        soundPool3.load(context,R.raw.game_over_sound, 1);


    }


    private void setOwnParameters() {
//        setBackgroundColor(Color.LTGRAY);

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

//                        String res = Math.round(e.getX()) + " " + Math.round(e.getY());
//                        Log.d("myTag",res);
                        invalidate();

                        boolean isSorted =  cellPositionManager.verifyOrders();

                        if (isSorted){
                            checkRecord(stepCount);
                            youWonDialog();
                            boolean isSoundOn = context.getSharedPreferences(LaunchActivity.MY_SETTING,Activity.MODE_PRIVATE).getBoolean(LaunchActivity.SOUND_SWITCHER,false);
                            if(isSoundOn){
                                soundPool2.play(1,1,1,0,0,0);
                            }
                            stepCount = 0;
                            txtBestScores.setText(String.valueOf(context.getSharedPreferences(LaunchActivity.MY_SETTING,Activity.MODE_PRIVATE).getInt(LaunchActivity.BEST_RECORD,1000)));
                            txtScores.setText("SCORES:" + String.valueOf(0));
                            context.stepProgressBar.setProgress(0);
                        }
                        else {
                            txtScores.setText("SCORES:" + String.valueOf(stepCount));
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
                            txtBestScores.setText(String.valueOf(context.getSharedPreferences(LaunchActivity.MY_SETTING,Activity.MODE_PRIVATE).getInt(LaunchActivity.BEST_RECORD,1000)));
                            txtScores.setText("SCORES:" + String.valueOf(0));
                            context.stepProgressBar.setProgress(0);

                            Log.d("myTag", "У вас закінчились кроки , ви програли!");

                        }

                        isOrdered();
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


    private void isOrdered(){
//        int count = 1;
//
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 4; j++) {
//                int bufX = cellPositionManager.cells[i][j].location.posX/ Cell.SIZE;
//                int bufY = cellPositionManager.cells[i][j].location.posY/ Cell.SIZE;
//
//                int id = bufX*4 + bufY;
//
//                Log.d("myTag",String.valueOf(id));
//                //Log.d("myTag",String.valueOf(count));
//
//                if (count == id){
//                    rightOrder++;
//                }
//                count++;
//            }
//        }
        int count  = 1;
        int rightOrder = 0;

        for (int i = 0; i < cellPositionManager.cells.length; i++) {
            for (int j = 0; j < cellPositionManager.cells[i].length; j++) {
                int id = cellPositionManager.cells[i][j].id;
                int buf = id/cellPositionManager.cells[i].length;
                if(id%cellPositionManager.cells[i].length == 0){
                    buf -=1;
                }
                if(id == count && i == buf){
                    rightOrder +=1;
                    count++;
                }
                else {
                    break;
                }
//                String log1 = "cell[" + i + "][" + j + "] = " + id;
//                Log.d("myIdTag",log1);
            }
        }
        this.rightOrder = rightOrder;
    }


    public void restartGame(){
        cellPositionManager.initComponents();
        stepCount = 0;
        rightOrder=0;
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

    private void youWonDialog(){
        View alertView = context.getLayoutInflater().inflate(R.layout.win_dialog, null);

        final Dialog myDialog = new Dialog(context, R.style.CustomAlertDialog);
        myDialog.setContentView(alertView);

        Button alertBtn = (Button) alertView.findViewById(R.id.alertBtn);

        alertBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.cancel();
                restartGame();
                soundPool2.pause(1);
                Toast.makeText(context,"Game restarted",Toast.LENGTH_SHORT).show();
            }
        });
        myDialog.show();
    }

    private  void youLoseDialog(){
        View alertView = context.getLayoutInflater().inflate(R.layout.lose_dialog, null);

        final Dialog myDialog = new Dialog(context, R.style.CustomAlertDialog);
        myDialog.setContentView(alertView);

        Button alertBtn = (Button) alertView.findViewById(R.id.alertLoseBtn);

        alertBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.cancel();
                restartGame();
                soundPool2.pause(1);
                Toast.makeText(context,"Game restarted",Toast.LENGTH_SHORT).show();
            }
        });
        myDialog.show();
    }

    public void setIndicatorView(IndicatorView indicatorView) {
        this.indicatorView = indicatorView;
    }

    public void setMinStepCount(int minStepCount) {
        this.minStepCount = minStepCount;
    }
}
