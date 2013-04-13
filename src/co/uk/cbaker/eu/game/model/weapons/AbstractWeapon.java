package co.uk.cbaker.eu.game.model.weapons;

import co.uk.cbaker.eu.game.model.Model;

public abstract class AbstractWeapon implements Model {

	// various weapon attributes
	private String name;
	private int damage;
	private float range;
	private float rateOfFire;
	private int clipCapacity;
	private float reloadTime;
	
	// current ammo trackers
	private int ammoInClip;
	private int ammoPool;
	
	// shooting and reload timers
	private float timeUntilNextShot;
	private float timeUntilReloaded;
		
	/**
	 * @param name
	 * @param damage
	 * @param range
	 * @param rateOfFire
	 * @param reloadTime
	 * @param clipCapacity
	 * @param ammoPool
	 */
	public AbstractWeapon(String name, int damage, float range, float rateOfFire, float reloadTime, int clipCapacity, int ammoPool) {
		super();
		
		// various weapon attributes
		this.name = name;
		this.damage = damage;
		this.range = range;
		this.rateOfFire = rateOfFire;
		this.clipCapacity = clipCapacity;
		this.reloadTime = reloadTime;
		
		// current ammo trackers
		this.ammoInClip = clipCapacity;
		this.ammoPool = ammoPool;
		
		// shooting and reload timers
		this.timeUntilNextShot = 0;
		this.timeUntilReloaded = 0;
	}
	
	/**
	 * @return the ammoPool
	 */
	public int getAmmoPool() {
		return ammoPool;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * The damage caused by this weapon per shot
	 * 
	 * @return The damage caused by this weapon each shot
	 */
	public int getDamage() {
		return this.damage;
	}
	
	/**
	 * The number of shots before the weapon needs to be reloaded
	 * 
	 * @return the number of shots before the weapon needs to be reloaded
	 */
	public int getClipCapacity() {
		return this.clipCapacity;
	}
		
	/**
	 * @return the ammoInClip
	 */
	public int getAmmoInClip() {
		return ammoInClip;
	}

	/**
	 * The distance in meters the weapon can fire
	 * 
	 * @return the distance in meters the weapon can fire
	 */
	public float getRange() {
		return this.range;
	}
	
	/**
	 * Check to see if the weapon is ready to fire
	 * 
	 * @return true if the weapon is ready, false otherwise
	 */
	private boolean isReady() {
		return this.timeUntilNextShot <= 0 && this.timeUntilReloaded <= 0 && (this.ammoInClip > 0 || this.ammoPool > 0);
	}	
	
	/**
	 * Reload the weapon from the ammo pool. If the pool is not enough to fill the clip
	 * the remaining bullets are used.
	 */
	private void reload() {
		// try to fill the clip to capacity
		if(this.ammoPool >= this.clipCapacity) {
			this.ammoInClip = this.clipCapacity;			
		}
		else {
			this.ammoInClip = this.ammoPool;
		}
		// reduce the ammo pool
		this.ammoPool -= this.ammoInClip;
	}
	
	/**
	 * attempt to shoot the weapon first checking if it is ready, then the ammo.
	 * @return true if there is ammo and the weapon is ready, false otherwise
	 */
	public boolean shoot() {
		if(this.isReady()) {			
			if(this.getAmmoInClip() > 0) {
				// successful shot
				this.timeUntilNextShot = this.rateOfFire;
				this.ammoInClip -= 1;
				
				// could do extra check for ammo in clip being 0 here to reload as soon as ammo is out 
				
				return true;
			}
			else {
				// time to reload
				this.timeUntilReloaded = this.reloadTime;
				return false;
			}
		}
		else {
			// weapon not recharged yet
			return false;
		}
	}
	
	@Override
	public void update(float delta) {
		// decremement the time until next shot timer
		if(this.timeUntilNextShot > 0) {
			this.timeUntilNextShot -= delta;
		}
		
		// decrement the time until reload completed timer
		if(this.timeUntilReloaded > 0) {
			this.timeUntilReloaded -= delta;
			// if the timer is now below 0, replenish clip from ammo pool
			if(this.timeUntilReloaded <= 0) {
				this.reload();
			}
		}
	}
}