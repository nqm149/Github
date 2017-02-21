package com.example.pingpong;
 
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceHolder;
 
public class PingPong extends Activity {
	private static boolean running = true;
	private SurfaceHolder surfaceHolder;
	private CreateGame view;
//	SharedPreferences pref = getSharedPreferences("MyPref", 0); 
//	Editor editor = pref.edit();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         
        //This is the only line you need to add.
        //We place our UI with the setContentView method
        setContentView(new CreateGame(this));
    }
 
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.ping_pong, menu);
//        return true;
//    }
//    public PingPong(SurfaceHolder surfaceHolder, CreateGame view) {
//		this.surfaceHolder = surfaceHolder;
//		this.view = view;
//
//	}
    static class GameThread extends Thread {

    	private SurfaceHolder surfaceHolder;
    	private CreateGame view;
//    	private long prevtime = System.currentTimeMillis();
//    	private long time, interval;
//    	private long INTERVAL_TIME = 2000;
//    	// desired fps
//    	private final static int MAX_FPS = 200;
//    	// maximum number of frames to be skipped
//    	private final static int MAX_FRAME_SKIPS = 5;
//    	// the frame period
//    	private final static int FRAME_PERIOD = 1000 / MAX_FPS;

    	// public int BALL_COLOR = 20;

    	public GameThread(SurfaceHolder surfaceHolder, CreateGame view) {
    		this.surfaceHolder = surfaceHolder;
    		this.view = view;

    	}

    	public void run() {
    		Canvas canvas = null;

    		while (running) {

    			try {
    				canvas = surfaceHolder.lockCanvas(null);
    				synchronized (surfaceHolder) {

    					view.clearView(canvas);
    					view.updateGame();
    					view.onDraw(canvas);
    				}

    			} finally {
    				if (canvas != null) {
    					surfaceHolder.unlockCanvasAndPost(canvas);
    				}
//    				if(running == false){
//    					editor.putString("key_name", "string value");
//    				}
    			}

    		}
    	}
    }
    
    @Override
    public void onBackPressed(){  
    	super.onBackPressed();
    }
    
    @Override
    protected void onStop(){
        super.onStop();
    }
    @Override
    protected void onPause() {
        super.onPause();
        running = false;
    }
    @Override
    protected void onResume(){
    	super.onResume();
    	running = true;
    }

}