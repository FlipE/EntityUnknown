package co.uk.cbaker.eu.game.entity.components;

public class CameraFocus implements Component {

	private boolean isCameraFocus;

	public CameraFocus(boolean isCameraFocus) {
		super();
		this.isCameraFocus = isCameraFocus;
	}

	/**
	 * @return the isCameraFocus
	 */
	public boolean isCameraFocus() {
		return isCameraFocus;
	}

	/**
	 * @param isCameraFocus the isCameraFocus to set
	 */
	public void setCameraFocus(boolean isCameraFocus) {
		this.isCameraFocus = isCameraFocus;
	}	
	
}