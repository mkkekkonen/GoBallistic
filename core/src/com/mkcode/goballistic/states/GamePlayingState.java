package com.mkcode.goballistic.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkcode.goballistic.gameobjects.Bullet;
import com.mkcode.goballistic.gameobjects.GroundElement;
import com.mkcode.goballistic.gameobjects.Target;
import com.mkcode.goballistic.gameobjects.TargetSize;
import com.mkcode.goballistic.gameobjects.Turret;
import com.mkcode.goballistic.ground.Ground;
import com.mkcode.goballistic.ground.GroundGenerator;
import com.mkcode.goballistic.inputfields.InputType;
import com.mkcode.goballistic.inputfields.NumberInput;
import com.mkcode.goballistic.managers.FontManager;
import com.mkcode.goballistic.math.Line;
import com.mkcode.goballistic.math.MToPx;
import com.mkcode.goballistic.math.Vector2;
import com.mkcode.goballistic.resources.Resources;
import com.mkcode.goballistic.ui.Button;
import com.mkcode.goballistic.ui.ControlsBackground;
import com.mkcode.goballistic.util.Constants;

public class GamePlayingState extends AbstractState {

	private Ground ground;
	private Turret turret;
	private Bullet bullet = null;
	private ControlsBackground controlsBackground;
	private NumberInput angleInput, forceInput;
	private BitmapFont labelFont;
	private Button button;
	private Target debugSmallTarget, debugLargeTarget;
	
	private int TOP = 0, RIGHT = 0, LEFT = 0, BOTTOM = 0;
	
	public static boolean 
			ANGLE_INPUT_SHOWN = false,
			FORCE_INPUT_SHOWN = false,
			FIRING = false;
	
	public GamePlayingState(FontManager fontManager) {
		super(fontManager);
		this.ground = GroundGenerator.generateGround();
		this.turret = new Turret();
		this.debugSmallTarget = new Target(30, 30, TargetSize.SMALL);
		this.debugLargeTarget = new Target(40, 40, TargetSize.LARGE);
		this.controlsBackground = new ControlsBackground(0, 0);
		this.angleInput = new NumberInput(
				this.turret,
				fontManager.getFont("inputFont"), 
				Constants.NUMBER_INPUT_OFFSET_X, 
				55, 
				Resources.tr("angle"),
				InputType.ANGLE
		);
		this.forceInput = new NumberInput(
				this.turret,
				fontManager.getFont("inputFont"), 
				Constants.NUMBER_INPUT_OFFSET_X, 
				5, 
				Resources.tr("force"),
				InputType.FORCE
		);
		this.labelFont = fontManager.getFont("labelFont");
		this.button = new Button();
	}
	
