package co.uk.cbaker.eu.game.entity.components;

public class AIVision implements Component {

	private boolean targetInSight;

	public AIVision() {
		super();
	}

	/**
	 * @return the targetInSight
	 */
	public boolean isTargetInSight() {
		return targetInSight;
	}

	/**
	 * @param targetInSight the targetInSight to set
	 */
	public void setTargetInSight(boolean targetInSight) {
		this.targetInSight = targetInSight;
	}
	
	
	
}