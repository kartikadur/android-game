package net.kartik.sprite.background;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class BackgroundAnimated {

	private Bitmap mNearBG;
	private Bitmap mFarBG;
	
	private int mNearBGX;
	private int mNearBGXNew;
	
	private int mFarBGX;
	private int mFarBGXNew;
	
	public BackgroundAnimated(Bitmap farBG, Bitmap nearBG){
		this.mNearBG = nearBG;
		this.mFarBG = farBG;
		mFarBGX =0;
		mFarBGXNew = mFarBG.getWidth();
		mNearBGX =0;
		mNearBGXNew = mNearBG.getWidth();
	}
	
	public void update(){
		mFarBGX -=1;
		mNearBGX -= 4;
		mFarBGXNew = mFarBG.getWidth() - (-mFarBGX);
		mNearBGXNew = mNearBG.getWidth() - (-mNearBGX);
	}
	
	public void draw(Canvas canvas){
		if(mFarBGXNew<=0){
			mFarBGX=0;
			canvas.drawBitmap(mFarBG, mFarBGX, 0, null);
		}else{
			canvas.drawBitmap(mFarBG, mFarBGX, 0, null);
			canvas.drawBitmap(mFarBG, mFarBGXNew, 0, null);
		}
		if(mNearBGXNew<=0){
			mNearBGX=0;
			canvas.drawBitmap(mNearBG, mNearBGX, 0, null);
		}else{
			canvas.drawBitmap(mNearBG, mNearBGX, 0, null);
			canvas.drawBitmap(mNearBG, mNearBGXNew, 0, null);
		}
	}
}
