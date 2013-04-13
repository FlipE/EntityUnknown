package co.uk.cbaker.eu.game.ai.soldier;

import co.uk.cbaker.eu.game.entity.EntityManager;
import co.uk.cbaker.eu.game.entity.components.AIBrain;
import co.uk.cbaker.eu.game.entity.components.Physics2D;
import co.uk.cbaker.eu.game.model.Floor;
import co.uk.cbaker.eu.game.pathfinding.Path;
import co.uk.cbaker.eu.game.state.State;
import co.uk.cbaker.eu.game.state.StateMachine;

import com.badlogic.gdx.math.Vector2;

/**
 * Singleton AIState implementation
 * 
 * @author Chris B
 */
public class GetPathState implements State {

	/** The AIState instance */
	private static State instance;

	/** Private constructor can't be called from outside the class */
	private GetPathState() {
	}

	/**
	 * Returns the singleton AIState instance
	 * 
	 * @return the singleton AIState instance
	 */
	public static State getInsatnce() {
		if (instance == null) {
			instance = new GetPathState();
		}
		return instance;
	}

	@Override
	public void Enter(EntityManager em, int entity, StateMachine machine) {
		// do something when state is entered
		System.out.println("GetPath.");
	}

	@Override
	public void Execute(EntityManager em, int entity, StateMachine machine) {
		Physics2D physics = em.getComponent(entity, Physics2D.class);
		AIBrain brain = em.getComponent(entity, AIBrain.class);
		
		Vector2 position = physics.getBody().getPosition();
		Vector2 target = brain.getEnemyPosition();
		
		// try to get a path to player
		Path path = Floor.getInstance().findPath((int) position.x, (int) position.y, (int) target.x, (int) target.y);
		if (path == null) {
			// player is out of sight and sound so move to the last known location
			// or possibly reset to starting location?
			machine.changeState(IdleState.getInsatnce());
		}
		else {
			// set the path in the brain and change state to follow the path
			brain.setPath(path);
			machine.changeState(PathToTargetState.getInsatnce());
		}
	}

	@Override
	public void Exit(EntityManager em, int entity, StateMachine machine) {
		// do something when state is exited
	}

}
