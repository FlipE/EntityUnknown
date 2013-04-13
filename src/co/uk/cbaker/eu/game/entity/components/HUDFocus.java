package co.uk.cbaker.eu.game.entity.components;

public class HUDFocus implements Component {

	private boolean isHUDFocus;

	public HUDFocus(boolean isHUDFocus) {
		super();
		this.isHUDFocus = isHUDFocus;
	}

	/**
	 * @return the isCameraFocus
	 */
	public boolean isHUDFocus() {
		return isHUDFocus;
	}

	/**
	 * @param isCameraFocus the isCameraFocus to set
	 */
	public void setCameraFocus(boolean isHUDFocus) {
		this.isHUDFocus = isHUDFocus;
	}
	
}