package com.mkcode.goballistic.states;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mkcode.goballistic.animation.Animation;
import com.mkcode.goballistic.animation.ExplodeAnimation;
import com.mkcode.goballistic.fonts.FontManager;
import com.mkcode.goballistic.gameobjects.Bullet;
import com.mkcode.goballistic.gameobjects.GroundElement;
import com.mkcode.goballistic.gameobjects.Target;
import com.mkcode.goballistic.gameobjects.TargetSize;
import com.mkcode.goballistic.gameobjects.Turret;
import com.mkcode.goballistic.ground.Ground;
import com.mkcode.goballistic.ground.GroundGenerator;
import com.mkcode.goballistic.input.InputType;
import com.mkcode.goballistic.input.NumberInput;
import com.mkcode.goballistic.math.Circle;
import com.mkcode.goballistic.math.Line;
import com.mkcode.goballistic.math.MToPx;
import com.mkcode.goballistic.math.Rect;
import com.mkcode.goballistic.math.Vector2;
import com.mkcode.goballistic.particles.ParticleManager;
import com.mkcode.goballistic.resources.Resources;
import com.mkcode.goballistic.score.Score;
import com.mkcode.goballistic.ui.FireButton;
import com.mkcode.goballistic.ui.ArrowButton;
import com.mkcode.goballistic.ui.ControlsBackground;
import com.mkcode.goballistic.ui.ExitButton;
import com.mkcode.goballistic.util.Constants;
import com.mkcode.goballistic.util.Random;
import com.mkcode.mousefix.Mouse;

public class GamePlayingState extends AbstractState {

