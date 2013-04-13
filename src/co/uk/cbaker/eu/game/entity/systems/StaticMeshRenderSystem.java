package co.uk.cbaker.eu.game.entity.systems;

import co.uk.cbaker.eu.game.entity.EntityManager;
import co.uk.cbaker.eu.game.entity.components.Physics2D;
import co.uk.cbaker.eu.game.entity.components.RenderableStatic;
import co.uk.cbaker.eu.game.shaders.Shaders;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Body;

public class StaticMeshRenderSystem extends AbstractSystem {

	private RenderableStatic renderable;
	private StillModel model;
	private Physics2D physics;
	private Body body;
	private PerspectiveCamera camera;
	private Matrix4 transform;
	
	public StaticMeshRenderSystem(EntityManager entityManager, PerspectiveCamera camera) {
		super(entityManager);
		this.camera = camera;
		transform = new Matrix4();
	}

	@Override
	public void process() {
		super.entities = em.getAllEntitiesPossessingComponent(RenderableStatic.class);
		super.process();
	}
	
	@Override
	public void process(int entity) {
		physics = em.getComponent(entity, Physics2D.class);		
		renderable = em.getComponent(entity, RenderableStatic.class);
		
		body = physics.getBody();
		model = renderable.getModel();
		
		// setup the model matrix
		transform.set(camera.combined);
		transform.translate(physics.getX(), physics.getY(), 0);
		transform.rotate(0, 0, 1, (float)Math.toDegrees(body.getAngle()));
		
		ShaderProgram shader = Shaders.defaultShader();
		shader.setUniformMatrix("u_projView", transform);
		
		model.render(shader);
	}
}
