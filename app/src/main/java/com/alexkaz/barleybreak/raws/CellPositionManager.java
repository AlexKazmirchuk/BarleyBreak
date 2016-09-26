package com.alexkaz.barleybreak.raws;

import android.graphics.Canvas;
import android.util.Log;

import com.alexkaz.barleybreak.GameFieldActivity;

import java.util.Arrays;
import java.util.Random;

public class CellPositionManager {
    // Свойства
    public Cell[][] cells = new Cell[4][4];
    public int[] randomNumberArr = new int[16];
    public int[] trueNumberArr = new int[16];
    public int[] tmpArr = new int[16];
    public GameFieldActivity context;
    public static final int CELLS_COUNT = 16;

    //Конструктор
    public CellPositionManager(GameFieldActivity context) {
        this.context = context;
        initComponents();
    }

    // Методы

    public void initComponents(){
        for (int i = 0; i < randomNumberArr.length; i++) {
            randomNumberArr[i] = i+1;
            trueNumberArr[i] = i+1;
            tmpArr[i] = i+1;
        }
        randomizeNumbers();
        initCells();
    }


    public void draw(Canvas g){
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                cells[i][j].draw(g);
            }
        }
    }

    public void swapCells(int firstRow, int firstCol, int secondRow, int secondCol){
        int tmpPosX = cells[firstRow][firstCol].location.posX;
        int tmpPosY = cells[firstRow][firstCol].location.posY;
        Cell tmpCell = cells[firstRow][firstCol];

        cells[firstRow][firstCol].location.posX = cells[secondRow][secondCol].location.posX;
        cells[firstRow][firstCol].location.posY = cells[secondRow][secondCol].location.posY;
        cells[firstRow][firstCol] = cells[secondRow][secondCol];

        cells[secondRow][secondCol].location.posX = tmpPosX;
        cells[secondRow][secondCol].location.posY = tmpPosY;
        cells[secondRow][secondCol] = tmpCell;
    }

    public void randomizeNumbers(){
        Random rand = new Random();

        for (int i = 0; i < randomNumberArr.length; i++) {
            int a = rand.nextInt(16);
            int b = randomNumberArr[i];
            randomNumberArr[i] = randomNumberArr[a];
            randomNumberArr[a] = b;
        }
    }

    public boolean exchangeCells(int x, int y){
        Position position = getPositionOfCell(x,y);

        if(position.row > 0){
            if(cells[position.row-1][position.col].id == CELLS_COUNT){
                swapCells(position.row,position.col,position.row-1,position.col);
                return true;
            }
        }
        if(position.row < 3){
            if(cells[position.row+1][position.col].id == CELLS_COUNT){
                swapCells(position.row,position.col,position.row+1,position.col);
                return true;
            }
        }
        if(position.col > 0){
            if (cells[position.row][position.col-1].id == CELLS_COUNT){
                swapCells(position.row,position.col,position.row,position.col-1);
                return true;
            }
        }
        if(position.col < 3){
            if (cells[position.row][position.col+1].id == CELLS_COUNT){
                swapCells(position.row,position.col,position.row,position.col+1);
                return true;
            }
        }
        return false;
    }

    private Position getPositionOfCell(int x, int y){
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j <cells[i].length ; j++) {
//                if (cells[i][j].location.posX < x && cells[i][j].location.posX + Cell.SIZE > x){
//                    if (cells[i][j].location.posY < y && cells[i][j].location.posY + Cell.SIZE > y){
                if (cells[i][j].location.posX < x && cells[i][j].location.posX + cells[i][j].getSizeX() > x){
                    if (cells[i][j].location.posY < y && cells[i][j].location.posY + cells[i][j].getSizeY() > y){
                        return new Position(i,j);
                    }
                }
            }
        }
        return new Position(1,1);
    }

    public boolean verifyOrders(){
        for (int i = 0; i <cells.length ; i++) {
            for (int j = 0; j <cells[i].length ; j++) {
                tmpArr[i*4+j] = cells[i][j].id;
            }
        }
        if(Arrays.equals(tmpArr,trueNumberArr)){
            Log.d("myTag", "Ура !!! Вы выиграли!");
            return true;
        }
        return false;
    }

    public void initCells(){
        cells[0] = new Cell[4];
        cells[1] = new Cell[4];
        cells[2] = new Cell[4];
        cells[3] = new Cell[4];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                cells[i][j] = new Cell(context, j,i, randomNumberArr[i*4+j]);
//                cells[i][j] = new Cell(context, Cell.SIZE*j,Cell.SIZE*i, trueNumberArr[i*4+j]);
            }
        }
    }
}
