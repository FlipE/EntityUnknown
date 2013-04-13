package co.uk.cbaker.eu.game.model.weapons;

public class Weapon extends AbstractWeapon {

	/**
	 * @param name
	 * @param damage
	 * @param range
	 * @param rateOfFire
	 * @param reloadTime
	 * @param clipCapacity
	 * @param ammoPool
	 */
	private Weapon(String name, int damage, float range, float rateOfFire, float reloadTime, int clipCapacity, int ammoPool) {
		super(name, damage, range, rateOfFire, reloadTime, clipCapacity, ammoPool);
	}

	/**
	 * Returns an instance of Weapon configured as a pistol
	 * @return
	 */
	public static Weapon pistol() {
		String name = "Pistol";
		int damage = 5;
		float range = 20;
		float rateOfFire = 1.0f;
		float reloadTime = 2.0f;
		int clipCapacity = 17;
		int ammoPool = 51;
		
		return new Weapon(name, damage, range, rateOfFire, reloadTime, clipCapacity, ammoPool);
	}
	
	/**
	 * Returns an instance of Weapon configured as a pistol
	 * @return
	 */
	public static Weapon assualtRifle() {
		String name = "Assault Rifle";
		int damage = 20;
		float range = 20;
		float rateOfFire = 0.1f;
		float reloadTime = 2.0f;
		int clipCapacity = 30;
		int ammoPool = 90;
		
		return new Weapon(name, damage, range, rateOfFire, reloadTime, clipCapacity, ammoPool);
	}
	
}