package com.commissionsinc.conway;

public class Cell {
    private boolean alive;
    private static final int size = 10;

    public Cell(boolean alive) {
        this.alive = alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }
}
