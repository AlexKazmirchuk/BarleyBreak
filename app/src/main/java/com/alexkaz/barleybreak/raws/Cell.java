package com.alexkaz.barleybreak.raws;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.alexkaz.barleybreak.GameFieldActivity;
import com.alexkaz.barleybreak.R;

public class Cell {

    // Свойства
    public GameFieldActivity context;
    public Bitmap skinImage;
    public Location location;

    public int id;
    private int x,y;

    private int sizeX ;
    private int sizeY ;

    private boolean locker = true;

    private Paint cellPaint;
    private Paint numberPaint;
    private int textPivot;


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

        /////// text positioning
        String number = id + "";
        Rect textRect = new Rect();
        numberPaint.getTextBounds(number,0,number.length(),textRect);
        int x = this.location.posX + sizeX/2 - Math.round(numberPaint.measureText(number)/2);
        int y = this.location.posY + sizeY/2 + textRect.height()/2;

        /////// draw text shadow
        numberPaint.setColor(Color.parseColor("#452d2d"));
        for (int i = 0; i < textPivot; i++) {
            g.drawText(number, x + i, y + i, numberPaint );
        }

        ////// draw text
        numberPaint.setColor(Color.parseColor("#2b1313"));
        g.drawText(number,x,y, numberPaint );
    }

    private void getSizes(int height, int width) {

        float fxSize = ((float) width /400.0f)*90;
        float fxBorder = ((float) width /400.0f)*7;
        float fxPivot = ((float) width /400.0f)*11;  //15

        float fySize = ((float) height /400.0f)*90;
        float fyBorder = ((float) height /400.0f)*7;
        float fyPivot = ((float) height /400.0f)*12; //15

        sizeX = (int) fxSize;
        int borderX = (int) fxBorder;
        int pivotX = (int) fxPivot;
        sizeY = (int) fySize;
        int borderY = (int) fyBorder;
        int pivotY = (int) fyPivot;

        numberPaint.setTextSize(sizeX * 0.594252f);
        textPivot = Math.round(sizeX * 0.046f);
        location.posX = (sizeX*x) + borderX *x + pivotX;
        location.posY = (sizeY*y) + borderY *y + pivotY;
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
