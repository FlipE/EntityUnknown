package co.uk.cbaker.eu.game.ai.soldier;

import co.uk.cbaker.eu.game.entity.EntityManager;
import co.uk.cbaker.eu.game.entity.components.AIVision;
import co.uk.cbaker.eu.game.state.State;
import co.uk.cbaker.eu.game.state.StateMachine;

/**
 * Singleton AIState implementation
 * 
 * @author Chris B
 */
public class SpotTargetState implements State {

	/** The AIState instance */
	private static State instance;
	
	/** Private constructor can't be called from outside the class */
	private SpotTargetState() {}
	
	/**
	 * Returns the singleton AIState instance 
	 * @return the singleton AIState instance
	 */
	public static State getInsatnce() {
		if(instance == null) {
			instance = new SpotTargetState();
		}
		return instance;
	}
	
	@Override
	public void Enter(EntityManager em, int entity, StateMachine machine) {
		// do something when state is entered
		System.out.println("SpotTargetState");
	}

	@Override
	public void Execute(EntityManager em, int entity, StateMachine machine) {
		AIVision sight = em.getComponent(entity, AIVision.class);
		
		// if enemy seen -> chase state
		if(sight.isTargetInSight()) {
			System.out.println("target aquired");
			machine.changeState(GetInRangeState.getInsatnce());
		}
		else {
			// enemy not seen -> path search
			machine.changeState(GetPathState.getInsatnce());
		}	
	}

	@Override
	public void Exit(EntityManager em, int entity, StateMachine machine) {
		// do something when state is exited		
	}

}
