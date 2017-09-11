package com.mkcode.goballistic.util;

import com.mkcode.goballistic.math.Vector2;

public class Constants {

	public static final int
			BARREL_LENGTH = 40,
			BULLET_DIM = 1,
			BULLET_WEIGHT = 7,
			BUTTON_DIM = 60,
			CONTROLS_AREA_HEIGHT = 11,
			EXIT_BUTTON_H = 40,
			EXIT_BUTTON_MARGIN = 10,
			EXIT_BUTTON_W = 50,
			GAME_AREA_HEIGHT = 37,
			GROUND_ELEMENT_WIDTH = 1,
			GROUND_TOP_OFFSET = 6,
			GROUND_WIDTH = 64,
			HIGH_SCORES_FONT_HEIGHT = 30,
			HIGH_SCORES_START_Y = 380,
			HIGH_SCORES_ROW_MARGIN = 5,
			HIGH_SCORES_POINTS_OFFSET_X = 300,
			INPUT_ANGLE_DEFAULT_VALUE = 45,
			INPUT_FORCE_DEFAULT_VALUE = 500,
			LABEL_OFFSET_X = 225,
			LARGE_TARGET_AMOUNT = 1,
			LARGE_TARGET_DIM = 2,
			LARGE_TARGET_WEIGHT = 30,
			MAIN_MENU_BUTTON_OFFSET_X = 100,
			MAIN_MENU_BUTTON_HISCORES_Y = 255,
			MAIN_MENU_BUTTON_PLAY_Y = 305,
			MAIN_MENU_BUTTON_W = 320,
			MAIN_MENU_BUTTON_H = 40,
			MAX_GROUND_ABS_HEIGHT_DELTA = 6,
			MAX_GROUND_HEIGHT = 27,
			MIN_GROUND_HEIGHT = 1,
			NUMBER_INPUT_OFFSET_X = 10,
			PIXELS_PER_METER = 10,
			PLATFORM_OFFSET = 5,
			PLATFORM_WIDTH = 6,
			SCOREBOARD_NAME_OFFSET_Y = 400,
			SCOREBOARD_POINTS_OFFSET_Y = 280,
			SCOREBOARD_RANKING_OFFSET_Y = 240,
			SCOREBOARD_SCORE_OFFSET_X = 320,
			SCOREBOARD_SCORE_TITLE_OFFSET_X = 40,
			SCOREBOARD_SHOTS_OFFSET_Y = 320,
			SCOREBOARD_TIME_OFFSET_Y = 360,
			SCOREBOARD_TITLE_OFFSET_X = 30,
			SCOREBOARD_TITLE_OFFSET_Y = 450,
			SCORE_LABEL_OFFSET_X = 440,
			SCORE_SHOTS_OFFSET_Y = 440,
			SCORE_TIME_OFFSET_Y = 400,
			SCORE_VALUE_OFFSET_X = 560,
			START_GROUND_HEIGHT = 12,
			SMALL_TARGET_AMOUNT = 0,
			SMALL_TARGET_DIM = 1,
			SMALL_TARGET_WEIGHT = 15,
			TITLE_OFFSET_X = 130,
			TITLE_OFFSET_Y = 410,
			TURRET_HEIGHT = 2,
			TURRET_OFFSET = 6,
			TURRET_WIDTH = 4,
			WND_ELE_HEIGHT = 48,
			WND_ELE_WIDTH = 64,
			WND_HEIGHT = 480,
			WND_WIDTH = 640;
	
	public static final float
			EPSILON = 0.00001f,
			BULLET_R = 0.5f;
	
	public static final String
			ASSETS_DIR = "assets/",
			HIGH_SCORES_FILE = "highscores.dat";
	
	public static final Vector2
			GRAVITY = new Vector2(0, -9.81f);
}
