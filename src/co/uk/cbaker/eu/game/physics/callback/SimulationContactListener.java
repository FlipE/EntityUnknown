package co.uk.cbaker.eu.game.physics.callback;

import co.uk.cbaker.eu.game.Config;
import co.uk.cbaker.eu.game.entity.EntityManager;
import co.uk.cbaker.eu.game.entity.components.AIBrain;
import co.uk.cbaker.eu.game.entity.components.AIHearing;
import co.uk.cbaker.eu.game.entity.components.CollisionTag;
import co.uk.cbaker.eu.game.entity.components.Physics2D;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class SimulationContactListener implements ContactListener {

	EntityManager entityManager;
	
	public SimulationContactListener(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	// player to player 		-> nothing
	// player to ammo   		-> player gets ammo
	// player to health			-> player get health
	// player to damage zone	-> player damaged
	// player to ai hearing 	-> ai notified player spotted
	@Override
	public void beginContact(Contact contact) {
		
		CollisionTag a = (CollisionTag) contact.getFixtureA().getUserData();
		CollisionTag b = (CollisionTag) contact.getFixtureB().getUserData();
		
		if(a == null || b == null) {
			return;
		}
		
		int typeA = a.getCollisionID();
		int typeB = b.getCollisionID();
		
		if(typeA == Config.PLAYER) {
			resolvePlayerCollision(a, b);
		}
		else if(typeB == Config.PLAYER) {
			resolvePlayerCollision(b, a);
		}		
		
	}

	public void resolvePlayerCollision(CollisionTag player, CollisionTag other) {
		int p = player.getEntity();
		int o = other.getEntity();
		int type = other.getCollisionID();
		
		switch(type) {
			case Config.AI_HEARING:
				Physics2D playerPhysics = entityManager.getComponent(p, Physics2D.class);
				AIHearing otherHearing = entityManager.getComponent(o, AIHearing.class);				
				AIBrain otherBrain = entityManager.getComponent(o, AIBrain.class);
				
				// only update the detection when no other detection is made					
				if(!otherHearing.isTargetDetected()) {						
					otherHearing.setTargetDetected(true);						
					otherBrain.setEnemyPosition(playerPhysics.getBody().getPosition());
					otherBrain.setLastKnownEnemyPosition(playerPhysics.getBody().getPosition().x, playerPhysics.getBody().getPosition().y);
				}
			break;
		}
	}
	
	@Override
	public void endContact(Contact contact) {
		
		CollisionTag a = (CollisionTag) contact.getFixtureA().getUserData();
		CollisionTag b = (CollisionTag) contact.getFixtureB().getUserData();
		
		if(a == null || b == null) {
			return;
		}
		
		int typeA = a.getCollisionID();
		int typeB = b.getCollisionID();
		
		if(typeA == Config.PLAYER) {
			endPlayerCollision(a, b);
		}
		else if(typeB == Config.PLAYER) {
			endPlayerCollision(b, a);
		}
	}

	public void endPlayerCollision(CollisionTag player, CollisionTag other) {
		//int p = player.getEntity();
		int o = other.getEntity();
		int type = other.getCollisionID();
		
		switch(type) {
			case Config.AI_HEARING:
				AIHearing h = entityManager.getComponent(o, AIHearing.class);
				h.setTargetDetected(false);
				
			break;
		}
	}
	
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	
}