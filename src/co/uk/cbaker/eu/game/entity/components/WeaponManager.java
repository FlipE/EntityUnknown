package co.uk.cbaker.eu.game.entity.components;

import co.uk.cbaker.eu.game.model.weapons.Weapon;

import com.badlogic.gdx.utils.Array;

public class WeaponManager implements Component {

	private Array<Weapon> weapons;
	private int weaponIndex;
	private Weapon weapon;
	private boolean tryShooting;
	
	public WeaponManager(Weapon weapon) {
		super();
		this.weapons = new Array<Weapon>();
		this.weapons.add(weapon);
		this.weapon = weapon;
		this.weaponIndex = 0;
	}

	public void add(Weapon weapon) {
		weapons.add(weapon);
	}
	
	public void nextWeapon() {
		this.weaponIndex = (this.weaponIndex + 1) % this.weapons.size;
		this.weapon = this.weapons.get(this.weaponIndex);
	}
	
	public void previousWeapon() {
		this.weaponIndex -= 1;
		if(this.weaponIndex < 0) {
			this.weaponIndex = this.weapons.size -1;
		}
		this.weapon = this.weapons.get(this.weaponIndex);
	}
	
	public boolean shoot() {
		if(tryShooting) {
			return weapon.shoot();
		}
		return false;
	}
	
	/**
	 * Reduce the time until weapon can fire again
	 * 
	 * @param deltaTime
	 */
	public void stepTimer(float delta) {
		weapon.update(delta);
	}

	/**
	 * @param isShooting the isShooting to set
	 */
	public void tryShooting(boolean tryShooting) {
		this.tryShooting = tryShooting;
	}

	/**
	 * The range of the currently equipped weapon
	 * @return
	 */
	public float getRange() {
		return weapon.getRange();
	}

	/**
	 * the damage of the currently equipped weapon
	 * @return
	 */
	public int getDamage() {
		return weapon.getDamage();
	}
	
	public int getAmmoInClip() {
		return this.weapon.getAmmoInClip();
	}
	
	public int getClipCapacity() {
		return this.weapon.getClipCapacity();
	}
	
	public int getAmmoPool() {
		return this.weapon.getAmmoPool();
	}
	
	public String getName() {
		return weapon.getName();
	}
}