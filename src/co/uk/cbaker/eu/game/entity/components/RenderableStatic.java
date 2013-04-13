package co.uk.cbaker.eu.game.entity.components;

import com.badlogic.gdx.graphics.g3d.model.still.StillModel;

public class RenderableStatic implements Component {

	private StillModel model;
	
	public RenderableStatic(StillModel model){		
		this.model = model;
	}

	/**
	 * Returns the currently assigned model
	 * 
	 * @return the model
	 */
	public StillModel getModel() {
		return model;
	}

	/**
	 * Sets a new model
	 * 
	 * @param model the model to set
	 */
	public void setMesh(StillModel model) {
		this.model = model;
	}	
	
}