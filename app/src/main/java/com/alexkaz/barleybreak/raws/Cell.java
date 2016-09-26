package com.alexkaz.barleybreak.raws;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.alexkaz.barleybreak.GameFieldActivity;
import com.alexkaz.barleybreak.R;

public class Cell {

    public static final int SIZE = 88;
    public static final int BORDER = 4;
    public static final int PIVOT = 10;

    // Свойства

    public GameFieldActivity context;
    public Bitmap skinImage;
    public Location location;

    public int id;
    private int x,y;

    private int sizeX ;
    private int sizeY ;
    private int borderX ;

    private int pivotX ;
    private int borderY ;
    private int pivotY ;

    private boolean locker = true;

    private Paint cellPaint;
    private Paint numberPaint;



    //Конструктор
    public Cell(GameFieldActivity context, int x, int y, int id){
        location = new Location();
        setOwnParameters(context, x,y,id);
    }

    // Методы
    private void setOwnParameters(GameFieldActivity context, int x, int y, int id){
        this.x = x;
        this.y = y;
        this.id = id;
        this.context = context;

        cellPaint = new Paint();
        numberPaint = new Paint();
        numberPaint.setTypeface(context.getDigitTapeFace());
    }

    private void selectSkinImage1(int id){
        switch (id){
            case 1:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_1);
                break;
            case 2:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_2);
                break;
            case 3:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_3);
                break;
            case 4:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_4);
                break;
            case 5:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_5);
                break;
            case 6:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_6);
                break;
            case 7:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_7);
                break;
            case 8:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_8);
                break;
            case 9:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_9);
                break;
            case 10:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_10);
                break;
            case 11:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_11);
                break;
            case 12:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_12);
                break;
            case 13:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_13);
                break;
            case 14:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_14);
                break;
            case 15:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_15);
                break;
            case 16:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_sixteen);
                break;
        }
    }

    private void selectSkinImage(int id){
        switch (id){
            case 1:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_one);
                break;
            case 2:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_two);
                break;
            case 3:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_three);
                break;
            case 4:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_four);
                break;
            case 5:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_five);
                break;
            case 6:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_six);
                break;
            case 7:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_seven);
                break;
            case 8:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_eight);
                break;
            case 9:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_nine);
                break;
            case 10:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_ten);
                break;
            case 11:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_eleven);
                break;
            case 12:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_twelve);
                break;
            case 13:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_thirteen);
                break;
            case 14:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_fourteen);
                break;
            case 15:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_fifteen);
                break;
            case 16:
                skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_sixteen);
                break;
        }
    }


    public void draw(Canvas g){
        if (locker){
            getSizes(g.getHeight(),g.getWidth());
            locker = false;
        }
        g.drawBitmap(skinImage, this.location.posX, this.location.posY,cellPaint);
        drawNumber(g);
    }

    private void drawNumber(Canvas g){
        if (id == 16) {
            return;
        }
        String number = id + "";
        Rect textRect = new Rect();
        numberPaint.getTextBounds(number,0,number.length(),textRect);
        int x = this.location.posX + sizeX/2 - Math.round(numberPaint.measureText(number)/2);
        int y = this.location.posY + sizeY/2 + textRect.height()/2;
//        /////// text rect ////////
//        Paint rectPaint = new Paint();
//        rectPaint.setColor(Color.DKGRAY);
//        textRect.offset(x,y);
//        g.drawRect(textRect,rectPaint);
//        ////////////////////////
//
//
//        //////   lines  ////////////////
//        Paint linePaint = new Paint();
//        linePaint.setColor(Color.GREEN);
//        g.drawLine(this.location.posX,this.location.posY + sizeY/2,this.location.posX + sizeX,this.location.posY + sizeY/2,linePaint);
//        g.drawLine(this.location.posX + sizeX/2,this.location.posY,this.location.posX + sizeX/2,this.location.posY + sizeY,linePaint);
//        ////////////////////////////////
        g.drawText(number,x,y, numberPaint );
    }

    private void getSizes(int height, int width) {

        float fHeight = height;
        float fWidth = width;

        float fxSize = (fWidth/400)*90;
        float fxBorder = (fWidth/400)*7;
        float fxPivot = (fWidth/400)*15;

        float fySize = (fHeight/400)*90;
        float fyBorder = (fHeight/400)*7;
        float fyPivot = (fHeight/400)*15;

        sizeX = (int) fxSize;
        borderX = (int) fxBorder;
        pivotX = (int) fxPivot;
        sizeY = (int) fySize;
        borderY = (int) fyBorder;
        pivotY = (int) fyPivot;

//        Log.d("sizesLog",sizeX + ""); // 87   0.4942
//        numberPaint.setTextSize(sizeX * 0.494252f);
        numberPaint.setTextSize(sizeX * 0.594252f);

        location.posX = (sizeX*x) + borderX*x + pivotX;
        location.posY = (sizeY*y) + borderY*y + pivotY;
//        selectSkinImage(this.id);
        if (id == 16){
            skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell_sixteen);
        } else {
            skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.cell);
        }
        skinImage = Bitmap.createScaledBitmap(skinImage,sizeX,sizeY,false);
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
}
