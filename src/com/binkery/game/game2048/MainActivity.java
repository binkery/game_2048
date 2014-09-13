package com.binkery.game.game2048;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends GameController {

	private static final String TAG = MainActivity.class.getSimpleName();

	private int mToastCounter = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.menu_about:
			Intent intent = new Intent(this, AboutActivity.class);
			startActivity(intent);
			break;
		case R.id.menu_restart:
			reStartNewGame();
			break;
		case R.id.menu_undo:
			showUndoToast();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void showUndoToast() {
		if (mToastCounter == 0) {
			Toast.makeText(this, "玩这游戏你还撤销？！鄙视！", Toast.LENGTH_SHORT).show();
		} else if (mToastCounter == 1) {
			Toast.makeText(this, "还撤销？！再次鄙视", Toast.LENGTH_SHORT).show();
		} else if (mToastCounter == 2) {
			Toast.makeText(this, "告诉你，没有撤销这功能！", Toast.LENGTH_SHORT).show();
		} else if (mToastCounter == 3) {
			Toast.makeText(this, "其实是我懒得做撤销，不好意思！", Toast.LENGTH_SHORT).show();
		} else if (mToastCounter == 4) {
			Toast.makeText(this, "跟你说没撤销了你还点，手溅！", Toast.LENGTH_SHORT).show();
		} else if (mToastCounter == 5) {
			Toast.makeText(this, "有意思吗？", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "没有撤销功能，别点了！！！", Toast.LENGTH_SHORT).show();
			return;
		}
		mToastCounter++;
	}

}
