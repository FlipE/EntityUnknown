package tests.normalmap;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.loaders.obj.ObjLoader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import tests.normalmap.shader.ReflectionShader;

public class ReflectionMapping implements ApplicationListener {
	
	private Mesh mesh;
	private Texture texture;
	private Matrix3 modelWorld = new Matrix3();
	private Matrix4 model = new Matrix4();
	private Matrix4 model2 = new Matrix4();
	private Vector3 zAxis = new Vector3(0, 0, 1).nor();
	private Vector3 yAxis = new Vector3(0, 1, 0).nor();
	private Vector3 xAxis = new Vector3(1, 0, 0).nor();
	private float angle = 45;
	private ShaderProgram shader;
	private PerspectiveCamera camera;

	@Override
	public void create() {
		try {
			if (mesh == null) {
				mesh = ObjLoader.loadObj(Gdx.files.internal("data/ape.obj").read());
				
				mesh.getVertexAttribute(Usage.Position).alias = "inVertex";
				mesh.getVertexAttribute(Usage.Normal).alias = "inNormal";

				createTexture();
				shader = new ShaderProgram(ReflectionShader.mVertexShader, ReflectionShader.mFragmentShader);
				if (shader.isCompiled() == false) {
                    Gdx.app.log("ShaderTest", shader.getLog());
                    System.exit(0);
				}
			}
			camera = new PerspectiveCamera(45, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			camera.translate(0.0f, 0.0f, 3.0f);
			camera.lookAt(0, 0, 0);
	        
			camera.update();
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Gdx.graphics.getGL20().glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		// Enable backface culling
		Gdx.graphics.getGL20().glCullFace(GL20.GL_BACK);
		Gdx.graphics.getGL20().glEnable(GL20.GL_CULL_FACE);

		Gdx.graphics.getGL20().glEnable(GL20.GL_DEPTH_TEST);
		
		Gdx.graphics.getGL20().glClearColor(0.9f, 0.9f, 0.9f, 1.0f);	
		
		Gdx.graphics.getGL20().glActiveTexture(GL20.GL_TEXTURE0);
		texture.bind();
		
	}
	
    private void createTexture() {
    	texture = new Texture(Gdx.files.internal("data/cubemap1.png"),true);
    	texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
}


	@Override
	public void dispose() {

	}

	@Override
	public void pause() { 

	}
	
	@Override
	public void render() {
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | GL20.GL_STENCIL_BUFFER_BIT);
	
		angle -= Gdx.graphics.getDeltaTime() * 45f;
		model.idt();
		model2.setToRotation(xAxis, 90);
		model.mul(model2);
		model2.setToRotation(zAxis, angle);
		model.mul(model2);
					
		modelWorld.setToRotation(angle);        
        camera.update();				

		// Reflection Shader
		shader.begin();
		texture.bind();
		shader.setUniformf("s2DMap", 0);
		shader.setUniformMatrix("MVPMatrix", camera.combined.mul(model));
		shader.setUniformMatrix("ModelWorld", modelWorld);
		shader.setUniformf("EyePosModel", camera.position.x,camera.position.y,camera.position.z);
		mesh.render(shader, GL20.GL_TRIANGLES);
		shader.end();
		
	}

	@Override
	public void resize(int width, int height) {
		Gdx.graphics.getGL20().glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.viewportHeight = Gdx.graphics.getHeight();
		camera.viewportWidth = Gdx.graphics.getWidth();
	}

	@Override
	public void resume() {

	}

}
