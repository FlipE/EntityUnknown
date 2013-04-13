package co.uk.cbaker.eu.game.ai.soldier;

import co.uk.cbaker.eu.game.entity.EntityManager;
import co.uk.cbaker.eu.game.entity.components.AIBrain;
import co.uk.cbaker.eu.game.entity.components.AIHearing;
import co.uk.cbaker.eu.game.entity.components.AIVision;
import co.uk.cbaker.eu.game.entity.components.Physics2D;
import co.uk.cbaker.eu.game.pathfinding.Path;
import co.uk.cbaker.eu.game.state.State;
import co.uk.cbaker.eu.game.state.StateMachine;

import com.badlogic.gdx.math.Vector2;

/**
 * Singleton AIState implementation
 * 
 * @author Chris B
 */
public class PathToTargetState implements State {

	/** The AIState instance */
	private static State instance;
	
	/** Private constructor can't be called from outside the class */
	private PathToTargetState() {}
	
	/**
	 * Returns the singleton AIState instance 
	 * @return the singleton AIState instance
	 */
	public static State getInsatnce() {
		if(instance == null) {
			instance = new PathToTargetState();
		}
		return instance;
	}
	
	@Override
	public void Enter(EntityManager em, int entity, StateMachine machine) {
		// do something when state is entered
		System.out.println("PathToTargetState");
	}

	@Override
	public void Execute(EntityManager em, int entity, StateMachine machine) {
		Physics2D physics = em.getComponent(entity, Physics2D.class);
		AIHearing hearing = em.getComponent(entity, AIHearing.class);
		AIBrain brain = em.getComponent(entity, AIBrain.class);
		AIVision sight = em.getComponent(entity, AIVision.class);
		
		// positions of this entity and the target
		Vector2 position = physics.getBody().getPosition();
		
		// if player is seen at any point switch to get in range
		if(sight.isTargetInSight()) {
			machine.changeState(GetInRangeState.getInsatnce());
		}
		
		// else move entity toward the current path location
		Path path = brain.getPath();
		float pathX = path.getWorldX();
		float pathY = path.getWorldY();
		
		// if entity is within 1 metre of the current path then move the path on
		if(position.y < pathY + 1 && position.y > pathY - 1
		&& position.x < pathX + 1 && position.x > pathX - 1) {
			path.nextStep();
			pathX = path.getWorldX();
			pathY = path.getWorldY();
		}
		
		System.out.println(pathX + ", " + pathY);
		
		// if path is complete and player is not anywhere nearby reset
		if(path.atEnd()) {
			machine.changeState(GetPathState.getInsatnce());
		}
		
		// reset direction each update
		physics.resetDirection();
		
		// the difference in vectors is the direction to move
		Vector2 direction = new Vector2(pathX - position.x, pathY - position.y);
		
		// move the entity
		physics.setDirection(direction);
		
		// set the angle to entity should turn towards
		physics.setTargetAngle(direction);
		
	}

	@Override
	public void Exit(EntityManager em, int entity, StateMachine machine) {
		// do something when state is exited
	}

}
