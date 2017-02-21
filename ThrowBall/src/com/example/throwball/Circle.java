package com.example.throwball;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Circle extends SurfaceView implements SurfaceHolder.Callback {

	private MainThread mainthread;
	private float ballX;
	private float ballY;
//	private static float bX;
//	private static float bY;
	private int radius;
	private Paint paint;
	private MainThread thread;
	public int direction = 1;
	public int prev;
	public int width;
	public int height;
	Context context;
	private Paint mPaint;
	private Bitmap mBitmap;
	private Paint mBitmapPaint;
	private Canvas mCanvas;
	private Path mPath;
	private Path redBallPath;
	private Paint redBallPaint;
	private Path blueBallPath;
	private Paint blueBallPaint;
	private Paint dropBallPaint;
	private boolean startMovingBall = false;
	public boolean ballInsideScreen = true;
	private float tg, sin, cos, cX, cY;
	private int speed = 20;
	private boolean datinhhuong = false;
	long startTime;
	public boolean ballCaught = false;
	public boolean ballThrown = false;
	public int BALL_COLOR = 20;
	int score = 0;
	public boolean turnBegin = false;

	public Circle(Context context, float x, float y, int radius) {
		super(context);

		ballX = 0;
		ballY = 0;
//		addBall(100, 1,1);
		this.radius = radius;
		// Tell the SurfaceHolder ( -> getHolder() ) to receive SurfaceHolder
		// callbacks
		getHolder().addCallback(this);
		this.thread = new MainThread(getHolder(), this);

		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.BLUE);
		// /
		mPath = new Path();
		redBallPaint = new Paint();
		redBallPath = new Path();
		redBallPaint.setAntiAlias(true);
		redBallPaint.setColor(Color.RED);
		redBallPaint.setStyle(Paint.Style.FILL);

		blueBallPaint = new Paint();
		blueBallPath = new Path();
		blueBallPaint.setAntiAlias(true);
		blueBallPaint.setColor(Color.BLUE);
		blueBallPaint.setStyle(Paint.Style.FILL);
		
		dropBallPaint = new Paint();
		dropBallPaint.setColor(Color.BLACK);
		dropBallPaint.setAntiAlias(true);
		
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(Color.BLUE);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(10);
		
	}

	public void moveCircle() {

		// Decrease speed of ball
//		long endTime = System.currentTimeMillis();
//		long duration = endTime - startTime;
//		if (duration >= 10000) {
//			duration = 0;
//			startTime = System.currentTimeMillis();
//			speed = speed - 3;
//			cX *= speed / (speed + 3);
//			cY *= speed / (speed + 3);
//			if (speed == 0) {
//			}
//		}
		
		ballX += cX;
		ballY += cY;
		//Nest1
				if (ballX > 140 && ballX < 240
				&& ballY - this.radius > getHeight() - 100 && ballY - this.radius < getHeight()) {
					score += 100;
					ballX = 0;ballY = 0;
					turnBegin = false;				
				}

				//Nest2
				if (ballX > getWidth()/2 - 36 && ballX < getWidth()/2 + 64
				&& ballY - this.radius > getHeight() - 100 && ballY - this.radius < getHeight()) {
					score += 200;
					ballX = 0;ballY = 0;
					turnBegin = false;					
				}

				//Nest3
				if (ballX > getWidth() - 212 && ballX < getWidth() - 112
				&& ballY - this.radius > getHeight() - 100 && ballY - this.radius < getHeight()) {
					score += 300;
					ballX = 0;ballY = 0;
					turnBegin = false;
				}
		//DOAN. NAY TAO VIET THU?
		cX = (float) (cX * 0.995);
		cY = (float) (cY * 0.995);

		if (ballInsideScreen) {
			
			if (ballX >= (getWidth() - this.radius)) {
				cX = -cX;
			} 
			if (ballX <= this.radius) {
				cX = -cX;
			} 
			if (ballY >= getHeight() - this.radius) {
				cY = -cY;
			} 
			if (ballY <= this.radius) {
				cY = -cY;
			}
		}else	
		if ( ballX < getWidth() - this.radius && 
		     ballX > this.radius &&
		     ballY < getHeight() - this.radius &&
		     ballY > this.radius ) {
			 	ballInsideScreen = true; 		
		}
		
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);

	}

	private void firstmove() {
		if (nX - mX >= 0 && nY - mY <= 0) {
			direction = 1;
		}
		if (nX - mX >= 0 && nY - mY >= 0) {
			direction = 2;
		}
		if (nX - mX <= 0 && nY - mY >= 0) {
			direction = 3;
		}
		if (nX - mX <= 0 && nY - mY <= 0) {
			direction = 4;
		}
	}

	private float mX, mY;
	public float nX, nY;

	private void calMove() {
		int distance = (int) Math.sqrt( (nX - mX)*(nX-mX) + (nY - mY)*(nY-mY) );
		speed = distance / 10;
		tg = Math.abs(nX - mX) / Math.abs(nY - mY);
		cos = (float) Math.sqrt(1 / (1 + tg * tg));
		sin = (float) Math.sqrt(1 - cos * cos);
		cX = speed * sin;
		cY = speed * cos;
		
		switch (direction) {
		case 1:
			cY = -cY;
			break;
		case 2:
			break;
		case 3:
			cX = -cX;
			break;
		case 4:
			cX = -cX;
			cY = -cY;
			break;
		}
	}
	
	private void checkBall(){
		if (mX < this.radius || getWidth() - mX < this.radius || 
				mY < this.radius || getHeight() - mY < this.radius) {
					ballInsideScreen = false;			
				}
	}
	private float sX,sY;
	private void touch_start() {
		mPath.reset();
//		speed = 30;
//		startTime = System.currentTimeMillis();
		ballX = 0;
		ballY = 0;
		startMovingBall = false;
		sX = balls.get(0).bX;
		sY = balls.get(0).bY;
		r = balls.get(0).radius;
		if( (nX - sX)*(nX - sX) + (nY - sY)*(nY - sY) < r*r){
			ballCaught = true;
		}
		mX = sX;
		mY = sY;
		mPath.moveTo(sX, sY);
		redBallPath.reset();
		redBallPath.addCircle(mX, mY, 50, Path.Direction.CW);
		checkBall();
		ballThrown = false;
	}

	private void touch_move() {
		mPath.reset();
		mPath.moveTo(mX, mY);
		mPath.lineTo(nX, nY);
		blueBallPath.reset();
		blueBallPath.addCircle(nX, nY, 50, Path.Direction.CW);
	}

	private void touch_up() {
		mPath.reset();
		redBallPath.reset();
		ballX = mX;
		ballY = mY;
		firstmove();
		calMove();
//		ballCaught = false;
		ballThrown = true;
		balls.remove(0);
	}
	private float r;
	public boolean onTouchEvent(MotionEvent event) {

		nX = event.getX();
		nY = event.getY();
//		List<Ball> balls = getBalls();
		
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touch_start();
			// cho bong roi ben trai moi lan cham vao man hinh
//			addBall(100,1,1);
			turnBegin = true;
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			touch_move();
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			if (!startMovingBall) {
				startMovingBall = true;
			}
			touch_up();
			invalidate();
			break;
		}
		return true;
	}

	private int ballThrownColor;
	public void onDraw(Canvas canvas) {
		// neu thay x,y bang bX,bY ( diem? keo dai cua duog thang? ) thi qua
		// bong xanh se dug im k chuyen dong nua
		if (!(ballX == 0 && ballY == 0) && ballCaught && ballThrown && turnBegin) {
//			canvas.drawCircle(ballX, ballY, this.radius, this.paint);
			this.paint.setColor(Color.rgb(50, 100, ballThrownColor));
			canvas.drawCircle(ballX, ballY, this.radius, this.paint);
		}
		// Dung mCanvas thi bong' luu lai. mai~ vi ko duoc xoa'
		
		canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
		if(ballCaught){
//			redBallPaint.setColor(Color.rgb(50, 100, mainthread.BALL_COLOR));
			redBallPaint.setColor(Color.rgb(50, 100, BALL_COLOR));
			ballThrownColor = BALL_COLOR;
			canvas.drawPath(redBallPath, redBallPaint);
			canvas.drawPath(mPath, mPaint);
		}
//		canvas.drawPath(blueBallPath, blueBallPaint);
		
		
		List<Ball> balls = getBalls();
		for (Ball b : balls) {	
				dropBallPaint.setColor(Color.rgb(50, 100, b.bcolor));			
				canvas.drawCircle(b.bX, b.bY, b.radius, dropBallPaint);
		}
		//Paint Score
				Paint scorePaint=new Paint();
				scorePaint.setColor(Color.BLACK);
				scorePaint.setTextSize(30);
			    canvas.drawText("YOUR SCORE: " + score, 20, 30, scorePaint);

			    //Paint Nest
				Bitmap bitmapNest;
				bitmapNest = BitmapFactory.decodeResource(this.getResources(),R.drawable.nest);
				super.onDraw(canvas);
				canvas.drawBitmap(bitmapNest, 140, getHeight() - 100, null);
				canvas.drawBitmap(bitmapNest, getWidth()/2 - 36, getHeight() - 100, null);
				canvas.drawBitmap(bitmapNest, getWidth() - 212, getHeight() - 100, null);
				//Background
//				Bitmap background;
//				background = BitmapFactory.decodeResource(this.getResources(),R.drawable.baseball);
//				canvas.drawBitmap(background, 0, 0, null);
				

	}

	public void clearCircle(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
	}

	public boolean startMovingBall() {
		return this.startMovingBall;
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
	}
	
	
	//Từ đây thử thêm bóng rơi từ trên xuống
		
	  private static final float BALL_RADIUS = 50f;
	  private static final float MAX_DISTANCE = 500;
	  private float DISTANCE_CHANGE_PER_MS = 0.2f;
