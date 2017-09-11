package com.mkcode.goballistic.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.math.Rect;
import com.mkcode.goballistic.math.Vector2;
import com.mkcode.goballistic.resources.Resources;
import com.mkcode.goballistic.util.Constants;

public class TextButton extends AbstractRectUIElement {

	private BitmapFont menuFont;
	
	private String resourcesKey;
	
	public TextButton(Vector2 bottomLeft, BitmapFont menuFont, String resourcesKey) {
		super(bottomLeft, Constants.MAIN_MENU_BUTTON_W, Constants.MAIN_MENU_BUTTON_H, "menubg.png");
		this.menuFont = menuFont;
		this.resourcesKey = resourcesKey;
	}

	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		menuFont.draw(batch, Resources.tr(resourcesKey), rect.getX(), rect.getY() + rect.getH() - 5);
	}
}
