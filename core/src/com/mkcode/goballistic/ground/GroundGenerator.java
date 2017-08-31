package com.mkcode.goballistic.ground;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mkcode.goballistic.fonts.FontManager;
import com.mkcode.goballistic.util.Constants;

public class GroundGenerator {

	private static int turretLevel = 0;
	private static boolean DEBUG = true;
	
	public static Ground generateGround() {
		
		Ground ground = new Ground();
		List<Integer> groundElements = ground.getGroundElements();
		Integer[][] groundElementMatrix = ground.getGroundElementMatrix();
		
		Random random = new Random(System.currentTimeMillis());
		
		int previous = Constants.START_GROUND_HEIGHT;
		
		for(int i = 0; i < Constants.GROUND_WIDTH; i++) {
			// generate random number between -maxdelta and maxdelta
			int delta = 0;
			// tank on level ground
			if(i <= Constants.PLATFORM_OFFSET || i >= Constants.PLATFORM_WIDTH + Constants.PLATFORM_OFFSET) {
				delta = random.nextInt(Constants.MAX_GROUND_ABS_HEIGHT_DELTA * 2 + 1);
				delta -= Constants.MAX_GROUND_ABS_HEIGHT_DELTA;
			}
			
			// clamp height between max and min values
			int height = Math.max(Constants.MIN_GROUND_HEIGHT, Math.min(Constants.MAX_GROUND_HEIGHT, previous + delta));
			
			if(turretLevel == 0 && i >= Constants.PLATFORM_OFFSET) {
				turretLevel = Constants.GAME_AREA_HEIGHT - height + Constants.TURRET_HEIGHT;
				System.out.println("Turret level: " + turretLevel);
			}
			
			groundElements.add(height);
			System.out.println("Height: " + height);
			previous = height;
		}
		
//		for(int y = Constants.MAX_GROUND_HEIGHT - 1; y >= 0; y--) {
		for(int y = 0; y < Constants.MAX_GROUND_HEIGHT; y++) {
			for(int x = 0; x < Constants.GROUND_WIDTH; x++) { 
				if(y + 1 >= groundElements.get(x))
					groundElementMatrix[y][x] = 1;
				else
					groundElementMatrix[y][x] = 0;
				if(DEBUG) System.out.print(groundElementMatrix[y][x]);
			}
			if(DEBUG) System.out.println();
		}
		
		return ground;
	}

	public static int getTurretLevel() {
		return turretLevel;
	}
	
	public static void resetTurretLevel() {
		turretLevel = 0;
	}
}
