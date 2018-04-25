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

    private void initEmpty(Cell newMatrix[][]) {
        for (int x = 0; x < numcols; x++) {
            for (int y = 0; y < numrows; y++) {
                newMatrix[x][y] = new Cell(false);
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
        Cell newMatrix[][] = new Cell[numcols][numrows];
        initEmpty(newMatrix);
        ArrayList<Cell> neighbors;
        for (int x = 0; x < numcols; x++) {
            for (int y = 0; y < numrows; y++) {
                neighbors = getNeighbors(x, y);
                int numNeighbors = countNeighbors(neighbors);

                if (cellMatrix[x][y].isAlive()) {
                    if (numNeighbors < 2) newMatrix[x][y].setAlive(false);
                    if (numNeighbors == 2 || numNeighbors == 3) newMatrix[x][y].setAlive(true);
                    if (numNeighbors > 3) newMatrix[x][y].setAlive(false);
                } else {
                    if (numNeighbors == 3) {
                        newMatrix[x][y].setAlive(true);
                    }
                }
            }
        }
        cellMatrix = newMatrix;
    }

    private ArrayList<Cell> getNeighbors(int x, int y) {
        ArrayList<Cell> neighbors = new ArrayList<>(8);

        // Left
        if (x - 1 >= 0) {
            neighbors.add(cellMatrix[x-1][y]);

            // Upper Left
            if (y - 1 >= 0) {
                neighbors.add(cellMatrix[x-1][y-1]);
            }

            // Bottom Left
            if (y + 1 < numrows) {
                neighbors.add(cellMatrix[x-1][y+1]);
            }
        }

        // Right
        if (x + 1 < numcols) {
            neighbors.add(cellMatrix[x+1][y]);

            // Upper Right
            if (y - 1 >= 0) {
                neighbors.add(cellMatrix[x+1][y-1]);
            }

            // Bottom Right
            if (y + 1 < numrows) {
                neighbors.add(cellMatrix[x+1][y+1]);
            }
        }

        // Top
        if (y - 1 >= 0) {
            neighbors.add(cellMatrix[x][y-1]);
        }

        // Bottom
        if (y + 1 < numrows) {
            neighbors.add(cellMatrix[x][y+1]);
        }

        return neighbors;
    }

    private int countNeighbors(ArrayList<Cell> neighbors) {
        int aliveNeighbors = 0;
        for (Cell cell : neighbors) {
            if (cell.isAlive()) aliveNeighbors++;
        }
        return aliveNeighbors;
    }
}