	private Ground ground;
	private GroundElement groundElementFlyweight;
	private Turret turret;
	private Bullet bullet = null;
	private List<Target> targetContainer = null;
	private List<Animation> animationContainer = null;
	private ParticleManager particleManager;
	private Sound explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));
	
	private ControlsBackground controlsBackground;
	private NumberInput angleInput, forceInput;
	private BitmapFont labelFont, scoreFont;
	private FireButton fireButton;
	private ArrowButton increaseAngleButton, decreaseAngleButton,
		increaseForceButton, decreaseForceButton;
	
	private Score score;
	
	private ShapeRenderer shapeRenderer;
	
	public static boolean FIRING = false;
	
	public GamePlayingState(StateManager stateManager, FontManager fontManager) {
		super(stateManager, fontManager);
		displayExitButton = true;
		groundElementFlyweight = new GroundElement();
		turret = new Turret();
		animationContainer = new ArrayList<Animation>();
		particleManager = new ParticleManager();
		
		controlsBackground = new ControlsBackground(0, 0);
		angleInput = new NumberInput(
				turret,
				fontManager.getFont("font30"), 
				Constants.NUMBER_INPUT_OFFSET_X, 
				55, 
				Resources.tr("angle"),
				InputType.ANGLE
		);
		increaseAngleButton = new ArrowButton(new Vector2(Constants.ARROW_UP_OFFSET_X, 62), true);
		decreaseAngleButton = new ArrowButton(new Vector2(Constants.ARROW_DOWN_OFFSET_X, 62), false);
		forceInput = new NumberInput(
				turret,
				fontManager.getFont("font30"), 
				Constants.NUMBER_INPUT_OFFSET_X, 
				5, 
				Resources.tr("force"),
				InputType.FORCE
		);
		increaseForceButton = new ArrowButton(new Vector2(Constants.ARROW_UP_OFFSET_X, 12), true);
		decreaseForceButton = new ArrowButton(new Vector2(Constants.ARROW_DOWN_OFFSET_X, 12), false);
		labelFont = fontManager.getFont("labelFont");
		scoreFont = fontManager.getFont("scoreFont");
		fireButton = new FireButton();
		Gdx.gl.glLineWidth(1);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setColor(Color.RED);
	}
	
	@Override
	public void init() {
		ground = GroundGenerator.generateGround();
		turret.init();
		generateTargets();
		// reset inputs
		angleInput.setValue(Constants.INPUT_ANGLE_DEFAULT_VALUE);
		forceInput.setValue(Constants.INPUT_FORCE_DEFAULT_VALUE);
		score = new Score();
	}
	
	@Override
	public void update(float deltaTime) {
		
		super.update(deltaTime);
		
		if(this.targetContainer.size() == 0)
			changeState("scoreboard");
		
		if(FIRING)
			System.out.println("pass");
		
		if(bullet != null) {
			if(bullet.getLocation().getX() > Constants.WND_ELE_WIDTH 
					|| bullet.getLocation().getY() <= Constants.CONTROLS_AREA_HEIGHT) {
				bullet.dispose();
				bullet = null;
				FIRING = false;
			}
			else
				bullet.update(deltaTime);
			
			checkBulletCollision();
		}
		
		for(Target target : this.targetContainer) {
			if(target.getBottomLeft().getY() < 0) {
				target.dispose();
				target = null;
			}
			if(target != null)
				target.update(deltaTime, ground);
		}
		
		for(int i = animationContainer.size() - 1; i >= 0; i--) {
			Animation animation = animationContainer.get(i);
			if(animation.hasEnded()) {
				animation.dispose();
				animationContainer.remove(i);
			}
			else
				animation.update();
		}
		
		particleManager.update(deltaTime);
		
		if(Mouse.clicked()) {
			
			float yCoord = Constants.WND_HEIGHT - Gdx.input.getY();
			
			if(angleInput.getRect().containsPoint(Gdx.input.getX(), yCoord)) {
				angleInput.getInput();
			}
			
			else if(forceInput.getRect().containsPoint(Gdx.input.getX(), yCoord)) {
				forceInput.getInput();
			}
			
			if(fireButton.getCircle().containsPoint(Gdx.input.getX(), Gdx.input.getY()) && !FIRING) {
				FIRING = true;
				score.incrementShots(); // update score
//				Vector2 barrelEndCoords = sineBulletLocation(this.angleInput.getValue());
				Vector2 barrelEndCoords = Vector2.polar(this.angleInput.getValue(), 3);
				bullet = new Bullet(
						barrelEndCoords.getX() + Constants.TURRET_OFFSET + Constants.TURRET_WIDTH / 2, 
						barrelEndCoords.getY() + GroundGenerator.getTurretLevel() + Constants.BULLET_DIM,
						angleInput.getValue(), 
						forceInput.getValue(),
						Constants.BULLET_WEIGHT
				);
			}
			
			if(increaseAngleButton.getRect().containsPoint(Gdx.input.getX(), yCoord))
				angleInput.increaseValue(Constants.ANGLE_BUTTON_DELTA);
			
			if(decreaseAngleButton.getRect().containsPoint(Gdx.input.getX(), yCoord))
				angleInput.decreaseValue(Constants.ANGLE_BUTTON_DELTA);
			
			if(increaseForceButton.getRect().containsPoint(Gdx.input.getX(), yCoord))
				forceInput.increaseValue(Constants.FORCE_BUTTON_DELTA);
			
			if(decreaseForceButton.getRect().containsPoint(Gdx.input.getX(), yCoord))
				forceInput.decreaseValue(Constants.FORCE_BUTTON_DELTA);
		}
		
		this.score.updateTime();
	}
	
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		ground.render(batch);
		turret.render(batch);
		if(bullet != null) {
			bullet.render(batch);
		}
		for(Target target : targetContainer)
			target.render(batch);
		for(Animation animation : animationContainer)
			animation.render(batch);
		particleManager.render(batch);
		
		scoreFont.draw(batch, Resources.tr("shots"), Constants.SCORE_LABEL_OFFSET_X, Constants.SCORE_SHOTS_OFFSET_Y);
		scoreFont.draw(batch, Resources.tr("time"), Constants.SCORE_LABEL_OFFSET_X, Constants.SCORE_TIME_OFFSET_Y);
		scoreFont.draw(batch, Integer.toString(score.getShots()), Constants.SCORE_VALUE_OFFSET_X, Constants.SCORE_SHOTS_OFFSET_Y);
		scoreFont.draw(
				batch, 
				score.getFormattedTime(), 
				Constants.SCORE_VALUE_OFFSET_X, 
				Constants.SCORE_TIME_OFFSET_Y
		);
		
		controlsBackground.render(batch);
		angleInput.render(batch);
		increaseAngleButton.render(batch);
		decreaseAngleButton.render(batch);
		forceInput.render(batch);
		increaseForceButton.render(batch);
		decreaseForceButton.render(batch);
		labelFont.draw(batch, Resources.tr("angle"), Constants.LABEL_OFFSET_X, 85);
		labelFont.draw(batch, Resources.tr("force"), Constants.LABEL_OFFSET_X, 35);
		fireButton.render(batch);
		exitButton.render(batch);
		
