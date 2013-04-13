package co.uk.cbaker.eu.game.entity.systems;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import co.uk.cbaker.eu.game.Config;
import co.uk.cbaker.eu.game.entity.EntityManager;
import co.uk.cbaker.eu.game.entity.components.AIBrain;
import co.uk.cbaker.eu.game.entity.components.AIVision;
import co.uk.cbaker.eu.game.entity.components.CollisionTag;
import co.uk.cbaker.eu.game.entity.components.Physics2D;
import co.uk.cbaker.eu.game.physics.callback.AIRayCastCallback;

public class AISightSystem extends AbstractTimedSystem {

	private AIVision sight;
	private AIBrain brain;
	private Physics2D physics;
	private Body body;
	private Vector2 position;
	private Vector2 target;
	private AIRayCastCallback callback;
	private World world;
	
	public AISightSystem(EntityManager entityManager, float frequency, World world) {
		super(entityManager, frequency);
		this.world = world;
		this.callback = new AIRayCastCallback();
	}

	@Override
	public void process() {
		super.entities = em.getAllEntitiesPossessingComponent(AIVision.class);
		super.process();
	}

	@Override
	public void process(int entity) {
		sight = em.getComponent(entity, AIVision.class);
		brain = em.getComponent(entity, AIBrain.class);
		physics = em.getComponent(entity, Physics2D.class);
		
		sight.setTargetInSight(false);
		
		if(brain != null && physics != null) {
			// check if the brains target is in line of sight
			body = physics.getBody();
			position = body.getPosition();
			target = brain.getEnemyPosition();
			
			if(target != null) {
				world.rayCast(callback, position, target);
				
				if(callback.fixture != null) {
					CollisionTag t = (CollisionTag) callback.fixture.getUserData();
					if(t != null) {
						if(t.getCollisionID() == Config.PLAYER) {
							//System.out.println("Player in sight");
							sight.setTargetInSight(true);
							brain.updateLastKnownEnemyPosition();
						}						
					}					
				}				
			}			
		}		
	}
	
}