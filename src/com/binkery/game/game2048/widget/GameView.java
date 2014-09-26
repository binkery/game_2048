package com.binkery.game.game2048.widget;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.binkery.common.logs.Logs;
import com.binkery.game.game2048.Cell;
import com.binkery.game.game2048.GameController;
import com.binkery.game.game2048.R;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	private static final String TAG = GameView.class.getSimpleName();

	private SurfaceHolder mSurfaceHolder = null;
	private GameController mController = null;
	private static int mUnitWidth = 0;
	private static int mCellMargin = 0;
	private float mTextSize = 0;
	private Paint mPaint = null;

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public GameView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);
		setLongClickable(true);

		mCellMargin = context.getResources().getDimensionPixelSize(
				R.dimen.cell_margin);
	}

	public void setGameController(GameController controller) {
		mController = controller;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
				getDefaultSize(0, heightMeasureSpec));
		int width = getMeasuredWidth();
		widthMeasureSpec = heightMeasureSpec = MeasureSpec.makeMeasureSpec(
				width, MeasureSpec.EXACTLY);
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
		mUnitWidth = (width - mCellMargin * 5) / 4;
		
		Paint paint = new Paint();
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		paint.setTextSize(20);
		float textWidth = paint.measureText("2048");
		
		mTextSize = mUnitWidth * 0.9f / textWidth * 20;
		drawGame();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Logs.i(TAG, "surfaceDestroyed");
	}

	public void flush() {
		drawGame();
	}

	private void drawGame() {
		Canvas canvas = mSurfaceHolder.lockCanvas();
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		// draw background
		paint.setColor(0xffBCAD9F);
		canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
		// draw grid
		paint.setColor(0xffd6cdc4);
		final int MARGIN = mCellMargin;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				int x = i * (mUnitWidth + MARGIN) + MARGIN;
				int y = j * (mUnitWidth + MARGIN) + MARGIN;
				int r = x + mUnitWidth;
				int b = y + mUnitWidth;
				canvas.drawRect(x, y, r, b, paint);
			}
		}

		List<Cell> cells = mController.getCells();
		for (int i = 0, len = cells.size(); i < len; i++) {
			drawCell(cells.get(i), canvas, paint);
		}
		mSurfaceHolder.unlockCanvasAndPost(canvas);
	}

	private void drawCell(Cell cell, Canvas canvas, Paint paint) {
		final int MARGIN = mCellMargin;
		// paint.setColor(Color.BLUE);
		setRectColor(paint, cell.value);
		
		int x = cell.x * (mUnitWidth + MARGIN) + MARGIN;
		int y = cell.y * (mUnitWidth + MARGIN) + MARGIN;
		int r = x + mUnitWidth;
		int b = y + mUnitWidth;
		canvas.drawRect(x, y, r, b, paint);

		setTextColor(paint, cell.value);
		paint.setTextAlign(Align.CENTER);
		String value = "" + cell.value;
		int fontSize = (int)(mUnitWidth/value.length() * 0.9f);
		paint.setTextSize(mTextSize);
		FontMetrics fm = paint.getFontMetrics(); 
		int lineHeight = (int)(fm.bottom -fm.top);
		canvas.drawText("" + cell.value, x + mUnitWidth / 2,
				y + mUnitWidth - (mUnitWidth - lineHeight)/2 - fm.bottom, paint);

	}

	private int backgournd = 0xffd6cdc4;

	private static void setTextColor(Paint paint, int value) {
		if(value < 8){
			paint.setColor(0xFF776e65);
			return;
		}
		paint.setColor(0xFFf9f6f2);
	}

	private static void setRectColor(Paint paint, int value) {
		int[] color = { 0xFFeee4da, 0xFFede0c8, 0xFFf2b179, 0xFFf59563,
				0xFFf67c5f, 0xFFf65e3b, 0xffedcf72, 0xffedcc61, 0xffedc850,
				0xffedc53f, 0xffedc22e, 0xffedc22e, 0xffedc22e };
		paint.setColor(color[log(value) - 1]);
	}

	public static int log(int value) {
		if ((value & (value - 1)) != 0) {
			return -1;
		}
		int log = 0;
		while (value > 0) {
			value = value >> 1;
			log++;
		}
		return log;
	}

}
