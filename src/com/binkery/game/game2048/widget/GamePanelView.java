package com.binkery.game.game2048.widget;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.binkery.game.game2048.Cell;
import com.binkery.game.game2048.GameController;
import com.binkery.game.game2048.InputListener;
import com.binkery.game.game2048.R;
import com.binkery.game.game2048.tools.Logs;

public class GamePanelView extends SurfaceView implements
		SurfaceHolder.Callback {

	private static final String TAG = GamePanelView.class.getSimpleName();

	private SurfaceHolder mSurfaceHolder = null;
	private GameController mController = null;
	private static int mUnitWidth = 0;
	private static int mCellMargin = 0;
	private float mTextSize = 0;
	private Paint mPaint = null;

	private int mGapWidth = 0;
	private int mAppNameWidth = 0;
	
	private int mCurScore = 0;
	private int mBestScore = 0;

	public GamePanelView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public GamePanelView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public GamePanelView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);
	}

	public void setGameController(GameController controller) {
		mController = controller;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
				getDefaultSize(0, heightMeasureSpec));
		int width = getMeasuredWidth();

		mGapWidth = getResources().getDimensionPixelSize(R.dimen.view_margin);
		mAppNameWidth = (width - mGapWidth * 2) / 3;

		widthMeasureSpec = MeasureSpec.makeMeasureSpec(width,
				MeasureSpec.EXACTLY);
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(mAppNameWidth,
				MeasureSpec.EXACTLY);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Logs.i(TAG, "surfaceCreated");
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Logs.i(TAG, "surfaceChanged " + width + "," + height);
		mTextSize = mUnitWidth / 4 * 0.9f;
		drawGame();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Logs.i(TAG, "surfaceDestroyed");
	}

	public void updateScore(int score) {
		mCurScore = score;
		drawGame();
	}
	
	public void setScoreAndBest(int score,int best){
		mCurScore = score;
		mBestScore = best;
	}

	private void drawGame() {
		Canvas canvas = mSurfaceHolder.lockCanvas();
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setTypeface(Typeface.DEFAULT_BOLD);

		paint.setColor(0xfffaf8ef);
		canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

		drawAppName(canvas, paint);
		drawScoreTitle(canvas, paint);
		drawBestTitle(canvas, paint);
		mSurfaceHolder.unlockCanvasAndPost(canvas);
	}

	private void drawAppName(Canvas canvas, Paint paint) {
		paint.setColor(0xFFECC400);
		canvas.drawRect(0, 0, mAppNameWidth, mAppNameWidth, paint);

		paint.setColor(0xFFFAFEFF);
		paint.setTextAlign(Align.CENTER);
		int fontSize = mAppNameWidth / 4;
		paint.setTextSize(fontSize);
		int appTextWidth = (int) paint.measureText("2048");
		float size = mAppNameWidth * 0.9f / appTextWidth * fontSize;
		paint.setTextSize(size);
		canvas.drawText("2048", (mAppNameWidth >> 1) ,
				(mAppNameWidth >> 1) + (fontSize >> 1), paint);
	}

	private void drawScoreTitle(Canvas canvas, Paint paint) {
		paint.setColor(0xFFBBADA0);
		int l = mAppNameWidth + mGapWidth;
		int t = 0;
		int r = l + mAppNameWidth;
		int b = (mAppNameWidth) * 2 / 3;
		canvas.drawRect(l, t, r, b, paint);
		
		paint.setColor(0xFFEFE3D5);
		int fontHeight = b / 3;
		paint.setTextSize(fontHeight);
//		int txtWidth = (int)paint.measureText("SCORE");
		canvas.drawText("SCORE", l + (mAppNameWidth )/2, t + fontHeight, paint);
		
		paint.setColor(0xFFFEFBF5);
//		int scoreWidth = (int)paint.measureText("" + mCurScore);
		canvas.drawText("" + mCurScore, l + (mAppNameWidth)/2, b - fontHeight/2, paint);
	}

	private void drawBestTitle(Canvas canvas, Paint paint) {
		paint.setColor(0xFFBBADA0);
		int l = (mAppNameWidth + mGapWidth) * 2;
		int t = 0;
		int r = l + mAppNameWidth;
		int b = (mAppNameWidth) * 2 / 3;
		
		canvas.drawRect(l, t, r, b, paint);
		
		paint.setColor(0xFFEFE3D5);
		int fontHeight = b / 3;
		paint.setTextSize(fontHeight);
//		int txtWidth = (int)paint.measureText("BEST");
		canvas.drawText("BEST", l + (mAppNameWidth )/2, t + fontHeight, paint);
		
		paint.setColor(0xFFFEFBF5);
//		int scoreWidth = (int)paint.measureText("" + mBestScore);
		canvas.drawText("" + mBestScore, l + (mAppNameWidth )/2, b - fontHeight/2, paint);
	}
}
