package com.commissionsinc.conway;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SimulationView extends SurfaceView implements Runnable {

    // Android SDK Surface/Painting
    private Context context;
    SurfaceHolder holder;
    Paint paint;

    // Simulation Thread
    Thread simThread;
    boolean running = true;

    // Surface Attributes
    int w, h;

    // Simulation Grid
    Grid grid;

    public SimulationView(Context context) {
        super(context);
        this.context = context;
        holder = getHolder();
        paint = new Paint();
        paint.setColor(Color.DKGRAY);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
        grid = new Grid(this.w, this.h);
    }

    // Simulation thread methods
    @Override
    public void run() {
        Canvas canvas;

        while (running) {
            if (holder.getSurface().isValid()) {
                canvas = holder.lockCanvas();

                grid.draw(canvas, paint);
                grid.update();

                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void resume() {
        running = true;
        simThread = new Thread(this);
        simThread.start();
    }

    public void pause() {
        running = false;
        try {
            simThread.join();
        } catch (InterruptedException e) {
            Log.d("SimulationView", "Unable to join thread with main");
        }
    }
}
