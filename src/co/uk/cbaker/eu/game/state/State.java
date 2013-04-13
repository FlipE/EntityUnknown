package co.uk.cbaker.eu.game.state;

import co.uk.cbaker.eu.game.entity.EntityManager;

public interface State {

	public void Enter(EntityManager em, int entity, StateMachine machine);
	public void Execute(EntityManager em, int entity, StateMachine machine);
	public void Exit(EntityManager em, int entity, StateMachine machine);
	
}