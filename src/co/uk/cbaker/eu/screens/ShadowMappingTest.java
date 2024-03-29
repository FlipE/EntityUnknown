/**
 * 
 */
package co.uk.cbaker.eu.screens;

import tests.PerspectiveCamController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.loaders.ModelLoaderRegistry;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * ShadowMappingTest.java
 * 
 * @author Chris B
 * @date 22 Feb 2013
 * @version 1.0
 * @param <T>
 */
public class ShadowMappingTest<T> extends GameScreen<T> {

	Stage ui;
	Skin skin;

	PerspectiveCamera cam;
	PerspectiveCamera lightCam;
	PerspectiveCamera currCam;
	Mesh plane;
	StillModel cube;
	ShaderProgram flatShader;
	ShaderProgram shadowGenShader;
	ShaderProgram shadowMapShader;
	ShaderProgram currShader;
	FrameBuffer shadowMap;
	InputMultiplexer multiplexer;
	PerspectiveCamController camController;
	Label fpsLabel;

	/**
	 * @param game
	 */
	public ShadowMappingTest(T game) {
		super(game);
		create();
	}

	public void create() {
		setupScene();
		setupShadowMap();
		setupUI();

		camController = new PerspectiveCamController(cam);
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(ui);
		multiplexer.addProcessor(camController);

		Gdx.input.setInputProcessor(multiplexer);
	}

	private void setupScene() {
		plane = new Mesh(true, 4, 4, new VertexAttribute(Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE));
		plane.setVertices(new float[] { -10, -1, 10, 10, -1, 10, 10, -1, -10, -10, -1, -10 });
		plane.setIndices(new short[] { 3, 2, 1, 0 });
		cube = ModelLoaderRegistry.loadStillModel(Gdx.files.internal("data/cube.obj"));

		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0, 0, 10);
		cam.lookAt(0, 0, 0);
		cam.update();
		currCam = cam;

		flatShader = new ShaderProgram(Gdx.files.internal("data/shaders/flat-vert.glsl").readString(), Gdx.files.internal("data/shaders/flat-frag.glsl").readString());
		if (!flatShader.isCompiled())
			throw new GdxRuntimeException("Couldn't compile flat shader: " + flatShader.getLog());
		currShader = flatShader;
	}

	private void setupShadowMap() {
		shadowMap = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		lightCam = new PerspectiveCamera(67, shadowMap.getWidth(), shadowMap.getHeight());
		lightCam.position.set(-10, 10, 0);
		lightCam.lookAt(0, 0, 0);
		lightCam.update();

		shadowGenShader = new ShaderProgram(Gdx.files.internal("data/shaders/shadowgen-vert.glsl").readString(), Gdx.files.internal("data/shaders/shadowgen-frag.glsl").readString());
		if (!shadowGenShader.isCompiled())
			throw new GdxRuntimeException("Couldn't compile shadow gen shader: " + shadowGenShader.getLog());

		shadowMapShader = new ShaderProgram(Gdx.files.internal("data/shaders/shadowmap-vert.glsl").readString(), Gdx.files.internal("data/shaders/shadowmap-frag.glsl").readString());
		if (!shadowMapShader.isCompiled())
			throw new GdxRuntimeException("Couldn't compile shadow map shader: " + shadowMapShader.getLog());
	}

	private void setupUI() {
		ui = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		skin = new Skin(Gdx.files.internal("uiskin.json"));

		Label label = new Label("Camera:", skin);
		final SelectBox cameraCombo = new SelectBox(new String[] { "Scene", "Light" }, skin);
		Label label2 = new Label("Shader", skin);
		final SelectBox shaderCombo = new SelectBox(new String[] { "flat", "shadow-gen", "shadow-map" }, skin);
		fpsLabel = new Label("fps:", skin);

		Table table = new Table();
		table.setSize(Gdx.graphics.getWidth(), 100);
		table.top().padTop(12);
		table.defaults().spaceRight(5);
		table.add(label);
		table.add(cameraCombo);
		table.add(label2);
		table.add(shaderCombo);
		table.add(fpsLabel);
		table.setY(ui.getHeight() - 100);
		ui.addActor(table);

		cameraCombo.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				if (cameraCombo.getSelectionIndex() == 0)
					currCam = cam;
				else
					currCam = lightCam;
				camController.cam = currCam;
			}
		});

		shaderCombo.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				int index = shaderCombo.getSelectionIndex();
				if (index == 0)
					currShader = flatShader;
				else if (index == 1)
					currShader = shadowGenShader;
				else
					currShader = shadowMapShader;
			}
		});
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

		if (currShader == flatShader) {
			currShader.begin();
			currShader.setUniformMatrix("u_projTrans", currCam.combined);

			currShader.setUniformf("u_color", 1, 0, 0, 1);
			plane.render(currShader, GL20.GL_TRIANGLE_FAN);

			currShader.setUniformf("u_color", 0, 1, 0, 1);
			cube.render(currShader);

			currShader.end();
		}
		else if (currShader == shadowGenShader) {
			currShader.begin();
			currShader.setUniformMatrix("u_projTrans", currCam.combined);

			plane.render(currShader, GL20.GL_TRIANGLE_FAN);
			cube.render(currShader);

			currShader.end();
		}
		else if (currShader == shadowMapShader) {
			shadowMap.begin();
			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
			Gdx.gl.glClearColor(0, 0, 0, 0);
			Gdx.gl.glEnable(GL20.GL_CULL_FACE);
			Gdx.gl.glCullFace(GL20.GL_FRONT);
			shadowGenShader.begin();
			shadowGenShader.setUniformMatrix("u_projTrans", lightCam.combined);
			plane.render(shadowGenShader, GL20.GL_TRIANGLE_FAN);
			cube.render(shadowGenShader);
			shadowGenShader.end();
			shadowMap.end();
			Gdx.gl.glDisable(GL20.GL_CULL_FACE);

			shadowMapShader.begin();
			shadowMap.getColorBufferTexture().bind();
			shadowMapShader.setUniformi("s_shadowMap", 0);
			shadowMapShader.setUniformMatrix("u_projTrans", cam.combined);
			shadowMapShader.setUniformMatrix("u_lightProjTrans", lightCam.combined);
			shadowMapShader.setUniformf("u_color", 1, 0, 0, 1);
			plane.render(shadowMapShader, GL20.GL_TRIANGLE_FAN);
			shadowMapShader.setUniformf("u_color", 0, 1, 0, 1);
			cube.render(shadowMapShader);
			shadowMapShader.end();

			ui.getSpriteBatch().begin();
			ui.getSpriteBatch().draw(shadowMap.getColorBufferTexture(), 0, 0, 100, 100);
			ui.getSpriteBatch().end();
		}

		fpsLabel.setText("fps: " + Gdx.graphics.getFramesPerSecond());
		ui.act();
		ui.draw();
		Table.drawDebug(ui);
	}

	@Override
	public void dispose() {
		ui.dispose();
		skin.dispose();
		plane.dispose();
		cube.dispose();
		flatShader.dispose();
		shadowGenShader.dispose();
		shadowMapShader.dispose();
		currShader.dispose();
		shadowMap.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.uk.cbaker.eu.screens.GameScreen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.uk.cbaker.eu.screens.GameScreen#pause()
	 */
	@Override
	public void pause() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.uk.cbaker.eu.screens.GameScreen#resume()
	 */
	@Override
	public void resume() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.uk.cbaker.eu.screens.GameScreen#show()
	 */
	@Override
	public void show() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.uk.cbaker.eu.screens.GameScreen#hide()
	 */
	@Override
	public void hide() {
	}

}