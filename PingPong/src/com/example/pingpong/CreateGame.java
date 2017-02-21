package com.example.pingpong;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CreateGame extends SurfaceView implements SurfaceHolder.Callback {
	
	private float ballX;
	private float ballY;
	private int RADIUS = 35;
	private Paint paint;
	private Paint rec1paint;
	private PingPong.GameThread thread;
	public int direction = 1;
	public int prev;
	public int width;
	public int height;
	Context context;
	private Paint mPaint,scorePaint;
	private Bitmap mBitmap;
	private Paint mBitmapPaint;
	private Canvas mCanvas;
	private boolean startMovingBall = false;
	public boolean ballInsideScreen = true;
	private float tg, sin, cos, cX, cY;
	private int SPEED = 15;
	private boolean datinhhuong = false;
	long startTime;
	public float ax1, ay1, ax2, ay2;
	public float bx1, by1, bx2, by2;
	private boolean player1 = false;
	private boolean player2 = false;
	private boolean onPlay = false;
	private int sizeX,sizeY;
	private float hdle1X,hdle1Y,hdle2X,hdle2Y;
	MediaPlayer mediaPlayer;
	
	@SuppressWarnings("deprecation")
	public CreateGame(Context context) {
		super(context);

		initGame();
		Bitmap background = BitmapFactory.decodeResource(this.getResources(),R.drawable.bg);	       

		hdle1X = 350;
		hdle1Y = 800;
		hdle2X = 350;
		hdle2Y = 200;
		sizeX = 150;
		sizeY = 150;

		getHolder().addCallback(this);
		this.thread = new PingPong.GameThread(getHolder(), this);
		mediaPlayer = MediaPlayer.create(context, R.raw.hit);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.BLUE);

		rec1paint = new Paint();
		rec1paint.setColor(Color.BLACK);
		rec1paint.setStyle(Paint.Style.FILL_AND_STROKE);
		rec1paint.setStrokeWidth(3);
		//
		scorePaint = new Paint();
		scorePaint.setColor(Color.WHITE);
		scorePaint.setTextSize(30);
	}

	public void initGame() {
		ballX = 350;
		ballY = 700;
		ball1X = 350;ball1Y = 100;
		ball2X = 350;ball2Y = 900;
		cX = SPEED;
		cY = SPEED;
	}

	private int point1 = 0;
	private int point2 = 0;

	public void updateGame() {
		if(startMovingBall){
			ballX += cX;
			ballY += cY;
		}
		 cX = (float) (cX * 0.995);
		 cY = (float) (cY * 0.995);
		if (ballY <= this.RADIUS && ballX>200 && ballX < 550) {
			point1 += 1;
			startMovingBall = false;
			initGame();
		} else if (ballY >= getHeight()-this.RADIUS && ballX>200 && ballX < 550) {
			point2 += 1;
			startMovingBall = false;
			initGame();
		}

		if (ballX >= (getWidth() - this.RADIUS)) {
			cX = -cX;
		}
		if (ballX <= this.RADIUS) {
			cX = -cX;
		}
		if (ballY >= getHeight() - this.RADIUS) {
			cY = -cY;
		}
		if (ballY <= this.RADIUS) {
			cY = -cY;
		}
		if (ballCollision(ballX,ballY,35,ball1X,ball1Y,75)) {
			kickoff();
			mediaPlayer.start();
			ballPush(ball1X,ball1Y,ballX,ballY);
		}

		if (ballCollision(ballX,ballY,35,ball2X,ball2Y,75)) {		
			kickoff();
			mediaPlayer.start();
			ballPush(ball2X,ball2Y,ballX,ballY);
		}
	}

	public void kickoff(){
		if(startMovingBall == false){
			startMovingBall = true;
		}
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);

	}

	private void ballPush(float ball2X,float ball2Y,float ballX,float ballY) {
		tg = Math.abs ( (ball2X - ballX) / (ball2Y - ballY) );
		cos = (float) Math.sqrt(1 / (1 + tg * tg));
		sin = (float) Math.sqrt(1 - cos * cos);
		cX = SPEED * sin;
		cY = SPEED * cos;
		if (ball2X - ballX >= 0 && ball2Y - ballY <= 0) {
			direction = 1;
		}
		if (ball2X - ballX >= 0 && ball2Y - ballY >= 0) {
			direction = 2;
		}
		if (ball2X - ballX <= 0 && ball2Y - ballY >= 0) {
			direction = 3;
		}
		if (ball2X - ballX <= 0 && ball2Y - ballY <= 0) {
			direction = 4;
		}
		switch (direction) {
		case 1:
			cX = -cX;
			break;
		case 2:
			cX = -cX;
			cY = -cY;
			break;
		case 3:
			cY = -cY;
			break;
		case 4:
			break;
		}
	}
	public boolean ballCollision(float x1, float y1, float ra1, float x2, float y2, float ra2) {
		if ( Math.sqrt ((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2)) <= (ra1 + ra2) )
			return true;
		else return false;
	}
	
