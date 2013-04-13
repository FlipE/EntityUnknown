package co.uk.cbaker.eu.game.ai.soldier;

import co.uk.cbaker.eu.game.entity.EntityManager;
import co.uk.cbaker.eu.game.state.State;
import co.uk.cbaker.eu.game.state.StateMachine;

public class SoldierStateMachine implements StateMachine {

	private State currentState;
	private State previousState;
	private State globalState;
	private EntityManager em;
	private int entity;
	
	/**
	 * 
	 * @param em The entity manager
	 * @param entity The entity which owns this machine
	 * @param currentState The start state of this machine
	 * @param globalState A global state in which common behaviours reside
	 */
	public SoldierStateMachine(EntityManager em, int entity, State currentState, State globalState) {
		super();
		this.em = em;
		this.entity = entity;
		this.currentState = currentState;
		this.previousState = currentState;
		this.globalState = globalState;
	}
	
	/**
	 * 
	 * @param em The entity manager
	 * @param entity The entity which owns this machine
	 * @param currentState The start state of this machine
	 */
	public SoldierStateMachine(EntityManager em, int entity, State currentState) {
		this(em, entity, currentState, null);
	}
	
	@Override
	public void update() {
		// if a global state exists, call its execute method
		if(this.globalState != null) {
			this.globalState.Execute(em, entity, this);
		}
		
		// same for the current state
		if(this.currentState != null) {
			this.currentState.Execute(em, entity, this);
		}
	}

	@Override
	public void changeState(State s) {
		if(s == null) {
			// TODO: throw null state error
			return;
		}
		
		// keep a record of the previous state
		this.previousState = this.currentState;
		
		// call the exit method of the previous state
		this.previousState.Exit(em, entity, this);
		
		// change state to new one
		this.currentState = s;
		
		// call the entry method of the new state
		this.currentState.Enter(em, entity, this);
	}

	@Override
	public void revertToPreviousState() {
		this.changeState(this.previousState);
	}

	@Override
	public boolean isInState(Class<? extends State> c) {		
		return this.currentState.getClass() == c;
	}

}