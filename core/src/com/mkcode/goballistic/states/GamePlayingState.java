package com.mkcode.goballistic.states;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.fonts.FontManager;
import com.mkcode.goballistic.gameobjects.Bullet;
import com.mkcode.goballistic.gameobjects.GroundElement;
import com.mkcode.goballistic.gameobjects.Target;
import com.mkcode.goballistic.gameobjects.TargetSize;
import com.mkcode.goballistic.gameobjects.Turret;
import com.mkcode.goballistic.ground.Ground;
import com.mkcode.goballistic.ground.GroundGenerator;
import com.mkcode.goballistic.inputfields.InputType;
import com.mkcode.goballistic.inputfields.NumberInput;
import com.mkcode.goballistic.math.Line;
import com.mkcode.goballistic.math.MToPx;
import com.mkcode.goballistic.math.Vector2;
import com.mkcode.goballistic.resources.Resources;
import com.mkcode.goballistic.score.Score;
import com.mkcode.goballistic.ui.FireButton;
import com.mkcode.goballistic.ui.ControlsBackground;
import com.mkcode.goballistic.ui.ExitButton;
import com.mkcode.goballistic.util.Constants;

public class GamePlayingState extends AbstractState {

	private Ground ground;
	private Turret turret;
	private Bullet bullet = null;
	private List<Target> targetContainer = null;
	
	private ControlsBackground controlsBackground;
	private NumberInput angleInput, forceInput;
	private BitmapFont labelFont, scoreFont;
	private FireButton fireButton;
	private ExitButton exitButton;
	
	private Score score;
	
	public static boolean 
			ANGLE_INPUT_SHOWN = false,
			FORCE_INPUT_SHOWN = false,
			FIRING = false;
	
	public GamePlayingState(StateManager stateManager, FontManager fontManager) {
		super(stateManager, fontManager);
		turret = new Turret();
		controlsBackground = new ControlsBackground(0, 0);
		angleInput = new NumberInput(
				turret,
				fontManager.getFont("inputFont"), 
				Constants.NUMBER_INPUT_OFFSET_X, 
				55, 
				Resources.tr("angle"),
				InputType.ANGLE
		);
		forceInput = new NumberInput(
				turret,
				fontManager.getFont("inputFont"), 
				Constants.NUMBER_INPUT_OFFSET_X, 
				5, 
				Resources.tr("force"),
				InputType.FORCE
		);
		labelFont = fontManager.getFont("labelFont");
		scoreFont = fontManager.getFont("scoreFont");
		fireButton = new FireButton();
		exitButton = new ExitButton(new Vector2(640 - 50 - 10, 10));
	}
	
	@Override
	public void init() {
		ground = GroundGenerator.generateGround();
		turret.init();
		generateTargets();
		score = new Score();
	}
	
