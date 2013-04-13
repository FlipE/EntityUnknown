package co.uk.cbaker.eu.game.entity.components;

public class Bullet implements Component {

	private float x1;
	private float y1;
	private float x2;
	private float y2;
	
	/**
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param lifespan 
	 */
	public Bullet(float x1, float y1, float x2, float y2) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	/**
	 * @return the x1
	 */
	public float getX1() {
		return x1;
	}

	/**
	 * @return the y1
	 */
	public float getY1() {
		return y1;
	}

	/**
	 * @return the x2
	 */
	public float getX2() {
		return x2;
	}

	/**
	 * @return the y2
	 */
	public float getY2() {
		return y2;
	}
	
	/**
	 * Set the location of this bullet
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void set(float x1, float y1, float x2, float y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
}