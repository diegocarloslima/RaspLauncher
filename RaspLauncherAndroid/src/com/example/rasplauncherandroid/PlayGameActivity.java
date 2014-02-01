package com.example.rasplauncherandroid;

import android.app.Activity;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

public class PlayGameActivity extends Activity {

	private View mRootView;
	
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private float mCurrentX;
	private SensorWorker mWorker;
	
	private int mMaxFlingVelocity;
	private GestureDetector mGestureDetector;
	private Runnable mRunnable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.play_game);

		mRootView = findViewById(R.id.play_game);
		
		mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(new SensorEventListener() {

			@Override
			public void onSensorChanged(SensorEvent event) {
				mCurrentX = event.values[0];
			}

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {}
			
		}, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

		mMaxFlingVelocity = ViewConfiguration.get(PlayGameActivity.this).getScaledMaximumFlingVelocity();

		mGestureDetector = new GestureDetector(PlayGameActivity.this, new GestureDetector.OnGestureListener() {

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				return false;
			}

			@Override
			public void onShowPress(MotionEvent e) {}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
				return false;
			}

			@Override
			public void onLongPress(MotionEvent e) {}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				final int arrowNumber = Math.round((4 * -velocityY / mMaxFlingVelocity));
				drawArrows(arrowNumber);
				
				new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						NetworkUtils.performRequest(NetworkUtils.BASE_URL + "servoarm/" + "0");
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						NetworkUtils.performRequest(NetworkUtils.BASE_URL + "servoarm/" + "100");
						
						
						return null;
					}
				}.execute();
				
				return true;
			}

			@Override
			public boolean onDown(MotionEvent e) {
				return false;
			}
		});

		mRunnable = new Runnable() {

			@Override
			public void run() {

				mRootView.invalidate();
			}
		};

		mWorker = new SensorWorker();
		mWorker.start();
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mWorker.quit();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mGestureDetector.onTouchEvent(event);
		
		final View circle = findViewById(R.id.play_game_circle);
		switch(event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			final int screenCoords[] = new int[2];
			circle.getLocationOnScreen(screenCoords);
			
			final RectF circleRect = new RectF(screenCoords[0], screenCoords[1], screenCoords[0] + circle.getWidth(), screenCoords[1] + circle.getHeight());
			if(circleRect.contains(event.getRawX(), event.getRawY())) {
				circle.setPressed(true);
			}
			break;
		case MotionEvent.ACTION_UP:
			circle.setPressed(false);
			break;
		}
		
		return super.onTouchEvent(event);
	}

	private void drawArrows(int number) {
		final View emptyArrow1 = findViewById(R.id.play_game_arrow_empty_1);
		final View emptyArrow2 = findViewById(R.id.play_game_arrow_empty_2);
		final View emptyArrow3 = findViewById(R.id.play_game_arrow_empty_3);
		final View emptyArrow4 = findViewById(R.id.play_game_arrow_empty_4);

		emptyArrow1.setVisibility(View.VISIBLE);
		emptyArrow2.setVisibility(View.VISIBLE);
		emptyArrow3.setVisibility(View.VISIBLE);
		emptyArrow4.setVisibility(View.VISIBLE);

		final View fullArrow1 = findViewById(R.id.play_game_arrow_full_1);
		final View fullArrow2 = findViewById(R.id.play_game_arrow_full_2);
		final View fullArrow3 = findViewById(R.id.play_game_arrow_full_3);
		final View fullArrow4 = findViewById(R.id.play_game_arrow_full_4);

		fullArrow1.setVisibility(View.INVISIBLE);
		fullArrow2.setVisibility(View.INVISIBLE);
		fullArrow3.setVisibility(View.INVISIBLE);
		fullArrow4.setVisibility(View.INVISIBLE);

		switch (number) {
		case 4:
			emptyArrow1.setVisibility(View.INVISIBLE);
			fullArrow1.setVisibility(View.VISIBLE);
		case 3:
			emptyArrow2.setVisibility(View.INVISIBLE);
			fullArrow2.setVisibility(View.VISIBLE);
		case 2:
			emptyArrow3.setVisibility(View.INVISIBLE);
			fullArrow3.setVisibility(View.VISIBLE);
		case 1:
			emptyArrow4.setVisibility(View.INVISIBLE);
			fullArrow4.setVisibility(View.VISIBLE);
			break;
		}

		mRootView.invalidate();
		mRootView.removeCallbacks(mRunnable);
		mRootView.postDelayed(mRunnable, 1000);
	}
	
	private final class SensorWorker extends Thread {

		private int speed = 0;
		private int direction = 0;
		private boolean quit;
		private float lastX;

		@Override
		public void run() {
			while(true) {
				
				boolean move = false;
				
				final float deltaX = mCurrentX - this.lastX;
				if(deltaX > 0 && deltaX > 1.5 && this.direction < 0) {
					NetworkUtils.performRequest(NetworkUtils.BASE_URL +  "direction/" + "10");
					move = true;
				} else if(deltaX < 0 && deltaX < -1.5 && this.direction > 0) {
					NetworkUtils.performRequest(NetworkUtils.BASE_URL +  "direction/" + "-10");
					move = true;
				}

				if(move && this.speed == 0) {
					NetworkUtils.performRequest(NetworkUtils.BASE_URL +  "speed/" + 14);
				} else if(!move && this.speed > 0) {
					NetworkUtils.performRequest(NetworkUtils.BASE_URL +  "speed/" + 0);
				}
				
				final View emptyLeftArrow = findViewById(R.id.play_game_arrow_left_empty);
				final View fullLeftArrow = findViewById(R.id.play_game_arrow_left_full);
				
				final View emptyRightArrow = findViewById(R.id.play_game_arrow_right_empty);
				final View fullRightArrow = findViewById(R.id.play_game_arrow_right_full);
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						emptyLeftArrow.setVisibility(View.VISIBLE);
						fullLeftArrow.setVisibility(View.INVISIBLE);
						
						emptyRightArrow.setVisibility(View.VISIBLE);
						fullRightArrow.setVisibility(View.INVISIBLE);
						
						if(deltaX >= 1) {
							emptyLeftArrow.setVisibility(View.INVISIBLE);
							fullLeftArrow.setVisibility(View.VISIBLE);
						} else if(deltaX <= -1) {
							emptyRightArrow.setVisibility(View.INVISIBLE);
							fullRightArrow.setVisibility(View.VISIBLE);
						}
						
						mRootView.invalidate();
					}
				});

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {}

				if(this.quit) {
					return;
				}
			}
		}

		public void quit() {
			this.quit = true;
			interrupt();
		}
	}
}
