package co.uk.cbaker.eu.game.entity.systems;

import com.badlogic.gdx.physics.box2d.Body;

import co.uk.cbaker.eu.game.entity.EntityManager;
import co.uk.cbaker.eu.game.entity.components.AIHearing;
import co.uk.cbaker.eu.game.entity.components.Physics2D;

public class AIHearingSystem extends AbstractSystem {

	private Physics2D physics;
	private AIHearing hearing;
	private Body sensor;
	private Body body;

	public AIHearingSystem(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public void process() {
		super.entities = em.getAllEntitiesPossessingComponent(AIHearing.class);
		super.process();
	}

	@Override
	public void process(int entity) {
		physics = em.getComponent(entity, Physics2D.class);
		hearing = em.getComponent(entity, AIHearing.class);
		sensor = hearing.getBody();
		
		if(physics != null) {
			body = physics.getBody();
		
			// set the sensor to the body's location and rotation
			sensor.setTransform(body.getPosition(), body.getAngle());
		}
	}
}
