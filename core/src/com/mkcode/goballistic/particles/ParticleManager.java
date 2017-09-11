package com.mkcode.goballistic.particles;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.util.Random;

public class ParticleManager {

	private List<Particle> particleContainer;
	
	public ParticleManager() {
		particleContainer = new ArrayList<Particle>();
	}
	
	public void update(float deltaTime) {
		for(int i = particleContainer.size() - 1; i >= 0; i--) {
			Particle particle = particleContainer.get(i);
			if(particle.lifetimeElapsed()) {
				particle.dispose();
				particleContainer.remove(i);
			}
			else
				particle.update(deltaTime);
		}
	}
	
	public void render(SpriteBatch batch) {
		for(Particle particle : particleContainer)
			particle.render(batch);
	}
	
	public void dispose() {
		for(Particle particle : particleContainer)
			particle.dispose();
	}
	
	public void createParticles(float x, float y) {
		int particleCount = 10 + Random.getRandom().nextInt(10);
		for(int i = 0; i < particleCount; i++) {
			particleContainer.add(new Particle(
					x, 
					y, 
					Random.getRandom().nextFloat() * 360, 
					5 + Random.getRandom().nextInt(5), 
					2000 + Random.getRandom().nextInt(5000)
			));
		}
	}
}
