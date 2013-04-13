package co.uk.cbaker.eu.game.entity.systems;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import co.uk.cbaker.eu.game.entity.EntityManager;
import co.uk.cbaker.eu.game.entity.components.Bullet;
import co.uk.cbaker.eu.game.entity.components.Expires;

public class BulletRenderSystem extends AbstractSystem {

	private Bullet bullet;
	private Expires expires;
	private ShapeRenderer shapeRenderer;
	private PerspectiveCamera cam;
	
	public BulletRenderSystem(EntityManager entityManager, PerspectiveCamera cam) {
		super(entityManager);
		this.shapeRenderer = new ShapeRenderer();
		this.cam = cam;
	}
	
	@Override
	public void process() {
		super.entities = em.getAllEntitiesPossessingComponent(Bullet.class);
		
		// begin
		cam.update();
		shapeRenderer.setProjectionMatrix(cam.combined);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(1, 1, 0, 1);
		
		// render
		super.process();
		
		// end
		shapeRenderer.end();
	}
	
	@Override
	public void process(int entity) {
		bullet = em.getComponent(entity, Bullet.class);
		expires = em.getComponent(entity, Expires.class);
		
		// if bullet has an expired component and it is expired then do not render
		if(expires != null) {
			if(expires.isExpired()) {
				return;
			}
		}		
		shapeRenderer.line(bullet.getX1(), bullet.getY1(), bullet.getX2(), bullet.getY2());		
	}
}