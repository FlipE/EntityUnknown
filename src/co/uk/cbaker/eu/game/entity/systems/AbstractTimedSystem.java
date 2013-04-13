package co.uk.cbaker.eu.game.entity.systems;

import com.badlogic.gdx.Gdx;

import co.uk.cbaker.eu.game.entity.EntityManager;

public abstract class AbstractTimedSystem extends AbstractSystem {

	private float frequency;
	private float timer;
	
	public AbstractTimedSystem(EntityManager entityManager, float frequency) {
		super(entityManager);
		this.frequency = frequency;
		this.timer = frequency;
	}

	@Override
	public void process() {
		this.timer -= Gdx.graphics.getDeltaTime();
		if(timer <= 0) {
			this.timer = frequency;
			super.process();
		}		
	}
	
}