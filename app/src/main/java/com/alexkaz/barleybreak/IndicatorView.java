package com.alexkaz.barleybreak;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

public class IndicatorView extends View {

    private int rightOrder = 0;
    Context context;

    public static final int RECT_SIDE_SIZE = 10;

    private int rectSideSizeX;
    private int borderSizeX;

    private int rectSideSizeY;
    private int borderSizeY;

    public IndicatorView(Context context) {
        super(context);
        this.context = context;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getSizes(canvas.getHeight(), canvas.getWidth());
        drawRects(canvas,rightOrder);
//        Paint p = new Paint();
//        p.setColor(Color.GREEN);
//        Rect rect = new Rect(0,0,10,10);
//        canvas.drawRect(rect,p);
    }

    private void drawRects(Canvas canvas, int rightOrder){
        if (rightOrder < 0){
            rightOrder = 0;
        }
        if (rightOrder > 16){
            rightOrder = 16;
        }
        int count = 0;







        Paint p = new Paint();
        p.setColor(Color.RED);
//        p.setColor(Color.rgb(23,191,115));

        Rect rect = new Rect(0,0,RECT_SIDE_SIZE,RECT_SIDE_SIZE);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(count == rightOrder ){
                    p.setColor(Color.GRAY);

                }

//                rect.set(j*BORDER_SIZE,i*BORDER_SIZE,j*BORDER_SIZE + RECT_SIDE_SIZE,i*BORDER_SIZE + RECT_SIDE_SIZE);
                rect.set(j*borderSizeX,i*borderSizeY,j*borderSizeX + rectSideSizeX,i*borderSizeY + rectSideSizeY);


                canvas.drawRect(rect,p);
                count++;
            }
        }
        p.setColor(Color.GREEN);

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

        Log.d("canvasSizes", rectSideSizeX + " " + borderSizeX);


    }

    public void setRightOrder(int rightOrder) {
        this.rightOrder = rightOrder;
    }

}