//	private void checkBall() {
//		if (mX < this.RADIUS || getWidth() - mX < this.RADIUS
//				|| mY < this.RADIUS || getHeight() - mY < this.RADIUS) {
//			ballInsideScreen = false;
//		}
//	}
	
	private void touch_start() {
//		startMovingBall = true;
	}

	private void touch_move() {
		
	}

	private void touch_up() {

	}

	public int id;
	public float ball2X, ball2Y;
	public float ball1X, ball1Y;
	// int count = 0;
	public boolean onTouchEvent(MotionEvent event) {

		int pointerCount = event.getPointerCount();
		for (int i = 0; i < pointerCount; i++) {
			id = event.getPointerId(i);
			int action = event.getActionMasked();
			int actionIndex = event.getActionIndex();
			if (event.getY(i) < getHeight()/2-50) {
				ball1X = event.getX(i);
				ball1Y = event.getY(i);
			}
			else if (event.getY(i) > getHeight()/2+50) {
				ball2X = event.getX(i);
				ball2Y = event.getY(i);
			}
			switch (action) {
			case MotionEvent.ACTION_DOWN:
				touch_start();
				invalidate();
				break;
			case MotionEvent.ACTION_UP:
				touch_up();
				invalidate();
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				touch_start();
				break;
			case MotionEvent.ACTION_POINTER_UP:
				touch_up();
				break;
			case MotionEvent.ACTION_MOVE:
				touch_move();
				invalidate();
				break;
			}
		}
		return true;
	}

	//Background
		Bitmap background = BitmapFactory.decodeResource(this.getResources(),R.drawable.ground);
		Bitmap bg = Bitmap.createScaledBitmap(background, 720,1090, true);
		//Draw puck
		Bitmap pk = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.puck);
		Bitmap puck = Bitmap.createScaledBitmap(pk, 70, 70, true);
		//Draw handle 1
		Bitmap hdle = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.handle1);
		Bitmap handle1 = Bitmap.createScaledBitmap(hdle, 150, 150, true);
		//Draw handle 2
		Bitmap hdle2 = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.handle2);
		Bitmap handle2 = Bitmap.createScaledBitmap(hdle2, 150, 150, true);
		

	private float puckX,puckY;
	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(bg, 0, 0, null);
		hdle1X = ball1X;hdle1Y = ball1Y;
		hdle2X = ball2X;hdle2Y = ball2Y;
		canvas.drawBitmap(handle1, hdle1X-75, hdle1Y-75, null);
		canvas.drawBitmap(handle2, hdle2X-75, hdle2Y-75, null);
		if(startMovingBall){
				puckX = ballX - 35;
				puckY = ballY - 35;
				canvas.drawBitmap(puck,puckX, puckY,null);
			}else{
				canvas.drawBitmap(puck,350-35, 700-35,null);
			}
			canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
		// Paint Score	
			canvas.drawText("SCORE :" + point1 + " - " + point2, 30,500, scorePaint);
	}

	public void clearView(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
	}
	private int count = 0;
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		if(count==0){
		thread.start();
		}
		count++;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
//		thread.interrupt();
	}
}