//	  private final List<MediaPlayer> players = new LinkedList<MediaPlayer>();
		private boolean running = false;
		
		public static final class Ball {
			float bX, bY, radius;
			int bcolor;
			
			public Ball(float x, float y,int color) {
				bX = x;
				bY = y;
				radius = BALL_RADIUS;
				this.bcolor = color;
			}
		}
		
		private final List<Ball> balls = new LinkedList<Ball>();
		

	    private volatile long lastTimeMs = -1;
		
		public final Object LOCK = new Object();
	  
		public List<Ball> getBalls() {
			synchronized (LOCK) {
				return new ArrayList<Ball>(balls);
			}
		}
		
		public void addBall(float x, float y,int color) {
			synchronized (LOCK) {
				balls.add(new Ball(x,y,color));
			}
		}
		
		public void setSize(int width, int height) {
			// TODO ignore this for now...we could hide Balls that
			// are out of bounds, for example
		}

	  public void updateBalls() {
	        long curTime = System.currentTimeMillis();
	        if (lastTimeMs < 0) {
	            lastTimeMs = curTime;
	            // this is the first reading, so don't change anything
	            return;
	        }
	        long elapsedMs = curTime - lastTimeMs;
	        lastTimeMs = curTime;
	        	        
	        final float distanceChange = elapsedMs * DISTANCE_CHANGE_PER_MS;
	        
	    	synchronized (LOCK) {
	    		Set<Ball> victims = new HashSet<Ball>();
	    		
	    		for (Ball b : balls) {
	    			b.bY += distanceChange;
	    			if (b.bY > MAX_DISTANCE) {
	    				victims.add(b);
	    			}
	    		}
	    		
	    		if (victims.size() > 0) {
	    			balls.removeAll(victims);
	    			// since a Ball popped, try to get a media player
	    		}
	    	}
	    	
	    }
}