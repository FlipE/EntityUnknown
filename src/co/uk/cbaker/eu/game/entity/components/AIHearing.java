package co.uk.cbaker.eu.game.entity.components;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * The AIHearing component requires a Physics2D component attached to the entity 
 * in order for its position to be updated. The hearing is used as a long range 
 * circular sensor for the AIBrain. 
 * 
 * @author Chris B
 */
public class AIHearing implements Component {

	private Body body;
	private boolean targetDetected;
	
	public AIHearing(Body body) {
		super();
		this.body = body;
	}

	/**
	 * @return the body
	 */
	public Body getBody() {
		return body;
	}

	/**
	 * @return the enemyDetected
	 */
	public boolean isTargetDetected() {
		return targetDetected;
	}

	/**
	 * @param targetDetected the enemyDetected to set
	 */
	public void setTargetDetected(boolean targetDetected) {
		this.targetDetected = targetDetected;
	}
}