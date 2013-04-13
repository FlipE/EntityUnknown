package co.uk.cbaker.eu.game.entity.systems;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import co.uk.cbaker.eu.game.entity.EntityManager;

public abstract class AbstractSystem implements SubSystem {

	protected Set<Integer> entities;
	protected EntityManager em;
	private Iterator<Integer> it;
	
	public AbstractSystem(EntityManager entityManager) {
		this.em = entityManager;
		this.entities = new HashSet<Integer>();
	}
	
	@Override
	public void process() {
		for(it = entities.iterator(); it.hasNext();) {
			process(it.next());
		}
	}
	
	@Override
	public abstract void process(int entity);
	
}