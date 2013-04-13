package co.uk.cbaker.eu.game.entity.components;

public class Health implements Component {

	private int maxHealth;
	private int currentHealth;
	
	/**
	 * Start an entity on max health
	 * 
	 * @param maxHealth
	 */
	public Health(int maxHealth) {
		this(maxHealth, maxHealth);
	}
	
	/**
	 * Start an entity on the given current health
	 * 
	 * @param maxHealth
	 * @param currentHealth
	 */
	public Health(int maxHealth, int currentHealth) {
		super();
		this.maxHealth = maxHealth;
		this.currentHealth = currentHealth;
	}
	
	/**
	 * health is critical at below 20%
	 * 
	 * @return true if health is less than 20%, false otherwise
	 */
	public boolean isCritical() {
		return currentHealth < 0.2f * maxHealth;
	}
	
	/**
	 * Reduce health by the amount specified. Health never goes below zero.
	 * 
	 * @param amount the amount to reduce health by
	 */
	public void damage(int amount) {
		this.currentHealth -= amount;
		if(this.currentHealth < 0) {
			this.currentHealth = 0;
		}
	}
	
	/**
	 * Increase health by the amount specified. Health stays equal or less than maximum
	 * 
	 * @param amount the amount to increase health by
	 */
	public void heal(int amount) {
		this.currentHealth += amount;
		if(this.currentHealth > this.maxHealth) {
			this.currentHealth = this.maxHealth;
		}
	}

	/**
	 * @return the currentHealth
	 */
	public int getCurrentHealth() {
		return currentHealth;
	}

	public boolean isDead() {
		return this.currentHealth <= 0;
	}
		
}