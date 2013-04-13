package co.uk.cbaker.eu.game.entity.systems;

import co.uk.cbaker.eu.game.entity.EntityManager;
import co.uk.cbaker.eu.game.entity.components.CameraFocus;
import co.uk.cbaker.eu.game.entity.components.Physics2D;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.physics.box2d.Body;

public class CameraFocusSystem extends AbstractSystem {

	private Physics2D physics;
	private CameraFocus focus;
	private Body body;
	private PerspectiveCamera cam;

	public CameraFocusSystem(EntityManager entityManager, PerspectiveCamera cam) {
		super(entityManager);
		this.cam = cam;
	}

	@Override
	public void process() {
		super.entities = em.getAllEntitiesPossessingComponent(CameraFocus.class);
		super.process();
	}

	@Override
	public void process(int entity) {
		focus = em.getComponent(entity, CameraFocus.class);
		if (focus.isCameraFocus()) {
			physics = em.getComponent(entity, Physics2D.class);
			body = physics.getBody();

			// set the camera to track the players position.
			// it would be nice for the camera to zoom out the faster the camera
			// focus entity is travelling
			cam.position.set(body.getPosition().x, body.getPosition().y - 6, calculateCameraHeight(body));
			cam.direction.set(body.getPosition().x, body.getPosition().y, -1).sub(cam.position).nor();
			cam.update();			
		}
	}

	private float calculateCameraHeight(Body body) {
		return 20;
	}	
}
