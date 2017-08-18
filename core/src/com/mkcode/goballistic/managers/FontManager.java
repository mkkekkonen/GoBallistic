package com.mkcode.goballistic.managers;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class FontManager {

	private FreeTypeFontGenerator generator; 
	private Map<String, BitmapFont> fontMap;
	
	public FontManager() {
		fontMap = new HashMap<String, BitmapFont>();
		generator = new FreeTypeFontGenerator(Gdx.files.internal("data-latin.ttf"));
		createFont("font10", 10);
		createFont("inputFont", 30);
		createFont("labelFont", 25);
	}
	
	public BitmapFont getFont(String key) {
		return fontMap.get(key);
	}
	
	public void dispose() {
		generator.dispose();
		for(Map.Entry<String, BitmapFont> entry : fontMap.entrySet())
			entry.getValue().dispose();
	}
	
	private void createFont(String key, int size) {
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = size;
		parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
		fontMap.put(key, generator.generateFont(parameter));
	}
}
