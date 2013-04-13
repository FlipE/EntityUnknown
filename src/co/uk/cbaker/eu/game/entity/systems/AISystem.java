package co.uk.cbaker.eu.game.entity.systems;

import co.uk.cbaker.eu.game.entity.EntityManager;
import co.uk.cbaker.eu.game.entity.components.AIBrain;
import co.uk.cbaker.eu.game.pathfinding.AStar;
import co.uk.cbaker.eu.game.state.StateMachine;

public class AISystem extends AbstractTimedSystem {

	private AIBrain ai;
	private StateMachine machine;
	private AStar pathFinder;

	public AISystem(EntityManager entityManager, float frequency, AStar pathFinder) {
		super(entityManager, frequency);
		this.pathFinder = pathFinder;
	}

	@Override
	public void process() {
		super.entities = em.getAllEntitiesPossessingComponent(AIBrain.class);
		super.process();
	}

	@Override
	public void process(int entity) {
		ai = em.getComponent(entity, AIBrain.class);
		machine = ai.getMachine();
		machine.update();
	}
}