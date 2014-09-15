package com.binkery.game.game2048;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.binkery.game.game2048.tools.SharedPreferencesTools;
import com.binkery.game.game2048.widget.GamePanelView;
import com.binkery.game.game2048.widget.GameView;

public class GameController extends Activity {

	private GameView mGameView = null;
	private GamePanelView mPanelView = null;
	private int[][] mMap = new int[4][4];
	private Random mRange = new Random();
	private GestureDetector mGestureDetector = null;

	private int mCurScore = 0;
	private int mBestScore = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mGameView = (GameView) findViewById(R.id.gameview);
		mPanelView = (GamePanelView) findViewById(R.id.gamepanel);
		mGameView.setGameController(this);

		getFromStorage();
		if (mMap == null) {
			buildNewGame();
		}
		mPanelView.setScoreAndBest(mCurScore, mBestScore);
		mGestureDetector = new GestureDetector(this, new InputListener(this));
		mGameView.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return mGestureDetector.onTouchEvent(event);
			}
		});
	}

	private void buildNewGame() {
		mMap = new int[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				mMap[i][j] = 0;
			}
		}
		buildRandom();
		buildRandom();
		mCurScore = 0;
		SharedPreferencesTools.saveData(this, mMap, mCurScore);
	}

	protected void reStartNewGame() {
		buildNewGame();
		mGameView.flush();
		mPanelView.setScoreAndBest(mCurScore, mBestScore);
		mPanelView.updateScore(mCurScore);
	}

	private void getFromStorage() {
		mCurScore = SharedPreferencesTools.getScore(this);
		mBestScore = SharedPreferencesTools.getBest(this);
		mMap = SharedPreferencesTools.getData(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
		SharedPreferencesTools.saveData(this, mMap, mCurScore);
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			buildRandom();
			mGameView.flush();
		}

	};

	private void move() {
		mGameView.flush();
		mPanelView.updateScore(mCurScore);
		mHandler.sendEmptyMessageDelayed(0, 200);
	}

	private void buildRandom() {
		int[] range = new int[16];
		int count = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (mMap[i][j] == 0) {
					range[count] = i * 4 + j;
					count++;
				}
			}
		}
		int random = mRange.nextInt(count);
		int data = range[random];
		int x = data / 4;
		int y = data % 4;
		mMap[x][y] = mRange.nextInt(11) / 10 * 2 + 2;
		if (count == 1) {
			if (checkGameOver()) {
				gameOver();
			}
		}
	}

	private void gameOver() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("GameOver");
		builder.setMessage("Your score is " + mCurScore + " , best score is "
				+ mBestScore);
		builder.setNegativeButton("OK", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (mCurScore > mBestScore) {
					SharedPreferencesTools.saveBest(GameController.this, mCurScore);
					mBestScore = mCurScore;
				}
				dialog.dismiss();
				buildNewGame();
				mGameView.flush();
				mPanelView.setScoreAndBest(mCurScore, mBestScore);
				mPanelView.updateScore(mCurScore);
			}
		});
		builder.setCancelable(false);
		builder.create().show();
	}

	private boolean checkGameOver() {

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				int cur = mMap[i][j];
				if (i < 3) {
					if (cur == mMap[i + 1][j]) {
						return false;
					}
				}
				if (j < 3) {
					if (cur == mMap[i][j + 1]) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public List<Cell> getCells() {
		List<Cell> list = new ArrayList<Cell>();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (mMap[i][j] > 0) {
					Cell cell = new Cell();
					cell.x = i;
					cell.y = j;
					cell.value = mMap[i][j];
					list.add(cell);
				}
			}
		}
		return list;
	}

	private boolean isMoved(int[][] origen, int[][] result) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (origen[i][j] != result[i][j]) {
					return true;
				}
			}
		}
		return false;
	}

	public void moveUp() {
		int[][] result = new int[4][4];
		for (int x = 0; x < 4; x++) {
			int index = 0;
			for (int y = 0; y <= 3; y++) {
				int value = mMap[x][y];
				if (value < 2) {
					continue;
				}
				if (result[x][index] < 2) {
					result[x][index] = value;
					continue;
				}
				if (result[x][index] == value) {
					result[x][index] += value;
					mCurScore += value * 2;
					index++;
					continue;
				}
				index++;
				result[x][index] = value;
			}

		}
		if (isMoved(mMap, result)) {
			mMap = result;
			move();
		}
	}

	public void moveRight() {
		int[][] result = new int[4][4];
		for (int y = 0; y < 4; y++) {
			int index = 3;
			for (int x = 3; x >= 0; x--) {
				int value = mMap[x][y];
				if (value < 2) {
					continue;
				}
				if (result[index][y] < 2) {
					result[index][y] = value;
					continue;
				}
				if (result[index][y] == value) {
					result[index][y] += value;
					index--;
					mCurScore += value * 2;
					continue;
				}
				index--;
				result[index][y] = value;
			}

		}
		if (isMoved(mMap, result)) {
			mMap = result;
			move();
		}
	}

	public void moveLeft() {
		int[][] result = new int[4][4];
		for (int y = 0; y < 4; y++) {
			int index = 0;
			for (int x = 0; x <= 3; x++) {
				int value = mMap[x][y];
				if (value < 2) {
					continue;
				}
				if (result[index][y] < 2) {
					result[index][y] = value;
					continue;
				}
				if (result[index][y] == value) {
					result[index][y] += value;
					mCurScore += value * 2;
					index++;
					continue;
				}
				index++;
				result[index][y] = value;
			}

		}
		if (isMoved(mMap, result)) {
			mMap = result;
			move();
		}
	}

	public void moveDown() {
		int[][] result = new int[4][4];
		for (int x = 0; x < 4; x++) {
			int index = 3;
			for (int y = 3; y >= 0; y--) {
				int value = mMap[x][y];
				if (value < 2) {
					continue;
				}
				if (result[x][index] < 2) {
					result[x][index] = value;
					continue;
				}
				if (result[x][index] == value) {
					result[x][index] += value;
					mCurScore += value * 2;
					index--;
					continue;
				}
				index--;
				result[x][index] = value;
			}

		}
		if (isMoved(mMap, result)) {
			mMap = result;
			move();
		}
	}
}
