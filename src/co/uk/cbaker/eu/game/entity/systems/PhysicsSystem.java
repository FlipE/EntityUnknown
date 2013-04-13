package co.uk.cbaker.eu.game.entity.systems;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import co.uk.cbaker.eu.game.entity.EntityManager;
import co.uk.cbaker.eu.game.entity.components.Health;
import co.uk.cbaker.eu.game.entity.components.Physics2D;

public class PhysicsSystem extends AbstractSystem {

	// create these here to avoid garbage collection of pointers;
	private Vector2 position;
	private Vector2 velocity;
	private Vector2 direction;
	private float maxVeclocity;
	private Body body;
	private Physics2D physics;
	private float target;
	private float angle;
	private Health health;

	public PhysicsSystem(EntityManager entityManager) {
		super(entityManager);
		direction = new Vector2();
	}

	@Override
	public void process() {
		super.entities = em.getAllEntitiesPossessingComponent(Physics2D.class);
		super.process();
	}

	@Override
	public void process(int entity) {

		// get the component from the entity
		physics = em.getComponent(entity, Physics2D.class);

		// get the physics body, direction and maximum velocity from the
		// component
		body = physics.getBody();

		// reset velocity if this object is not a projectile
		if (!physics.isProjectile()) {
			body.setLinearVelocity(0, 0);
		}
		
		// stop body from spinning continuously
		body.setAngularVelocity(0);
		
		// skip if entity has health and is dead
		health = em.getComponent(entity, Health.class);
		if (health != null) {
			if (health.isDead()) {
				return;
			}
		}		

		// set the direction vector and normalise it
		direction.x = physics.getDirection().x;
		direction.y = physics.getDirection().y;
		direction.nor();

		maxVeclocity = physics.getMaxVeclocity();

		// ---------------------------------------------

		// set the position vector from the physics body
		position = body.getPosition();

		// apply an impulse in the set direction is isMoving
		body.applyLinearImpulse(direction.x * maxVeclocity * 10, direction.y * maxVeclocity * 10, position.x, position.y);

		// ---------------------------------------------

		// set the velocity vector from the physics body
		velocity = body.getLinearVelocity();

		// limit the maximum velocity
		if (Math.abs(velocity.x) > maxVeclocity) {
			velocity.x = Math.signum(velocity.x) * maxVeclocity;
			body.setLinearVelocity(velocity.x, velocity.y);
		}
		if (Math.abs(velocity.y) > maxVeclocity) {
			velocity.y = Math.signum(velocity.y) * maxVeclocity;
			body.setLinearVelocity(velocity.x, velocity.y);
		}

		// ---------------------------------------------

		// target angle is the direction velocity
		// float target = MathUtils.atan2(velocity.y, velocity.x);
		target = physics.getTargetAngle();
		angle = body.getAngle();

		// if the angle is not near to the target angle then turn to face
		//if (angle < target - 0.001 || angle > target + 0.001) {
		if (angle < target || angle > target) {
			// Stop the body from turning the wrong direction by correcting the
			// angle
			if (angle - target < -Math.PI) {
				angle += 2 * Math.PI;
			}
			if (angle - target > Math.PI) {
				angle -= 2 * Math.PI;
			}

			// Slowly adjust the angle to the target angle
			// rotation speed is proportional to difference in angle
			body.setTransform(position.x, position.y, angle - (0.2f * (angle - target)));
		}
	}
}