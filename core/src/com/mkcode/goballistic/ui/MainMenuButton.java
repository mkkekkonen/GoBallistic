package com.mkcode.goballistic.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.math.Rect;
import com.mkcode.goballistic.math.Vector2;
import com.mkcode.goballistic.resources.Resources;
import com.mkcode.goballistic.util.Constants;

public class MainMenuButton extends AbstractUIElement {

	private Rect rect;
	private BitmapFont menuFont;
	
	private String resourcesKey;
	
	public MainMenuButton(Vector2 bottomLeft, BitmapFont menuFont, String resourcesKey) {
		super("menubg.png");
		this.rect = new Rect(bottomLeft, Constants.MAIN_MENU_BUTTON_W, Constants.MAIN_MENU_BUTTON_H);
		this.menuFont = menuFont;
		this.resourcesKey = resourcesKey;
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(this.texture, this.rect.getX(), this.rect.getY());
		this.menuFont.draw(batch, Resources.tr(resourcesKey), this.rect.getX(), this.rect.getY() + this.rect.getH() - 5);
	}
	
	public Rect getRect() {
		return this.rect;
	}
}
