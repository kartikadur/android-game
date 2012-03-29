package net.kartik.sprite;

import net.kartik.sprite.background.BackgroundAnimated;
import net.kartik.sprite.models.ElaineAnimated;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

	private static final String TAG = MainGamePanel.class.getSimpleName();

	private MainThread thread;
	private ElaineAnimated elaine;

	private BackgroundAnimated space;
	
	//the fps to be displayed
	private String avgFps;
	public void setAvgFps(String avgFps) {
		this.avgFps = avgFps;
	}
	
	public MainGamePanel(Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
	
		//create space and load bitmap
		space = new BackgroundAnimated(
				BitmapFactory.decodeResource(getResources(), R.drawable.background_a),
				BitmapFactory.decodeResource(getResources(), R.drawable.background_b)
		);
		
		// create Elaine and load bitmap
		elaine = new ElaineAnimated(
				BitmapFactory.decodeResource(getResources(), R.drawable.zero_flight) 
				, 10, 40	// initial position
				, 70, 45	// width and height of sprite
				, 10, 5);	// FPS and number of frames in the animation
		
		// create the game loop thread
		thread = new MainThread(getHolder(), this);
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// at this point the surface is created and
		// we can safely start the game loop
		thread.setRunning(true);
		thread.start();
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface is being destroyed");
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
		Log.d(TAG, "Thread was shut down cleanly");
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// handle touch
		}
			return true;
		}
		
		public void render(Canvas canvas) {
			canvas.drawColor(Color.BLACK);
			space.draw(canvas);
			elaine.draw(canvas);
			// display fps
			displayFps(canvas, avgFps);
		}
		
		/**
		* This is the game update method. It iterates through all the objects
		* and calls their update method if they have one or calls specific
		* engine's update method.
		*/
		public void update() {
			elaine.update(System.currentTimeMillis());
			space.update();
		}
		
		private void displayFps(Canvas canvas, String fps) {
		if (canvas != null && fps != null) {
			Paint paint = new Paint();
			paint.setARGB(255, 255, 255, 255);
			canvas.drawText(fps, this.getWidth() - 50, 20, paint);
		}
	}

}