//		fontManager.getFont("font10").draw(batch, "X: " + Gdx.input.getX(), 50, 480 - 50);
//		fontManager.getFont("font10").draw(batch, "Y: " + Gdx.input.getY(), 50, 480 - 70);
		
		//if(bullet != null)
			//drawDebugLines();
		//drawDebugRect();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		if(ground != null)
			ground.dispose();
		groundElementFlyweight.dispose();
		turret.dispose();
		if(bullet != null)
			bullet.dispose();
		for(Target target : targetContainer)
			target.dispose();
		for(Animation animation : animationContainer)
			animation.dispose();
		particleManager.dispose();
		controlsBackground.dispose();
		angleInput.dispose();
		increaseAngleButton.dispose();
		decreaseAngleButton.dispose();
		forceInput.dispose();
		increaseForceButton.dispose();
		decreaseForceButton.dispose();
		fireButton.dispose();
		exitButton.dispose();
	}
	
	private void cleanUpOnExit() {
		ground.dispose();
		if(bullet != null)
			bullet.dispose();
		for(Target target : targetContainer)
			target.dispose();
		for(int i = animationContainer.size() - 1; i >= 0; i--) {
			animationContainer.get(i).dispose();
			animationContainer.remove(i);
		}
		particleManager.dispose();
	}
	
	private void changeState(String stateName) {
		cleanUpOnExit();
		stateManager.changeState(stateName);
	}
	
	private void generateTargets() {
		if(targetContainer != null)
			for(Target target : targetContainer)
				target.dispose();
		targetContainer = new ArrayList<Target>();
		for(int i = 0; i < Constants.SMALL_TARGET_AMOUNT; i++)
			targetContainer.add(generateTarget(TargetSize.SMALL));
		for(int i = 0; i < Constants.LARGE_TARGET_AMOUNT; i++)
			targetContainer.add(generateTarget(TargetSize.LARGE));
	}
	
	private Target generateTarget(TargetSize size) {
		float x = Random.getRandom().nextFloat() * Constants.WND_ELE_WIDTH / 2;
		x += (Constants.WND_ELE_WIDTH / 2);
		x -= (size == TargetSize.SMALL ? Constants.SMALL_TARGET_DIM : Constants.LARGE_TARGET_DIM);
		float y = Random.getRandom().nextFloat() * (Constants.WND_ELE_HEIGHT 
				- Constants.MAX_GROUND_HEIGHT 
				- Constants.CONTROLS_AREA_HEIGHT
				- (size == TargetSize.SMALL ? Constants.SMALL_TARGET_DIM : Constants.LARGE_TARGET_DIM));
		y += (Constants.MAX_GROUND_HEIGHT + Constants.CONTROLS_AREA_HEIGHT);
		return new Target(x, y, size);
	}
	
