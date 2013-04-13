package co.uk.cbaker.eu.game.factory;

import com.badlogic.gdx.utils.Array;

public abstract class AbstractFactory {

	protected Array<Integer> active;
	protected Array<Integer> inactive;
	
	public AbstractFactory() {
		super();
		this.active = new Array<Integer>(16);
		this.inactive = new Array<Integer>(16);
	}
	
	public void deactivate(int entity) {
		if(this.active.removeValue(entity, true)) {
			this.inactive.add(entity);
		}
	}
}