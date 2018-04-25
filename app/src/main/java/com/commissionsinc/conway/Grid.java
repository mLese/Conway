package com.commissionsinc.conway;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;
import java.util.Random;

public class Grid {
    private Cell cellMatrix[][];
    private int numcols, numrows;
    private static int cellWidth = 10;
    private static int cellHeight = 10;

    public Grid(int w, int h) {
        numcols = w / cellWidth;
        numrows = h / cellHeight;

        cellMatrix = new Cell[numcols][numrows];

        init();
    }

    private void init() {
        Random random = new Random(System.currentTimeMillis());
        for (int x = 0; x < numcols; x++) {
            for (int y = 0; y < numrows; y++) {
                cellMatrix[x][y] = new Cell(random.nextBoolean());
            }
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawColor(Color.BLACK);
        paint.setColor(Color.GREEN);
        for (int x = 0; x < numcols; x++) {
            for (int y = 0; y < numrows; y++) {
                if (cellMatrix[x][y].isAlive()) {
                    canvas.drawRect(x * cellWidth, y * cellHeight, (x * cellWidth) + cellWidth, (y * cellWidth) + cellHeight, paint);
                }
            }
        }
    }

    /*
    1. Any live cell with fewer than two live neighbors dies
    2. Any live cell with two or three live neighbors lives
    3. Any live cell with more than three live neighbors dies
    4. Any dead cell with exactly 3 live neighbors becomes a live cell
     */
    public void update() {

    }
}
