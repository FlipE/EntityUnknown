package co.uk.cbaker.eu.game.entity.systems;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import co.uk.cbaker.eu.game.entity.EntityManager;
import co.uk.cbaker.eu.game.entity.components.Controllable;
import co.uk.cbaker.eu.game.entity.components.Physics2D;
import co.uk.cbaker.eu.game.entity.components.WeaponManager;

public class PlayerControllerSystem extends AbstractSystem implements InputProcessor {
	
	// create these here to avoid garbage collection of pointers;
	private Vector2 direction;
	private Physics2D physics;
	private WeaponManager weapon;
	private Controllable controllable;
	private boolean isStraffing;
	private boolean isShooting;
	private boolean nextWeapon;
	private boolean previousWeapon;
	private float switchWeaponTimer;
	private final float switchWeaponDelay = 0.5f;
	
	public PlayerControllerSystem(EntityManager entityManager) {
		super(entityManager);
		direction = new Vector2();
	}	
	
	@Override
	public void process() {
		super.entities = em.getAllEntitiesPossessingComponent(Controllable.class);
		super.process();
	}
	
	@Override
	public void process(int entity) {
		// get the components from the entity
		controllable = em.getComponent(entity, Controllable.class);
		physics = em.getComponent(entity, Physics2D.class);
		weapon = em.getComponent(entity, WeaponManager.class);
		
		// update the physics and weapon components if entity is control focus
		if(controllable.isControlFocus()) {
			
			if(physics != null) {
				// movement direction
				physics.setDirection(direction);
				
				// turn to face if not straffing
				if (!(direction.x == 0 && direction.y == 0) && !isStraffing) {
					physics.setTargetAngle(direction);
				}
			}
			
			// weapon component update
			if(weapon != null) {
				weapon.tryShooting(isShooting);
				
				// update switchWeaponTimer
				switchWeaponTimer -= Gdx.graphics.getDeltaTime();
				
				// try to change to next weapon
				if(nextWeapon && switchWeaponTimer <= 0) {
					weapon.nextWeapon();
					switchWeaponTimer = switchWeaponDelay;
				}
				
				// try to change to previous weapon
				if(previousWeapon && switchWeaponTimer <= 0) {
					weapon.previousWeapon();
					switchWeaponTimer = switchWeaponDelay;
				}
				
			}
		}
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.W:
			direction.y += 1;
			break;
		case Keys.S:
			direction.y -= 1;
			break;
		case Keys.A:
			direction.x -= 1;
			break;
		case Keys.D:
			direction.x += 1;
			break;
		case Keys.SHIFT_RIGHT:
			isStraffing = true;			
			break;
		case Keys.SHIFT_LEFT:
			isStraffing = true;
			break;
		case Keys.CONTROL_RIGHT:
			isShooting = true;
			break;
		case Keys.E:
			nextWeapon = true;
			break;
		case Keys.Q:
			previousWeapon = true;
			break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.W:
			direction.y -= 1;
			break;
		case Keys.S:
			direction.y += 1;
			break;
		case Keys.A:
			direction.x += 1;
			break;
		case Keys.D:
			direction.x -= 1;
			break;
		case Keys.SHIFT_RIGHT:
			isStraffing = false;			
			break;
		case Keys.SHIFT_LEFT:
			isStraffing = false;
			break;
		case Keys.CONTROL_RIGHT:
			isShooting = false;
			break;
		case Keys.E:
			nextWeapon = false;
			// reset timer so it doesn't feel laggy and can switch weapons quick by spamming key
			this.switchWeaponTimer = 0;
			break;
		case Keys.Q:
			previousWeapon = false;
			// reset timer so it doesn't feel laggy and can switch weapons quick by spamming key
			this.switchWeaponTimer = 0;
			break;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		return false;
	}

	// @Override
	public boolean touchMoved(int x, int y) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#mouseMoved(int, int)
	 */
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}
}