package co.uk.cbaker.eu.game.entity.components;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Physics2D extends AbstractComponent<Physics2D> implements Component {

	private Body body;
	private float maxVelocity;
	private Vector2 direction;
	private float targetAngle;
	private boolean isMoving;
	private boolean isProjectile;

	public Physics2D(Body body, float maxVelocity, boolean isProjectile) {
		super();
		this.body = body;
		this.maxVelocity = maxVelocity;
		this.isProjectile = isProjectile;
		this.isMoving = isProjectile;
		this.direction = new Vector2();
	}
	
	/**
	 * @return the body
	 */
	public Body getBody() {
		return body;
	}

	public Vector2 getDirection() {
		return this.direction;
	}

	public void setDirection(float x, float y) {
		this.direction.x = x;
		this.direction.y = y;
	}
	
	public void setDirection(Vector2 direction) {
		this.direction.set(direction);
	}
	
	public float getMaxVeclocity() {
		return this.maxVelocity;
	}

	public boolean isProjectile() {
		return this.isProjectile;
	}

	/**
	 * @return the targetAngle
	 */
	public float getTargetAngle() {
		return targetAngle;
	}

	/**
	 * @param targetAngle the targetAngle to set
	 */
	public void setTargetAngle(float targetAngle) {
		this.targetAngle = targetAngle;
	}
	
	/**
	 * @param targetAngle the targetAngle to set
	 */
	public void setTargetAngle(Vector2 vector) {
		this.targetAngle = MathUtils.atan2(vector.y, vector.x);
	}

	/**
	 * @return the isMoving
	 */
	public boolean isMoving() {
		return isMoving;
	}

	/**
	 * @param isMoving the isMoving to set
	 */
	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public void resetDirection() {
		this.direction.x = 0;
		this.direction.y = 0;
	}
	
	public float getX() {
		return body.getPosition().x;
	}
	
	public float getY() {
		return body.getPosition().y;
	}
}