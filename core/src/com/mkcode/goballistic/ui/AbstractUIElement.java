package com.mkcode.goballistic.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.math.Vector2;

public abstract class AbstractUIElement {

	protected Texture texture;
	
	protected AbstractUIElement(String fileName) {
		this.texture = new Texture(Gdx.files.internal(fileName));
	}
	
	public void render(SpriteBatch batch) {
	}
	
	public void dispose() {
		this.texture.dispose();
	}
}