//	private Vector2 sineBulletLocation(float angle) {
//		
//		double x, y;
//		
//		// law of sines
//		y = Math.sin(Math.toRadians(angle)) * (4 / Math.sin(Math.toRadians(90)));
//		
//		// Pythagorean theorem
//		double xPow2 = (Math.pow(4, 2) - Math.pow(y, 2));
//		x = Math.sqrt(xPow2);
//		
//		return new Vector2((float)x, (float)y);
//	}
	
	private void checkBulletCollision() {
		checkBulletGroundCollision();
		checkBulletTargetCollision();
	}
	
	private void checkBulletGroundCollision() {
		int top, right, bottom, left;
		if(bullet != null) {
			Vector2 bulletLocation = bullet.getLocation();
			Vector2 newBulletLocation = new Vector2(
					bulletLocation.getX(), 
					bulletLocation.getY()
			);
			// calculate bullet bounds
			top = (int)Math.floor(newBulletLocation.getY() + 0.5) - (Constants.CONTROLS_AREA_HEIGHT - 1);
			right = (int)Math.floor(newBulletLocation.getX() + 0.5);
			bottom = (int)Math.floor(newBulletLocation.getY() - 0.5) - (Constants.CONTROLS_AREA_HEIGHT - 1);
			left = (int)Math.floor(newBulletLocation.getX() - 0.5);
			// check "squares" the bullet is in
			checkBulletGroundElementCollision(left, top);
			checkBulletGroundElementCollision(right, top);
			checkBulletGroundElementCollision(right, bottom);
			checkBulletGroundElementCollision(left, bottom);
		}
	}
	
	private void checkBulletGroundElementCollision(int x, int y) {
		Integer[][] groundElementMatrix = ground.getGroundElementMatrix();
		if(bullet != null && y >= 0 && y < Constants.MAX_GROUND_HEIGHT && x >= 0 && x < Constants.GROUND_WIDTH) {
			int yCoord = Constants.MAX_GROUND_HEIGHT - 1 - y;
			if(groundElementMatrix[yCoord][x] == 1) { // ground element found in this location
				groundElementFlyweight.setBottomLeft(new Vector2(x, y));
				Line line = bullet.getLineFromPreviousLocation();
				Rect groundEleRect = groundElementFlyweight.getRect();
				boolean intersectsRect = line.intersectsRect(groundEleRect); 
				if(intersectsRect
						|| bullet.getCircle().intersectsRectLines(groundEleRect)) {
					groundElementMatrix[yCoord][x] = 0;
					explosionSound.play();
					particleManager.createParticles(
							bullet.getCircle().getX(), 
							bullet.getCircle().getY()
					);
					bullet.dispose();
					bullet = null;
				}
			}
		}
	}
	
	private void checkBulletTargetCollision() {
		for(int i = targetContainer.size() - 1; i >= 0; i--) {
			Target target = targetContainer.get(i);
			Line line = null;
			Rect targetRect = null;
			if(bullet != null) {
				line = bullet.getLineFromPreviousLocation();
				targetRect = target.getRect();
			}
			Circle bulletCircle = null;
			if(bullet != null)
				bulletCircle = bullet.getCircle();
			if(bullet != null 
					&& (line.intersectsRect(targetRect) 
							|| bulletCircle.intersectsRect(target.getRect()))) {
				explosionSound.play();
				animationContainer.add(new ExplodeAnimation(bullet.getCircle().getX(), bullet.getCircle().getY()));
				bullet.dispose();
				bullet = null;
				targetContainer.remove(i);
				target.dispose();
				target = null;
			}
		}
	}

	private void drawDebugLines() {
		Line line = bullet.getLineFromPreviousLocation();
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.line(
				new com.badlogic.gdx.math.Vector2(
						MToPx.mToPx(line.getX1()), 
						MToPx.mToPx(line.getY1())), 
				new com.badlogic.gdx.math.Vector2(
						MToPx.mToPx(line.getX2()), 
						MToPx.mToPx(line.getY2()))
		);
		shapeRenderer.end();
	}
	
	private void drawDebugRect() {
		if(targetContainer.size() > 0 && targetContainer.get(0) != null) {
			Line[] bounds = targetContainer.get(0).getRect().getBounds();
			for(Line line : bounds) {
				shapeRenderer.begin(ShapeType.Line);
				shapeRenderer.line(
						new com.badlogic.gdx.math.Vector2(
								MToPx.mToPx(line.getX1()), 
								MToPx.mToPx(line.getY1())), 
						new com.badlogic.gdx.math.Vector2(
								MToPx.mToPx(line.getX2()), 
								MToPx.mToPx(line.getY2()))
				);
				shapeRenderer.end();
			}
		}
	}
	
	public Score getScore() {
		return score;
	}
	
	
}
