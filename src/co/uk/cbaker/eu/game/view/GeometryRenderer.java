package co.uk.cbaker.eu.game.view;

import co.uk.cbaker.eu.game.Assets;
import co.uk.cbaker.eu.game.model.Floor;
import co.uk.cbaker.eu.game.model.FloorCell;
import co.uk.cbaker.eu.game.model.Quad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class GeometryRenderer {
	
	private PerspectiveCamera camera;

	public GeometryRenderer(PerspectiveCamera camera) {
		this.camera = camera;
	}
	
	/**
	 * Render all cells in the current screen bounds
	 * @param floor
	 * @param shader 
	 */
	public void render(Floor floor, ShaderProgram shader) {
		
		FloorCell[][] floorCells = floor.getFloor();
		
		int lowerX = floor.getScreenLowerXBound(camera.position.x, camera.viewportWidth);
		int lowerY = floor.getScreenLowerYBound(camera.position.y, camera.viewportHeight);
		int upperX = floor.getScreenUpperXBound(camera.position.x, camera.viewportWidth);
		int upperY = floor.getScreenUpperYBound(camera.position.y, camera.viewportHeight);
				
		try {
			for(int x = lowerX; x < upperX; x+=1) {
				for(int y = lowerY; y < upperY; y+=1) {
					// this can throw out of bounds if the floor is empty
					Quad[] quads = floorCells[x][y].getQuads();
					
					for(int i = 0; i < quads.length; i += 1) {
						Quad quad = quads[i];
						if(quad != null) {
							int textureIndex = floorCells[x][y].getTexture(i);							
							Assets.textures.get(textureIndex).bind();
							quad.getMesh().render(shader, GL20.GL_TRIANGLE_STRIP, 0, 4);						
						}
					}
				}
			}
		}
		catch(Exception e) {
			// The floor has gone wrong! Holy cockbags batman!
		}
	}	
}