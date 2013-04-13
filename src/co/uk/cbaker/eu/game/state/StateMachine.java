package co.uk.cbaker.eu.game.state;

public interface StateMachine {

	public void update();
	public void changeState(State s);
	public void revertToPreviousState();
	public boolean isInState(Class<? extends State> c);
	
}