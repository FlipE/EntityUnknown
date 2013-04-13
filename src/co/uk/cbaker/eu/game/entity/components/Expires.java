package co.uk.cbaker.eu.game.entity.components;

public class Expires implements Component {

	private float lifespan;
	
	/**
	 * 
	 * @param lifespan 
	 */
	public Expires(float lifespan) {
		super();
		this.lifespan = lifespan;
	}

	/**
	 * 
	 * @param delta
	 */
	public void step(float delta) {
		this.lifespan -= delta;
		if(this.lifespan < 0) {
			this.lifespan = 0;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isExpired() {
		return this.lifespan == 0;
	}
	
	/**
	 * 
	 * @param lifespan
	 */
	public void setLifespan(float lifespan) {
		this.lifespan = lifespan;
	}
	
}