package co.uk.cbaker.eu.game.ai.soldier;

import co.uk.cbaker.eu.game.entity.EntityManager;
import co.uk.cbaker.eu.game.entity.components.AIHearing;
import co.uk.cbaker.eu.game.state.State;
import co.uk.cbaker.eu.game.state.StateMachine;

/**
 * Singleton AIState implementation
 * 
 * @author Chris B
 */
public class IdleState implements State {

	/** The AIState instance */
	private static State instance;
	
	/** Private constructor can't be called from outside the class */
	private IdleState() {}
	
	/**
	 * Returns the singleton AIState instance 
	 * @return the singleton AIState instance
	 */
	public static State getInsatnce() {
		if(instance == null) {
			instance = new IdleState();
		}
		return instance;
	}
	
	@Override
	public void Enter(EntityManager em, int entity, StateMachine machine) {
		// do something when state is entered
		System.out.println("Idle.");
	}

	@Override
	public void Execute(EntityManager em, int entity, StateMachine machine) {
		AIHearing hearing = em.getComponent(entity, AIHearing.class);
		
		// if enemy heard -> searching
		if(hearing.isTargetDetected()) {
			machine.changeState(SpotTargetState.getInsatnce());
		}		
	}

	@Override
	public void Exit(EntityManager em, int entity, StateMachine machine) {
		// do something when state is exited
		System.out.println("Hey! You hear something?");
	}

}
