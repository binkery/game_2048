package com.binkery.game.game2048;


public class Cell {

	public int value = 2;
	public int x = 0;
	public int y = 0;

	public int targetX = 0;
	public int targetY = 0;
	public int targetValue = 2;
	
	public boolean isEmpty(){
		return value < 2;
	}
	
	public boolean isCanMove = false;
	public boolean isCanCombined = true;
}
