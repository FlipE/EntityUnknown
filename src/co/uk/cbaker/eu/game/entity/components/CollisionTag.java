package co.uk.cbaker.eu.game.entity.components;

public class CollisionTag implements Component {

	private int collisionID;
	private int entity;
	
	public CollisionTag(int entity, int collisionID) {
		super();
		this.collisionID = collisionID;
		this.entity = entity;
	}

	/**
	 * @return the collisionID
	 */
	public int getCollisionID() {
		return collisionID;
	}

	/**
	 * @param collisionID the collisionID to set
	 */
	public void setCollisionID(int collisionID) {
		this.collisionID = collisionID;
	}

	/**
	 * @return the entity
	 */
	public int getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(int entity) {
		this.entity = entity;
	}
	
	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("CID: ");
		s.append(this.collisionID);
		s.append(" EID: ");
		s.append(this.entity);
		return s.toString();		
	}
	
}