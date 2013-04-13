/**
 * 
 */
package co.uk.cbaker.eu.game.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Shaders.java
 *
 * @author 	Chris B
 * @date	22 Feb 2013
 * @version	1.0
 */
public class Shaders {

	private static ShaderProgram shadowMapShader;
	private static ShaderProgram shadowGenShader;
	private static ShaderProgram flatShader;
	private static ShaderProgram defaultShader;
	
	public static ShaderProgram shadowMapShader() {
		
		if(shadowMapShader == null) {
			shadowMapShader = new ShaderProgram(Gdx.files.internal("data/shaders/shadowmap-vert.glsl").readString(), Gdx.files.internal("data/shaders/shadowmap-frag.glsl").readString());
			if (!shadowMapShader.isCompiled())
				throw new GdxRuntimeException("Couldn't compile shadow map shader: " + shadowMapShader.getLog());
		}
		
		return shadowMapShader;
	}

	/**
	 * @return
	 */
	public static ShaderProgram shadowGenShader() {
		if(shadowGenShader == null) {
			shadowGenShader = new ShaderProgram(Gdx.files.internal("data/shaders/shadowgen-vert.glsl").readString(), Gdx.files.internal("data/shaders/shadowgen-frag.glsl").readString());
			if (!shadowGenShader.isCompiled())
				throw new GdxRuntimeException("Couldn't compile shadow gen shader: " + shadowGenShader.getLog());
		}
		
		return shadowGenShader;
	}

	public static ShaderProgram flatShader() {
		
		if(flatShader == null) {
			flatShader = new ShaderProgram(Gdx.files.internal("data/shaders/flat-vert.glsl").readString(), Gdx.files.internal("data/shaders/flat-frag.glsl").readString());
			if (!flatShader.isCompiled())
				throw new GdxRuntimeException("Couldn't compile flat shader: " + flatShader.getLog());
		}
		
		return flatShader;
	}
	
	public static ShaderProgram defaultShader() {
		
		if(defaultShader == null) {
			defaultShader = new ShaderProgram(Gdx.files.internal("data/shaders/shader-vs.glsl").readString(), Gdx.files.internal("data/shaders/shader-fs.glsl").readString());
			if (!defaultShader.isCompiled())
				throw new GdxRuntimeException("Couldn't compile default shader: " + flatShader.getLog());
		}
		
		return defaultShader;
	}
	
}