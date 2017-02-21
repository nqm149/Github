package com.example.throwball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;


public class MainActivity extends ActionBarActivity {

	DrawingView dv;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dv = new DrawingView(this);
        setContentView(dv);
    }
    public class DrawingView extends View{
    	public int width;
    	public int height;
    	Context context;
    	private Paint mPaint;
    	private Bitmap mBitmap;
    	private Paint mBitmapPaint;
    	private Canvas mCanvas;
    	private Path mPath;
    	private Path ballPath;
    	private Paint ballPaint;
    	
		public DrawingView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			mPath = new Path();
			ballPaint = new Paint();
			ballPath = new Path();
			ballPaint.setAntiAlias(true);
			ballPaint.setColor(Color.RED);
			ballPaint.setStyle(Paint.Style.FILL);
			
			mPaint = new Paint();
			mPaint.setAntiAlias(true);
			mPaint.setDither(true);
			mPaint.setColor(Color.BLUE);
			mPaint.setStyle(Paint.Style.STROKE);
			mPaint.setStrokeJoin(Paint.Join.ROUND);
			mPaint.setStrokeCap(Paint.Cap.ROUND);
			mPaint.setStrokeWidth(10);
		
		}
		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);

			mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
			mCanvas = new Canvas(mBitmap);

		}
		
		@Override
		protected void onDraw(Canvas canvas){
			super.onDraw(canvas);
			
			canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
			canvas.drawPath(ballPath, ballPaint);
			canvas.drawPath(mPath,mPaint);
			
		}
		
		private float mX,mY;
//		private static final float TOUCH_TOLERANCE = 4;

		private void touch_start(float x,float y){
			mPath.reset();
			mPath.moveTo(x,y);
			mX = x;
			mY = y;
			ballPath.reset();
			ballPath.addCircle(mX,mY,50,Path.Direction.CW);

		}
    	private void touch_move(float x,float y){

    		mPath.reset();
    		mPath.moveTo(mX,mY);
			mPath.lineTo(x, y);
    	}
    	
    	private void touch_up(float x,float y){
    		mPath.reset();
    	}
    	
    	public boolean onTouchEvent(MotionEvent event){
    		float x = event.getX();
    		float y = event.getY();
    		
    		switch(event.getAction()){
    		case MotionEvent.ACTION_DOWN:
    			touch_start(x,y);
    			invalidate();
    			break;
    		case MotionEvent.ACTION_MOVE:
    			touch_move(x,y);
    			invalidate();
    			break;
    		case MotionEvent.ACTION_UP:
    			touch_up(x,y);
    			invalidate();
    			break;
    		}
    		return true;
    	}
    }
}
