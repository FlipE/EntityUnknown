package co.uk.cbaker.eu.game.factory;

import co.uk.cbaker.eu.game.Config;
import co.uk.cbaker.eu.game.entity.EntityManager;
import co.uk.cbaker.eu.game.entity.components.Bullet;
import co.uk.cbaker.eu.game.entity.components.CollisionTag;
import co.uk.cbaker.eu.game.entity.components.Expires;

import com.badlogic.gdx.physics.box2d.World;

public class BulletFactory extends AbstractFactory {

	private static BulletFactory instance;
	
	private BulletFactory() {
		
	}
	
	public static BulletFactory getInstance() {
		if(instance == null) {
			instance = new BulletFactory();
		}
		return instance;
	}
	
	public int bullet(World world, EntityManager em, float x1, float y1, float x2, float y2, float lifespan) {

		int entity = 0;
		
		if(super.inactive.size > 0) {
			entity = super.inactive.removeIndex(0);
			
			Bullet bullet = em.getComponent(entity, Bullet.class);
			bullet.set(x1, y1, x2, y2);
			
			Expires expires = em.getComponent(entity, Expires.class);
			if(expires != null) {
				expires.setLifespan(lifespan);
			}
		}
		else {
			// create the entity id
			entity = em.createEntity();

			// create a tag with collision id and entity id
			CollisionTag collisionTag = new CollisionTag(entity, Config.BULLET);
			
			// every entity has a tag
			em.addComponent(entity, collisionTag);
			em.addComponent(entity, new Bullet(x1, y1, x2, y2));
			em.addComponent(entity, new Expires(lifespan));
		}
		
		// add entity to active list
		super.active.add(entity);
		
		return entity;
	}
	
	/*
	
	public static int bullet(World world, EntityManager em, float x, float y, float angle) {

		PolygonShape boxPoly = new PolygonShape();
		boxPoly.setAsBox(0.2f, 0.2f);

		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = BodyType.DynamicBody;

		// box starting position
		boxBodyDef.position.x = x;
		boxBodyDef.position.y = y;
		Body body = world.createBody(boxBodyDef);

		FixtureDef fixture = new FixtureDef();
		fixture.isSensor = true;
		fixture.density = 15f;
		fixture.shape = boxPoly;
		body.createFixture(fixture);

		// create the entity id
		int entity = em.createEntity();

		// create a tag with collision id and entity id
		CollisionTag collisionTag = new CollisionTag(entity, Config.BULLET);

		// set the user data on the fixture
		body.getFixtureList().get(0).setUserData(collisionTag);

		Physics2D physics =  new Physics2D(body, 48, true);
		
		physics.setDirection((float)Math.cos(angle), (float)Math.sin(angle));
		
		// every entity has a tag
		em.addComponent(entity, collisionTag);
		em.addComponent(entity, physics);

		boxPoly.dispose();
		
		return entity;
	}
	
	*/
}