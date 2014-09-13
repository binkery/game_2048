package com.binkery.game.game2048.tools;

import android.util.Log;

public class Logs {

	private static final String SUF = "bky-game2048-";

	public static final void i(String TAG, String msg) {
		Log.i(SUF + TAG, msg);
	}

	public static final void e(String TAG, String msg) {
		Log.e(SUF + TAG, msg);
	}

	public static final void v(String TAG, String msg) {
		Log.v(SUF + TAG, msg);
	}

	public static final void w(String TAG, String msg) {
		Log.w(SUF + TAG, msg);
	}

	public static final void d(String TAG, String msg) {
		Log.d(SUF + TAG, msg);
	}
}
