package co.uk.cbaker.eu.game.entity.systems;

import com.badlogic.gdx.Gdx;

import co.uk.cbaker.eu.game.entity.EntityManager;
import co.uk.cbaker.eu.game.entity.components.Expires;
import co.uk.cbaker.eu.game.factory.BulletFactory;

public class BulletExpirationSystem extends AbstractSystem {

	private Expires expires;
	private BulletFactory factory;
	
	public BulletExpirationSystem(EntityManager entityManager) {
		super(entityManager);
		this.factory = BulletFactory.getInstance();
	}
	
	@Override
	public void process() {
		super.entities = em.getAllEntitiesPossessingComponent(Expires.class);
		super.process();
	}
	
	@Override
	public void process(int entity) {
		expires = em.getComponent(entity, Expires.class);
		
		// check here and return if already expired
		if(expires.isExpired()) {
			return;
		}
		
		// step the timer
		expires.step(Gdx.graphics.getDeltaTime());
		
		// check again and deactivate freshly expired entities
		if(expires.isExpired()) {
			factory.deactivate(entity);
		}
	}	
}