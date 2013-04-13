package co.uk.cbaker.eu.game.entity.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import co.uk.cbaker.eu.game.Config;
import co.uk.cbaker.eu.game.entity.EntityManager;
import co.uk.cbaker.eu.game.entity.components.CollisionTag;
import co.uk.cbaker.eu.game.entity.components.Health;
import co.uk.cbaker.eu.game.entity.components.Physics2D;
import co.uk.cbaker.eu.game.entity.components.WeaponManager;
import co.uk.cbaker.eu.game.factory.BulletFactory;
import co.uk.cbaker.eu.game.physics.callback.ShootingRayCastCallback;

public class ShootingSystem extends AbstractSystem {

	private World world;
	private WeaponManager weapon;
	private Health health;
	private Physics2D physics;
	private Body body;
	private Vector2 position;
	private Vector2 target;
	private Vector2 contact;
	private float angle;
	private ShootingRayCastCallback callback;
	private BulletFactory factory;

	public ShootingSystem(EntityManager entityManager, World world) {
		super(entityManager);
		this.world = world;
		this.callback = new ShootingRayCastCallback();
		this.target = new Vector2();
		this.contact = new Vector2();
		this.factory = BulletFactory.getInstance();
	}

	@Override
	public void process() {
		super.entities = em.getAllEntitiesPossessingComponent(WeaponManager.class);
		super.process();
	}

	@Override
	public void process(int entity) {
		
		// skip this weapon if the owner entity is dead
		health = em.getComponent(entity, Health.class);
		if(health != null) {
			if (health.isDead()) {
				return;
			}
		}
		
		weapon = em.getComponent(entity, WeaponManager.class);
		physics = em.getComponent(entity, Physics2D.class);

		// step the weapon
		weapon.stepTimer(Gdx.graphics.getDeltaTime());
		
		// try shooting the weapon
		if (weapon.shoot()) {

			// set the starting coordinates
			body = physics.getBody();
			position = body.getPosition();

			// set the target coordinates
			// angle = body.getAngle();
			angle = physics.getTargetAngle();
			target.x = (float) Math.cos(angle);
			target.y = (float) Math.sin(angle);

			// multiply the target vector by weapon range before adding start
			// position
			target.mul(weapon.getRange());

			// add the starting position to the angle vector
			target.add(position);

			// cast a ray
			// this.rayCast(position, target);
			callback.reset();
			world.rayCast(callback, position, target);

			if (callback.fixture != null) {
								
				// get the point at which the ray collides
				//contact = callback.point;
				
				///////////////////////////////////////////////////////////////////////////
				// This section is necessary because raycast callback fails to set
				// the correct position of the nearest contact when ignoring sensors
				
				contact.x = (float) Math.cos(angle);
				contact.y = (float) Math.sin(angle);

				// multiply the target vector by callback fraction before adding start position
				contact.mul(weapon.getRange() * callback.fraction);

				// add the starting position to the angle vector
				contact.add(position);
				
				///////////////////////////////////////////////////////////////////////////
				
				// create a bullet from the start location to the contact point
				int bulletLifespan = 1;
				this.factory.bullet(world, em, position.x, position.y, contact.x, contact.y, bulletLifespan);

				// create a physical reaction to the impact
				// callback.fixture.getBody().applyForce(target.mul(100), contact);

				// get the type of the collision
				CollisionTag tag = (CollisionTag) callback.fixture.getUserData();
				if (tag != null) {

					// grab the entity
					int e = tag.getEntity();

					// damage the hit object
					// create a new hit entity and play a sound
					if (tag.getCollisionID() == Config.PLAYER) {
						// hurt player
						Health h = em.getComponent(e, Health.class);
						h.damage(weapon.getDamage());
						
					} else if (tag.getCollisionID() == Config.AI_BOT) {
						// hurt ai
						Health h = em.getComponent(e, Health.class);
						h.damage(weapon.getDamage());						
						
					} else if (tag.getCollisionID() == Config.AI_HEARING) {
						// this should never happen because of the sensor filter
						System.out.println("AI Hearing");
					}

				} else {					
					// TODO: queue a ricochet sound
					// TODO: queue a particle effect
				}
			} else {
				// bullet made no contact
				this.factory.bullet(world, em, position.x, position.y, target.x, target.y, 1);

			}
		}
	}
}