package co.uk.cbaker.eu.game.ai.soldier;

import co.uk.cbaker.eu.game.entity.EntityManager;
import co.uk.cbaker.eu.game.entity.components.AIBrain;
import co.uk.cbaker.eu.game.entity.components.AIHearing;
import co.uk.cbaker.eu.game.entity.components.AIVision;
import co.uk.cbaker.eu.game.entity.components.Physics2D;
import co.uk.cbaker.eu.game.state.State;
import co.uk.cbaker.eu.game.state.StateMachine;

import com.badlogic.gdx.math.Vector2;

/**
 * Singleton AIState implementation
 * 
 * @author Chris B
 */
public class GetInRangeState implements State {

	/** The AIState instance */
	private static State instance;
	
	/** Private constructor can't be called from outside the class */
	private GetInRangeState() {}
	
	/**
	 * Returns the singleton AIState instance 
	 * @return the singleton AIState instance
	 */
	public static State getInsatnce() {
		if(instance == null) {
			instance = new GetInRangeState();
		}
		return instance;
	}
	
	@Override
	public void Enter(EntityManager em, int entity, StateMachine machine) {
		// do something when state is entered
		System.out.println("Im gonna check it out!");
	}

	@Override
	public void Execute(EntityManager em, int entity, StateMachine machine) {
		Physics2D physics = em.getComponent(entity, Physics2D.class);
		AIHearing hearing = em.getComponent(entity, AIHearing.class);
		AIBrain brain = em.getComponent(entity, AIBrain.class);
		AIVision sight = em.getComponent(entity, AIVision.class);
				
		// reset direction each update
		physics.resetDirection();
		
		Vector2 position = physics.getBody().getPosition();
		Vector2 target = brain.getLastKnownEnemyPosition();
			
		// the difference in vectors is the direction
		Vector2 direction = new Vector2(target.x - position.x, target.y - position.y);
		
		// set the new direction
		if(!hearing.isTargetDetected() && sight.isTargetInSight()) {
			physics.setDirection(direction);
			physics.setTargetAngle(direction);
		}
		
		// if in sight and in range then attack
		if(hearing.isTargetDetected() && sight.isTargetInSight()) {
			machine.changeState(AttackState.getInsatnce());
		}
		
		/*
		
		TODO: This is no longer needed as is but is a useful snippet 
		it checks if 1 entity is near to another
		
		// within 1 metre of location and no enemy nearby -> standing && enemy not nearby
		if(position.y < other.y + 1 && position.y > other.y - 1
		&& position.x < other.x + 1 && position.x > other.x - 1) {
			
			if(hearing.isTargetDetected()) {
				brain.updateLastKnownEnemyPosition();
			}
			else {
				// reset direction - stop walking
				physics.resetDirection();
			
				// transition states
				machine.changeState(StandingState.getInsatnce());
			}
		}
		*/
		
		if (!hearing.isTargetDetected() && !sight.isTargetInSight()) {
			// reset direction - stop walking
			physics.resetDirection();
		
			// find a path to target
			machine.changeState(GetPathState.getInsatnce());			
						
		}
		
	}

	@Override
	public void Exit(EntityManager em, int entity, StateMachine machine) {
		// do something when state is exited
		System.out.println("It was nothing.");
	}

}
