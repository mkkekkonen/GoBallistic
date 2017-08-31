package com.mkcode.goballistic.inputfields;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mkcode.goballistic.fonts.FontManager;
import com.mkcode.goballistic.math.Rect;
import com.mkcode.goballistic.math.Vector2;

public abstract class AbstractInput {

	protected BitmapFont font;
	
	protected Texture background;
	protected Rect rect;
	
	protected String title;
	protected static final int
			marginX = 10,
			marginY = 32;
	
	/**
	 * Here x and y are not in world space but in screen space.
	 * @param x int left border in screen space
	 * @param y int bottom border in screen space
	 */
	protected AbstractInput(BitmapFont font, String fileName, int x, int y, String title) {
		this.font = font;
		this.background = new Texture(Gdx.files.internal(fileName));
		this.rect = new Rect(x, y, this.background.getWidth(), this.background.getHeight());
		this.title = title;
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(background, rect.getX(), rect.getY());
	}
	
	public void dispose() {
		this.background.dispose();
	}
	
	public Rect getRect() {
		return this.rect;
	}
}
