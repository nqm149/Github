package com.example.throwball;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
 
public class MainThread extends Thread {
 
    private SurfaceHolder surfaceHolder;
    private Circle circle;
    private long prevtime = System.currentTimeMillis();
    private long time,interval;
    private long INTERVAL_TIME = 2000;
//    public int BALL_COLOR = 20;
 
    public MainThread(SurfaceHolder surfaceHolder, Circle circle) {
        this.surfaceHolder = surfaceHolder;
        this.circle = circle;
       
    }
 
    public void run() {
        Canvas canvas = null;
 
        while (true) {
 
            try {
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                	time = System.currentTimeMillis();
                	interval = time - prevtime;
                    circle.clearCircle(canvas);
                    if(!circle.ballCaught||circle.ballThrown){
                    circle.updateBalls();
                    if (interval>INTERVAL_TIME){
                    	circle.BALL_COLOR += 20;
                    	circle.addBall(500, 1,circle.BALL_COLOR);
                    	prevtime = time;
                    }
                    if (circle.startMovingBall() ){
                    	circle.moveCircle();
                    }
                    }
                    circle.onDraw(canvas);
                }
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
 
        }
    }
}