	@Override
	public void update(float deltaTime) {
		if(this.bullet != null) {
			if(bullet.getLocation().getX() > Constants.WND_WIDTH 
					|| bullet.getLocation().getY() <= MToPx.mToPx(Constants.CONTROLS_AREA_HEIGHT)) {
				System.out.println("Location: " + bullet.getLocation());
				this.bullet.dispose();
				this.bullet = null;
				FIRING = false;
			}
			else
				this.bullet.update(deltaTime);
			
			this.checkBulletCollision();
		}
		
		float yCoord = Constants.WND_HEIGHT - Gdx.input.getY();
		if(Gdx.input.isTouched()) {
			if(this.angleInput.getRect().containsPoint(Gdx.input.getX(), yCoord) && !ANGLE_INPUT_SHOWN) {
				ANGLE_INPUT_SHOWN = true;
				this.angleInput.getInput();
			}
			else if(this.forceInput.getRect().containsPoint(Gdx.input.getX(), yCoord) && !FORCE_INPUT_SHOWN) {
				FORCE_INPUT_SHOWN = true;
				this.forceInput.getInput();
			}
			if(this.button.getCircle().containsPoint(Gdx.input.getX(), Gdx.input.getY()) && !FIRING) {
				FIRING = true;
				float[] barrelEndCoords = this.sineBulletLocation(this.angleInput.getValue());
				this.bullet = new Bullet(
						barrelEndCoords[0] + MToPx.mToPx(Constants.TURRET_OFFSET + Constants.TURRET_WIDTH / 2), 
						barrelEndCoords[1] + MToPx.mToPx(GroundGenerator.getTurretLevel()),
						this.angleInput.getValue(), 
						this.forceInput.getValue(),
						Constants.BULLET_WEIGHT
				);
			}
		}
	}
	
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		this.ground.render(batch);
		this.turret.render(batch);
		if(this.bullet != null)
			this.bullet.render(batch);
		this.debugSmallTarget.render(batch);
		this.debugLargeTarget.render(batch);
		this.controlsBackground.render(batch);
		this.angleInput.render(batch);
		this.forceInput.render(batch);
		this.labelFont.draw(batch, Resources.tr("angle"), Constants.LABEL_OFFSET_X, 85);
		this.labelFont.draw(batch, Resources.tr("force"), Constants.LABEL_OFFSET_X, 35);
		this.labelFont.draw(batch, "Top: " + TOP, 50, 450);
		this.labelFont.draw(batch, "Left: " + LEFT, 50, 400);
		this.button.render(batch);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		this.ground.dispose();
		this.turret.dispose();
		if(this.bullet != null)
			this.bullet.dispose();
		this.controlsBackground.dispose();
		this.angleInput.dispose();
		this.forceInput.dispose();
		this.button.dispose();
	}
	
	private float[] sineBulletLocation(float angle) {
		
		double x, y;
		
		// law of sines
		y = Math.sin(Math.toRadians(angle)) * (30 / Math.sin(Math.toRadians(90)));
		
		// Pythagorean theorem
		double xPow2 = (Math.pow(30, 2) - Math.pow(y, 2));
		x = Math.sqrt(xPow2);
		
		return new float[] { (float)x, (float)y };
	}
	
	private void checkBulletCollision() {
		this.checkBulletGroundCollision();
	}
	
	private void checkBulletGroundCollision() {
		int top, right, bottom, left;
		if(bullet != null) {
			Vector2 bulletLocation = this.bullet.getLocation();
			Vector2 newBulletLocation = new Vector2(
					bulletLocation.getX(), 
					bulletLocation.getY()
			);
			// calculate bullet bounds
			TOP = top = (int)Math.floor((newBulletLocation.getY() + 5) / 10) - 10;
			right = (int)Math.floor((newBulletLocation.getX() + 5) / 10);
			bottom = (int)Math.floor((newBulletLocation.getY() - 5) / 10) - 10;
			LEFT = left = (int)Math.floor((newBulletLocation.getX() - 5) / 10);
			// check "squares" the bullet is in
			checkBulletGroundElementCollision(left, top);
			checkBulletGroundElementCollision(right, top);
			checkBulletGroundElementCollision(right, bottom);
			checkBulletGroundElementCollision(left, bottom);
		}
	}
	
	private void checkBulletGroundElementCollision(int x, int y) {
		Integer[][] groundElementMatrix = this.ground.getGroundElementMatrix(); 
		System.out.println("X: " + x + " Y: " + y);
		if(bullet != null && y >= 0 && y < Constants.MAX_GROUND_HEIGHT && x >= 0 && x < Constants.GROUND_WIDTH) {
			int yCoord = Constants.MAX_GROUND_HEIGHT - 1 - y;
			System.out.println(this.bullet.getCircle());
			if(groundElementMatrix[yCoord][x] == 1) { // ground element found in this location
				GroundElement groundElement = new GroundElement(x, y);
				Line[] eleBounds = groundElement.getRect().getBounds();
				for(Line line : eleBounds) {
					System.out.print(line);
					if(this.bullet.getCircle().lineIntersects(line)) {
						System.out.println(" COLLISION");
						groundElementMatrix[yCoord][x] = 0;
						this.bullet.dispose();
						this.bullet = null;
						FIRING = false;
						break;
					}
					System.out.println(" no collision");
				}
			}
		}
	}
}
