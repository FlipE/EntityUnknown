package co.uk.cbaker.eu.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Assets {

	// keeping track of texture names because they are not checking for equality properly
	public static final ArrayList<Texture> textures = new ArrayList<Texture>();
	
	// TODO: load these textures from xml
	public static void load() {
		loadTexture("ceiling.png");
		loadTexture("ceiling.png");
		loadTexture("floor.png");
		loadTexture("wall.png");
	}
	
	/**
	 * Load a texture and add it to the texture list given a filepath
	 * 
	 * @param filePath
	 * @throws Exception
	 */
	public static void loadTexture(String fileName) {
		Texture texture = new Texture(Gdx.files.internal(fileName), true);
		textures.add(texture);		
	}
}