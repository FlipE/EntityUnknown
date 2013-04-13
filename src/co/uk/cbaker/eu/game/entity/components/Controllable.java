package co.uk.cbaker.eu.game.entity.components;

public class Controllable implements Component {

	private boolean isControlFocus;

	public Controllable(boolean isControlFocus) {
		super();
		this.isControlFocus = isControlFocus;
	}

	/**
	 * @return the isControlFocus
	 */
	public boolean isControlFocus() {
		return isControlFocus;
	}

	/**
	 * @param isControlFocus the isControlFocus to set
	 */
	public void setControlFocus(boolean isControlFocus) {
		this.isControlFocus = isControlFocus;
	}
	
}