/**
 * 
 */
package co.uk.cbaker.eu.screens;

import co.uk.cbaker.eu.game.shaders.Shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.loaders.ModelLoaderRegistry;
import com.badlogic.gdx.graphics.g3d.materials.Material;
import com.badlogic.gdx.graphics.g3d.materials.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.model.keyframe.KeyframedAnimation;
import com.badlogic.gdx.graphics.g3d.model.keyframe.KeyframedModel;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

/**
 * StillModelTest.java
 *
 * @author 	Chris B
 * @date	25 Feb 2013
 * @version	1.0
 */
public class KeyframedModelTest <T> extends GameScreen<T> {

	private KeyframedModel model;
	private PerspectiveCamera cam;
	private ShaderProgram shader;
	private Matrix4 transform;
	private Vector3 position;
	private Quaternion rotation;
	private float time;
	private float animationSpeed;
	
	public KeyframedModelTest(T game) {
		super(game);
		this.initialise();
	}

	/**
	 * Load a model
	 * Apply a material
	 * Create a camera
	 * Load a shader
	 */
	private void initialise() {
		
		// load a model
		model = ModelLoaderRegistry.loadKeyframedModel(Gdx.files.internal("player.md2"));

		// apply a material
		Texture texture = new Texture(Gdx.files.internal("256_Female_Green_DIFF_md2.png"));
		Material material = new Material();
		material.addAttribute(new TextureAttribute(texture, 0, "u_texture"));
		model.setMaterial(material);
		
		// create a camera
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0, 0, 20);
		cam.lookAt(0, 0, 0);
		cam.update();
		
		// load a shader
		shader = Shaders.defaultShader();
		
		transform = new Matrix4();
		position = new Vector3(0, 0, 0);
		rotation = new Quaternion(0, 0, 0, 0);
		animationSpeed = 5;
	}
	
	/* (non-Javadoc)
	 * @see co.uk.cbaker.eu.screens.GameScreen#render(float)
	 */
	@Override
	public void render(float delta) {
		
		// update model animation
		time += delta * animationSpeed;
		if (time > model.getAnimation("all").totalDuration) {
			time = time % model.getAnimation("all").totalDuration;
		}
		model.setAnimation("all", time, true);
		
		// General opengl render stuff
		GL20 gl = Gdx.graphics.getGL20();
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
		//gl.glEnable(GL20.GL_CULL_FACE);
		//gl.glCullFace(GL20.GL_FRONT);
		gl.glEnable(GL20.GL_DEPTH_TEST);
		gl.glEnable(GL20.GL_TEXTURE_2D);
		gl.glActiveTexture(GL20.GL_TEXTURE0);

		transform.set(cam.combined);
		transform.translate(position);
		transform.rotate(rotation);
		
		shader.begin();
		shader.setUniformi("u_texture", 0);
		shader.setUniformMatrix("u_projView", transform);
		
		//shader.setUniformMatrix("u_projTrans", cam.combined);
		//shader.setUniformf("u_color", 1, 0, 0, 1);
		
		// render the model
		model.render(shader);
		
		shader.end();
		
	}

	/* (non-Javadoc)
	 * @see co.uk.cbaker.eu.screens.GameScreen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
	}

	/* (non-Javadoc)
	 * @see co.uk.cbaker.eu.screens.GameScreen#pause()
	 */
	@Override
	public void pause() {
	}

	/* (non-Javadoc)
	 * @see co.uk.cbaker.eu.screens.GameScreen#resume()
	 */
	@Override
	public void resume() {
	}

	/* (non-Javadoc)
	 * @see co.uk.cbaker.eu.screens.GameScreen#show()
	 */
	@Override
	public void show() {
	}

	/* (non-Javadoc)
	 * @see co.uk.cbaker.eu.screens.GameScreen#hide()
	 */
	@Override
	public void hide() {
	}

	/* (non-Javadoc)
	 * @see co.uk.cbaker.eu.screens.GameScreen#dispose()
	 */
	@Override
	public void dispose() {
	}

}
