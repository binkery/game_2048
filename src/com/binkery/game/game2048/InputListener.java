package com.binkery.game.game2048;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

import com.binkery.game.game2048.tools.Logs;

public class InputListener extends SimpleOnGestureListener {

	private static final String TAG = InputListener.class.getSimpleName();

	private GameController mController = null;

	public InputListener(GameController controller) {
		mController = controller;
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return super.onSingleTapUp(e);
	}

	@Override
	public void onLongPress(MotionEvent e) {
		super.onLongPress(e);
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return super.onScroll(e1, e2, distanceX, distanceY);
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		float x = (velocityX > 0 ? velocityX : -velocityX);
		float y = (velocityY > 0 ? velocityY : -velocityY);

		if (x > y) {
			if (velocityX > 0) {
				// move right
				Logs.i(TAG, "Move Right");
				mController.moveRight();
			} else {
				// move left
				Logs.i(TAG, "Move Left");
				mController.moveLeft();
			}
		} else {
			if (velocityY > 0) {
				// move down
				Logs.i(TAG, "Move Down");
				mController.moveDown();
			} else {
				// move up
				Logs.i(TAG, "Move Up");
				mController.moveUp();
			}
		}
		return super.onFling(e1, e2, velocityX, velocityY);
	}

	@Override
	public void onShowPress(MotionEvent e) {
		super.onShowPress(e);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return super.onDown(e);
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {
		return super.onDoubleTap(e);
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		return super.onDoubleTapEvent(e);
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		return super.onSingleTapConfirmed(e);
	}

}
