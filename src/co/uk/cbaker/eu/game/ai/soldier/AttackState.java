package co.uk.cbaker.eu.game.ai.soldier;

import com.badlogic.gdx.math.Vector2;

import co.uk.cbaker.eu.game.entity.EntityManager;
import co.uk.cbaker.eu.game.entity.components.AIBrain;
import co.uk.cbaker.eu.game.entity.components.AIHearing;
import co.uk.cbaker.eu.game.entity.components.AIVision;
import co.uk.cbaker.eu.game.entity.components.Physics2D;
import co.uk.cbaker.eu.game.entity.components.WeaponManager;
import co.uk.cbaker.eu.game.state.State;
import co.uk.cbaker.eu.game.state.StateMachine;

/**
 * Singleton AIState implementation
 * 
 * @author Chris B
 */
public class AttackState implements State {

	/** The AIState instance */
	private static State instance;
	
	/** Private constructor can't be called from outside the class */
	private AttackState() {}
	
	/**
	 * Returns the singleton AIState instance 
	 * @return the singleton AIState instance
	 */
	public static State getInsatnce() {
		if(instance == null) {
			instance = new AttackState();
		}
		return instance;
	}
	
	@Override
	public void Enter(EntityManager em, int entity, StateMachine machine) {
		// do something when state is entered
		System.out.println("opening fire!");
	}

	@Override
	public void Execute(EntityManager em, int entity, StateMachine machine) {
		// if out of hearing then get back in range
		AIHearing hearing = em.getComponent(entity, AIHearing.class);
		if (!hearing.isTargetDetected()) {
			machine.changeState(GetInRangeState.getInsatnce());
			return;
		}
		
		Physics2D physics = em.getComponent(entity, Physics2D.class);
		AIBrain brain = em.getComponent(entity, AIBrain.class);
		AIVision sight = em.getComponent(entity, AIVision.class);
		WeaponManager weapon = em.getComponent(entity, WeaponManager.class);
		
		// reset direction and shooting each update
		physics.resetDirection();
		weapon.tryShooting(false);
		
		Vector2 position = physics.getBody().getPosition();
		Vector2 other = brain.getLastKnownEnemyPosition();
			
		// the difference in vectors is the direction
		Vector2 direction = new Vector2(other.x - position.x, other.y - position.y);
		
		// set the new direction
		physics.setTargetAngle(direction);
						
		if (sight.isTargetInSight()) {
			// shoot
			weapon.tryShooting(true);
		}
		else {
			// find a path
			machine.changeState(GetPathState.getInsatnce());
			//System.out.println("cant find a path");
		}
		
		// enemy seen -> spot enemy
		
	}

	@Override
	public void Exit(EntityManager em, int entity, StateMachine machine) {
		
		// make sure to stop firing when the state is left
		WeaponManager weapon = em.getComponent(entity, WeaponManager.class);
		if(weapon != null) {
			weapon.tryShooting(false);
		}
	}

}
