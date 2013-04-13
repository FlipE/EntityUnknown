package co.uk.cbaker.eu.game.entity.components;

import com.badlogic.gdx.math.Vector2;

import co.uk.cbaker.eu.game.pathfinding.Path;
import co.uk.cbaker.eu.game.state.StateMachine;

public class AIBrain implements Component {
	
	StateMachine machine;

	/** this is a pointer to the actual current position */
	private Vector2 enemyPosition;
	private Path path;
	
	/** this is the contact point of the detection */
	private Vector2 lastKnownEnemyPosition;
	
	public AIBrain(StateMachine machine) {
		super();
		this.machine = machine;
		this.lastKnownEnemyPosition = new Vector2();
	}

	/**
	 * @return the machine
	 */
	public StateMachine getMachine() {
		return machine;
	}

	/**
	 * @param machine the machine to set
	 */
	public void setMachine(StateMachine machine) {
		this.machine = machine;
	}
	
	public void setEnemyPosition(Vector2 position) {
		this.enemyPosition = position;
	}

	/**
	 * @return the enemyPosition
	 */
	public Vector2 getEnemyPosition() {
		return enemyPosition;
	}

	public void setLastKnownEnemyPosition(float x, float y) {
		this.lastKnownEnemyPosition.x = x;
		this.lastKnownEnemyPosition.y = y;
	}
	
	/**
	 * @return the lastKnownEnemyPosition
	 */
	public Vector2 getLastKnownEnemyPosition() {
		return lastKnownEnemyPosition;
	}

	public void updateLastKnownEnemyPosition() {
		this.lastKnownEnemyPosition.x = this.enemyPosition.x;
		this.lastKnownEnemyPosition.y = this.enemyPosition.y;
	}

	/**
	 * @return the path
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(Path path) {
		this.path = path;
	}
	
	
}