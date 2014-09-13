package com.binkery.game.game2048.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Tools {
	
	private static final String NAME = "game_2048";
	private static final String DATA = "data";
	private static final String SCORE = "score";
	private static final String BEST = "best";
	
	public static void saveData(Context context,int[][] data,int score){
		SharedPreferences pre = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		Editor edit = pre.edit();
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i<4;i++){
			for(int j = 0;j<4;j++){
				sb.append(data[i][j]).append(",");
			}
		}
		edit.putString(DATA, sb.toString());
		edit.putInt(SCORE, score);
		edit.commit();
	}
	
	public static int[][] getData(Context context){
		SharedPreferences pre = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		String data = pre.getString(DATA, null);
		if(data == null){
			return null;
		}
		String[] str = data.split(",");
		int[][] map = new int[4][4];
		for(int i = 0 ; i < 16;i++){
			map[i/4][i%4] = Integer.parseInt(str[i]);
		}
		return map;
	}

	public static int getScore(Context context){
		SharedPreferences pre = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		return pre.getInt(SCORE, 0);
	}
	
	public static int getBest(Context context){
		SharedPreferences pre = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		return pre.getInt(BEST, 0);
	}
	
	public static void saveBest(Context context,int best){
		SharedPreferences pre = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		Editor edit = pre.edit();
		edit.putInt(BEST, best);
		edit.commit();
	}
}
