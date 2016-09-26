package com.alexkaz.barleybreak.raws;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.alexkaz.barleybreak.GameFieldActivity;
import com.alexkaz.barleybreak.R;

public class Cell {
    // Свойства
    public Location location;
    public Bitmap skinImage;
    public int id;
    public GameFieldActivity context;

    private int x,y;

    public static final int SIZE = 88;
    public static final int BORDER = 4;
    public static final int PIVOT = 10;

    private boolean locker = true;

    private int sizeX ;
    private int borderX ;
    private int pivotX ;

    private int sizeY ;
    private int borderY ;
    private int pivotY ;


    //Конструктор
    public Cell(GameFieldActivity context, int x, int y, int id){
        location = new Location();
        setOwnParameters(context, x,y,id);
    }

    // Методы
    private void setOwnParameters(GameFieldActivity context, int x, int y, int id){
//        location.posX = (Cell.SIZE*x) + Cell.BORDER*(x/Cell.SIZE) + Cell.PIVOT;
//        location.posY = (Cell.SIZE*y) + Cell.BORDER*(y/Cell.SIZE) + Cell.PIVOT;

//        location.posX = (sizeX*x) + borderX*(x/sizeX) + pivotX;
//        location.posY = (sizeY*y) + borderY*(y/sizeY) + pivotY;
        this.x = x;
        this.y = y;
        this.id = id;
        this.context = context;
//        selectSkinImage(this.id);
//        skinImage = Bitmap.createScaledBitmap(skinImage,Cell.SIZE,Cell.SIZE,false);
//        skinImage = Bitmap.createScaledBitmap(skinImage,sizeX,sizeY,false);
    }

    private void selectSkinImage(int id){
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


    public void draw(Canvas g){
//        g.drawImage(skinImage, this.location.posX, this.location.posY, null);

//        g.drawColor(Color.LTGRAY);

//        skinImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
        if (locker){
            getSizes(g.getHeight(),g.getWidth());
            locker = false;
        }
//        p.setColor(Color.GREEN);

//        g.drawCircle(this.location.posX,this.location.posY,Cell.RADIUS,p);

//        g.drawRect(this.location.posX,this.location.posY,this.location.posX + Cell.SIZE - Cell.BORDER,this.location.posY + Cell.SIZE - Cell.BORDER,p);
        g.drawBitmap(skinImage, this.location.posX, this.location.posY,new Paint());
//        p.setColor(Color.BLACK);
//        g.drawText(String.valueOf(id) ,this.location.posX + 30, this.location.posY + 30, p);

//        p.setColor(Color.LTGRAY);

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
//        borderX = 4;

        pivotX = (int) fxPivot;

        sizeY = (int) fySize;
        borderY = (int) fyBorder;
//        borderY = 4;

        pivotY = (int) fyPivot;

        location.posX = (sizeX*x) + borderX*x + pivotX;
        location.posY = (sizeY*y) + borderY*y + pivotY;

        selectSkinImage(this.id);

        skinImage = Bitmap.createScaledBitmap(skinImage,sizeX,sizeY,false);

//        String resultX = "sizeX = " + sizeX + ", borderX = " + borderX + ", pivotX = " + pivotX;
//        String resultY = "sizeY = " + sizeY + ", borderY = " + borderY + ", pivotY = " + pivotY;
//
//
//        Log.d("scaleLog","-----------");
//        Log.d("scaleLog",resultX);
//        Log.d("scaleLog",resultY);
//        Log.d("scaleLog","-----------");
//
//        Log.d("canvasLog","height = " + height + ", width" + width);
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
}
