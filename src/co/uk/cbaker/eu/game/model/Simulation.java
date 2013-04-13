package co.uk.cbaker.eu.game.model;

import io.LevelLoader;
import co.uk.cbaker.eu.game.entity.EntityManager;
import co.uk.cbaker.eu.game.entity.systems.AIHearingSystem;
import co.uk.cbaker.eu.game.entity.systems.AISightSystem;
import co.uk.cbaker.eu.game.entity.systems.AISystem;
import co.uk.cbaker.eu.game.entity.systems.BulletExpirationSystem;
import co.uk.cbaker.eu.game.entity.systems.PhysicsSystem;
import co.uk.cbaker.eu.game.entity.systems.PlayerControllerSystem;
import co.uk.cbaker.eu.game.entity.systems.ShootingSystem;
import co.uk.cbaker.eu.game.entity.systems.SubSystem;
import co.uk.cbaker.eu.game.factory.LevelFactory;
import co.uk.cbaker.eu.game.pathfinding.AStar;
import co.uk.cbaker.eu.game.physics.callback.SimulationContactListener;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Simulation implements Model {

	private World b2World;
	private Floor floor;
	private AStar pathFinder;
	private EntityManager entityManager;
	
	// Systems
	private SubSystem physicsSystem;
	private SubSystem playerControllerSystem;
	private SubSystem aiSystem;
	private SubSystem aiHearingSystem;
	private AISightSystem aiSightSystem;
	private ShootingSystem shootingSystem;
	private BulletExpirationSystem bulletExpirationSystem;

	public Simulation() {
		initialise();
	}

	protected void initialise() {
				
		// create the entity manager
		this.entityManager = new EntityManager();
		
		// create the physics world
		this.b2World = new World(new Vector2(0, 0), true);
		this.b2World.setContactListener(new SimulationContactListener(this.entityManager));
		
		// The floor and the pathfinder
		this.floor = Floor.getInstance();
		
		// set up all systems
		this.physicsSystem = new PhysicsSystem(this.entityManager);
		this.playerControllerSystem = new PlayerControllerSystem(this.entityManager);
		this.aiSystem = new AISystem(this.entityManager, 0.1f, this.pathFinder);
		this.aiHearingSystem = new AIHearingSystem(this.entityManager);
		this.aiSightSystem = new AISightSystem(this.entityManager, 0.1f, this.b2World);
		this.shootingSystem = new ShootingSystem(this.entityManager, b2World);
		this.bulletExpirationSystem = new BulletExpirationSystem(this.entityManager);
		
		// load the level
		floor.setFloor(LevelLoader.load("C:/GameDev/Entity Unknown/EntityUnknown-android/assets/maps/map01.eul", this.b2World, this.entityManager));
		LevelFactory.load(this.b2World, this.entityManager);
		
	}

	@Override
	public void update(float delta) {
		// physics step
		this.b2World.step(delta, 4, 4);
		
		// run all systems
		this.aiSystem.process();
		this.playerControllerSystem.process();
		this.shootingSystem.process();
		this.physicsSystem.process();
		this.aiHearingSystem.process();
		this.aiSightSystem.process();
		this.bulletExpirationSystem.process();
	}
	
	/**
	 * Returns the entity manager
	 * @return the entity manager
	 */
	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	public World getPhysicsWorld() {
		return this.b2World;
	}
	
	public Floor getFloor() {
		return this.floor;
	}
	
	/**
	 * Method returns the system in charge of input processing. This is then sent to the input multiplexer
	 * which splits input between the simulation and the ui.
	 * 
	 * @return the system in charge of player control.
	 */
	public InputProcessor getInputProcessor() {
		return (InputProcessor)this.playerControllerSystem;
	}	
}