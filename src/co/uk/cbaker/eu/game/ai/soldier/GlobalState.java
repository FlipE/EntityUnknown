package co.uk.cbaker.eu.game.ai.soldier;

import co.uk.cbaker.eu.game.entity.EntityManager;
import co.uk.cbaker.eu.game.entity.components.Health;
import co.uk.cbaker.eu.game.state.State;
import co.uk.cbaker.eu.game.state.StateMachine;

/**
 * Singleton AIState implementation
 * 
 * @author Chris B
 */
public class GlobalState implements State {

	/** The AIState instance */
	private static State instance;
	
	/** Private constructor can't be called from outside the class */
	private GlobalState() {}
	
	/**
	 * Returns the singleton AIState instance 
	 * @return the singleton AIState instance
	 */
	public static State getInsatnce() {
		if(instance == null) {
			instance = new GlobalState();
		}
		return instance;
	}
	
	@Override
	public void Enter(EntityManager em, int entity, StateMachine machine) {
		// do something when state is entered
		System.out.println("Dead.");
	}

	@Override
	public void Execute(EntityManager em, int entity, StateMachine machine) {
		if(!machine.isInState(DeadState.class)) {
			Health health = em.getComponent(entity, Health.class);
			if(health.isDead()) {
				machine.changeState(DeadState.getInsatnce());
			}
		}
	}

	@Override
	public void Exit(EntityManager em, int entity, StateMachine machine) {
	}

}
