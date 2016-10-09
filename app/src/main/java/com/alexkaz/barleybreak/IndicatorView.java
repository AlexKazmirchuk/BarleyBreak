package com.alexkaz.barleybreak;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class IndicatorView extends View {

    private int rightOrder = 0;
    Context context;
    private boolean locker = true;

    public static final int RECT_SIDE_SIZE = 10;

    private int rectSideSizeX;
    private int borderSizeX;

    private int rectSideSizeY;
    private int borderSizeY;
    private Paint indicatorPaint;
    private Rect indicatorRect;

    public IndicatorView(Context context) {
        super(context);
        this.context = context;
        indicatorPaint = new Paint();
        indicatorRect = new Rect(0,0,RECT_SIDE_SIZE,RECT_SIDE_SIZE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (locker){
            getSizes(canvas.getHeight(), canvas.getWidth());
            locker = false;
        }
        drawRects(canvas,rightOrder);
    }

    private void drawRects(Canvas canvas, int rightOrder){
        if (rightOrder < 0){
            rightOrder = 0;
        }
        if (rightOrder > 16){
            rightOrder = 16;
        }
        int count = 0;

        indicatorPaint.setColor(Color.RED);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(count == rightOrder ){
                    indicatorPaint.setColor(Color.GRAY);
                }
                indicatorRect.set(j*borderSizeX,i*borderSizeY,j*borderSizeX + rectSideSizeX,i*borderSizeY + rectSideSizeY);
                canvas.drawRect(indicatorRect,indicatorPaint);
                count++;
            }
        }
    }

    private void getSizes(int height, int width){
        float fxSize = ((float) width /400)*70;
        float fxBorder = ((float) width /400)*110;

        float fySize = ((float) height /400)*70;
        float fyBorder = ((float) height /400)*110;

        rectSideSizeX = (int) fxSize;
        borderSizeX = (int) fxBorder;

        rectSideSizeY = (int) fySize;
        borderSizeY = (int) fyBorder;
    }

    public void setRightOrder(int rightOrder) {
        this.rightOrder = rightOrder;
    }
}
