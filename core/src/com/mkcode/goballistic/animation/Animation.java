package com.mkcode.goballistic.animation;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.math.MToPx;
import com.mkcode.goballistic.math.Vector2;

public class Animation {

	private List<Texture> frames;
	private Vector2 centerPoint;
	
	private int frameIndex;
	private long frameTime, startTime;
	
	public Animation(String [] fileNames, long frameTime, float x, float y) {
		startTime = System.currentTimeMillis();
		this.frameTime = frameTime;
		frames = new ArrayList<Texture>();
		for(String fileName : fileNames)
			frames.add(new Texture(Gdx.files.internal(fileName)));
		centerPoint = new Vector2(x, y);
	}
	
	public void update() {
		frameIndex = (int)((System.currentTimeMillis() - startTime) / frameTime);
		if(frameIndex >= frames.size())
			frameIndex = -1;
	}
	
	public void render(SpriteBatch batch) {
		if(frameIndex > -1) {
			Texture texture = frames.get(frameIndex); 
			batch.draw(texture, MToPx.mToPx(centerPoint.getX()) - texture.getWidth() / 2, MToPx.mToPx(centerPoint.getY()) - texture.getHeight() / 2);
		}
	}
	
	public void dispose() {
		for(int i = 0; i < frames.size(); i++)
			frames.get(i).dispose();
	}
	
	public boolean hasEnded() {
		return System.currentTimeMillis() > startTime + frameTime * frames.size();
	}
}
