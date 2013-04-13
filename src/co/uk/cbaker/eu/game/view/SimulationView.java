package co.uk.cbaker.eu.game.view;

import co.uk.cbaker.eu.game.Config;
import co.uk.cbaker.eu.game.entity.EntityManager;
import co.uk.cbaker.eu.game.entity.systems.BulletRenderSystem;
import co.uk.cbaker.eu.game.entity.systems.CameraFocusSystem;
import co.uk.cbaker.eu.game.entity.systems.StaticMeshRenderSystem;
import co.uk.cbaker.eu.game.entity.systems.SubSystem;
import co.uk.cbaker.eu.game.model.Floor;
import co.uk.cbaker.eu.game.model.Simulation;
import co.uk.cbaker.eu.game.shaders.Shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class SimulationView implements View {

	//private Simulation simulation;
	private EntityManager entityManager;
	private World physicsWorld;
	private Floor floor;

	// ui
	private SpriteBatch batch;
	private Stage stage;
	private HUDView hud;

	private SubSystem renderSystem;
	private SubSystem bulletRenderSystem;
	private SubSystem cameraFocusSystem;

	private PerspectiveCamera cam;
	private Box2DDebugRenderer renderer;
	private GeometryRenderer geometryRenderer;
	private ShaderProgram shader;

	public SimulationView(Simulation simulation) {
		//this.simulation = simulation;
		this.entityManager = simulation.getEntityManager();
		this.physicsWorld = simulation.getPhysicsWorld();
		this.floor = simulation.getFloor();
		initialise();
	}

	private void initialise() {
		// create a camera
		float aspectRatio = Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
		cam = new PerspectiveCamera(45, (Gdx.graphics.getWidth() / Config.PIXELS_PER_METER) * aspectRatio, (Gdx.graphics.getHeight() / Config.PIXELS_PER_METER) * aspectRatio);

		// ui
		//batch = new SpriteBatch();
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		hud = new HUDView(stage, stage.getSpriteBatch(), this.entityManager);

		// shaders
		shader = Shaders.defaultShader();

		// setup all view systems
		this.renderSystem = new StaticMeshRenderSystem(this.entityManager, cam);
		this.cameraFocusSystem = new CameraFocusSystem(this.entityManager, cam);
		this.bulletRenderSystem = new BulletRenderSystem(this.entityManager, cam);

		// box2D debug render
		renderer = new Box2DDebugRenderer();
		geometryRenderer = new GeometryRenderer(cam);
	}

	// some light stuff
	// RGBA 0 - 1
	float[] lightColor = { 0.3f, 0.4f, 0.4f, 0 };
	// xyz?
	float[] lightPosition = { -10, 10, 10, 0 };

	@Override
	public void render() {

		// General opengl render stuff
		GL20 gl = Gdx.graphics.getGL20();
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
		gl.glEnable(GL20.GL_CULL_FACE);
		gl.glEnable(GL20.GL_DEPTH_TEST);
		gl.glEnable(GL20.GL_TEXTURE_2D);
		gl.glActiveTexture(GL20.GL_TEXTURE0);

		// update the camera position
		this.cameraFocusSystem.process();
		
		shader.begin();
		shader.setUniformi("u_texture", 0);
		shader.setUniformMatrix("u_projView", cam.combined);
		
		// render the world
		gl.glCullFace(GL20.GL_FRONT);
		this.geometryRenderer.render(this.floor, shader);
		
		// render static entities
		gl.glCullFace(GL20.GL_BACK);
		this.renderSystem.process();
		
		shader.end();
		
		// disable this here to allow hud to render properly
		gl.glDisable(GL20.GL_CULL_FACE);
		
		this.bulletRenderSystem.process();
		this.renderer.render(physicsWorld, cam.combined);	
		this.hud.render();
	}

	public InputProcessor getInputProcessor() {
		return stage;
	}

}