	@Override
	public void update(float deltaTime) {
		if(bullet != null) {
			if(bullet.getLocation().getX() > Constants.WND_ELE_WIDTH 
					|| bullet.getLocation().getY() <= Constants.CONTROLS_AREA_HEIGHT) {
				System.out.println("Location: " + bullet.getLocation());
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
		
		if(Gdx.input.isTouched()) {
			
			float yCoord = Constants.WND_HEIGHT - Gdx.input.getY();
			
			if(angleInput.getRect().containsPoint(Gdx.input.getX(), yCoord) && !ANGLE_INPUT_SHOWN) {
				ANGLE_INPUT_SHOWN = true;
				angleInput.getInput();
			}
			
			else if(forceInput.getRect().containsPoint(Gdx.input.getX(), yCoord) && !FORCE_INPUT_SHOWN) {
				FORCE_INPUT_SHOWN = true;
				forceInput.getInput();
			}
			
			if(fireButton.getCircle().containsPoint(Gdx.input.getX(), Gdx.input.getY()) && !FIRING) {
				FIRING = true;
				score.incrementShots(); // update score
				float[] barrelEndCoords = sineBulletLocation(this.angleInput.getValue());
				bullet = new Bullet(
						barrelEndCoords[0] + Constants.TURRET_OFFSET + Constants.TURRET_WIDTH / 2, 
						barrelEndCoords[1] + GroundGenerator.getTurretLevel(),
						angleInput.getValue(), 
						forceInput.getValue(),
						Constants.BULLET_WEIGHT
				);
			}
			
			if(exitButton.getRect().containsPoint(Gdx.input.getX(), yCoord)) {
				cleanUpOnExit();
				stateManager.changeState("mainMenu");
			}
		}
		
		this.score.updateTime();
	}
	
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		ground.render(batch);
		turret.render(batch);
		if(bullet != null)
			bullet.render(batch);
		for(Target target : targetContainer)
			target.render(batch);
		
		scoreFont.draw(batch, Resources.tr("shots"), Constants.SCORE_LABEL_OFFSET_X, Constants.SCORE_SHOTS_OFFSET_Y);
		scoreFont.draw(batch, Resources.tr("time"), Constants.SCORE_LABEL_OFFSET_X, Constants.SCORE_TIME_OFFSET_Y);
		scoreFont.draw(batch, Integer.toString(score.getShots()), Constants.SCORE_VALUE_OFFSET_X, Constants.SCORE_SHOTS_OFFSET_Y);
		scoreFont.draw(
				batch, 
				formatTime(score.getTime()), 
				Constants.SCORE_VALUE_OFFSET_X, 
				Constants.SCORE_TIME_OFFSET_Y
		);
		
		controlsBackground.render(batch);
		angleInput.render(batch);
		forceInput.render(batch);
		labelFont.draw(batch, Resources.tr("angle"), Constants.LABEL_OFFSET_X, 85);
		labelFont.draw(batch, Resources.tr("force"), Constants.LABEL_OFFSET_X, 35);
		fireButton.render(batch);
		exitButton.render(batch);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		ground.dispose();
		turret.dispose();
		if(bullet != null)
			bullet.dispose();
		for(Target target : targetContainer)
			target.dispose();
		controlsBackground.dispose();
		angleInput.dispose();
		forceInput.dispose();
		fireButton.dispose();
	}
	
	private void cleanUpOnExit() {
		ground.dispose();
		if(bullet != null)
			bullet.dispose();
		for(Target target : targetContainer)
			target.dispose();
	}
	
	private void generateTargets() {
		if(targetContainer != null)
			for(Target target : targetContainer)
				target.dispose();
		targetContainer = new ArrayList<Target>();
		Random random = new Random(System.currentTimeMillis());
		for(int i = 0; i < Constants.SMALL_TARGET_AMOUNT; i++)
			targetContainer.add(generateTarget(TargetSize.SMALL, random));
		for(int i = 0; i < Constants.LARGE_TARGET_AMOUNT; i++)
			targetContainer.add(generateTarget(TargetSize.LARGE, random));
	}
	
	private Target generateTarget(TargetSize size, Random random) {
		float x = random.nextFloat() * Constants.WND_ELE_WIDTH / 2;
		x += (Constants.WND_ELE_WIDTH / 2);
		x -= (size == TargetSize.SMALL ? Constants.SMALL_TARGET_DIM : Constants.LARGE_TARGET_DIM);
		float y = random.nextFloat() * (Constants.WND_ELE_HEIGHT 
				- Constants.MAX_GROUND_HEIGHT 
				- Constants.CONTROLS_AREA_HEIGHT
				- (size == TargetSize.SMALL ? Constants.SMALL_TARGET_DIM : Constants.LARGE_TARGET_DIM));
		y += (Constants.MAX_GROUND_HEIGHT + Constants.CONTROLS_AREA_HEIGHT);
		return new Target(x, y, size);
	}
	
	private float[] sineBulletLocation(float angle) {
		
		double x, y;
		
		// law of sines
		y = Math.sin(Math.toRadians(angle)) * (3 / Math.sin(Math.toRadians(90)));
		
		// Pythagorean theorem
		double xPow2 = (Math.pow(3, 2) - Math.pow(y, 2));
		x = Math.sqrt(xPow2);
		
		return new float[] { (float)x, (float)y };
	}
	
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
				GroundElement groundElement = new GroundElement(x, y);
				if(bullet.getCircle().intersectsRectLines(groundElement.getRect())) {
					groundElementMatrix[yCoord][x] = 0;
					bullet.dispose();
					bullet = null;
				}
			}
		}
	}
	
	private void checkBulletTargetCollision() {
		for(int i = targetContainer.size() - 1; i >= 0; i--) {
			Target target = targetContainer.get(i);
			if(bullet != null && bullet.getCircle().intersectsRect(target.getRect())) {
				bullet.dispose();
				bullet = null;
				targetContainer.remove(i);
				target.dispose();
				target = null;
			}
		}
	}
	
	private String formatTime(float time) {
		int minutes = (int)Math.floor(time / 60f);
		float secondsRemaining = Math.round((time % 60f) * 100f) / 100f;
		return (minutes + ":" + secondsRemaining);
	}
}
