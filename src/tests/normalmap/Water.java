//package de.swagner.gdx.obj.normalmap;
//
//import java.io.InputStream;
//
//import com.badlogic.gdx.ApplicationListener;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input.Keys;
//import com.badlogic.gdx.graphics.GL10;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Mesh;
//import com.badlogic.gdx.graphics.PerspectiveCamera;
//import com.badlogic.gdx.graphics.Pixmap;
//import com.badlogic.gdx.graphics.Pixmap.Format;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.Texture.TextureFilter;
//import com.badlogic.gdx.graphics.VertexAttributes.Usage;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g3d.loaders.obj.ObjLoader;
//import com.badlogic.gdx.graphics.glutils.ShaderProgram;
//import com.badlogic.gdx.math.Matrix3;
//import com.badlogic.gdx.math.Matrix4;
//import com.badlogic.gdx.math.Vector3;
//
//import de.swagner.gdx.obj.normalmap.helper.ObjLoaderTan;
//import de.swagner.gdx.obj.normalmap.shader.NormalMapShader;
//import de.swagner.gdx.obj.normalmap.shader.WaterShader;
//
//public class Water implements ApplicationListener {
//	
//	private Mesh mesh;
//	private Texture textureDiff;
//	private Texture textureNorm;
//	private Matrix4 projection = new Matrix4();
//	private Matrix3 modelWorld = new Matrix3();
//	private Matrix4 view = new Matrix4();
//	private Matrix4 model = new Matrix4();
//	private Matrix4 model2 = new Matrix4();
//	private Matrix4 combined = new Matrix4();
//	private Vector3 yAxis = new Vector3(0, 1, 0).nor();
//	private Vector3 xAxis = new Vector3(1, 0, 0).nor();
//	private Vector3 light = new Vector3(-2f, 1f, 10f);
//	private float angle = 45;
//	private ShaderProgram shader;
//	private PerspectiveCamera camera;
//
//	@Override
//	public void create() {
//		try {
//			if (mesh == null) {
//				InputStream in = Gdx.files.internal("data/plane.obj").read();
//				mesh = ObjLoaderTan.loadObj(in);
//				mesh.getVertexAttribute(Usage.Position).alias = "a_vertex";
//				mesh.getVertexAttribute(Usage.Normal).alias = "a_normal";
//				mesh.getVertexAttribute(10).alias = "a_tangent";
//				mesh.getVertexAttribute(11).alias = "a_binormal";
//				mesh.getVertexAttribute(Usage.TextureCoordinates).alias = "a_texcoord0";
//				in.close();
//				createTexture();
//				shader = new ShaderProgram(WaterShader.mVertexShader, WaterShader.mFragmentShader);
//				if (shader.isCompiled() == false) {
//                    Gdx.app.log("ShaderTest", shader.getLog());
//                    System.exit(0);
//				}
//			}
//			camera = new PerspectiveCamera(45, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//			camera.translate(0.0f, 0.0f, 2.1f);
//			camera.lookAt(0, 0, 0);
//	        
//			camera.update();
//	        
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		Gdx.graphics.getGL20().glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//		
//		// Enable backface culling
//		Gdx.graphics.getGL20().glCullFace(GL20.GL_BACK);
//		Gdx.graphics.getGL20().glEnable(GL20.GL_CULL_FACE);
//
//		Gdx.graphics.getGL20().glEnable(GL20.GL_DEPTH_TEST);
//		Gdx.graphics.getGL20().glDepthFunc(GL20.GL_GEQUAL);
//		Gdx.graphics.getGL20().glClearDepthf(0.0f);
//		
//		Gdx.graphics.getGL20().glClearColor(0.0f, 0.0f, 0.0f, 1.0f);	
//		
//		Gdx.graphics.getGL20().glActiveTexture(GL20.GL_TEXTURE0);
//		textureDiff.bind();
//		Gdx.graphics.getGL20().glActiveTexture(GL20.GL_TEXTURE1);
//		textureNorm.bind();
//		
//	}
//	
//    private void createTexture() {
////        Pixmap pixmap = new Pixmap(256, 256, Format.RGB565);
////        boolean useRed = true;
////        for(int y = 0; y < 256; y+= 8) {
////                for(int x = 0; x < 256; x+= 8) {                        
////                        pixmap.setColor(useRed?1:0, 0, useRed?0:1, 1);
////                        pixmap.fillRectangle(x, y, 8, 8);
////                        useRed = !useRed;
////                }
////                useRed = !useRed;
////        }
////        textureDiff = new Texture(pixmap, true);
////        textureDiff.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
//        textureDiff = new Texture(Gdx.files.internal("data/Map-COL.jpg"),true);
//        textureDiff.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
//        textureNorm = new Texture(Gdx.files.internal("data/Infinite-Level_02_Tangent_SmoothUV.jpg"),true);
//        textureNorm.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
//}
//
//
//	@Override
//	public void dispose() {
//
//	}
//
//	@Override
//	public void pause() { 
//
//	}
//	
//	@Override
//	public void render() {
//		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | GL20.GL_STENCIL_BUFFER_BIT);
//	
//		angle -= Gdx.graphics.getDeltaTime() * 45f;
//		model.idt();
//		model2.setToRotation(xAxis, 90);
//		model.mul(model2);
//		model2.setToScaling(1f, 1f, 1f);
//		model.mul(model2);
//		
//		model2.setToTranslation(0.0f, 0.0f, 0.0f);
//		model.mul(model2);
//		
//				
//		modelWorld.setToRotation(angle);
//		
//        if(Gdx.input.isKeyPressed(Keys.KEYCODE_A))
//        	light.x += Gdx.graphics.getDeltaTime() * 10f;
//        if(Gdx.input.isKeyPressed(Keys.KEYCODE_D))
//        	light.x -= Gdx.graphics.getDeltaTime() * 10f;
//        if(Gdx.input.isKeyPressed(Keys.KEYCODE_W))
//        	light.y += Gdx.graphics.getDeltaTime() * 10f;
//        if(Gdx.input.isKeyPressed(Keys.KEYCODE_S))
//        	light.y -= Gdx.graphics.getDeltaTime() * 10f;
//        
//        if(Gdx.input.isKeyPressed(Keys.KEYCODE_DPAD_UP)) {
//        	camera.translate(0, Gdx.graphics.getDeltaTime() * 10f, 0);
//        }
//        if(Gdx.input.isKeyPressed(Keys.KEYCODE_DPAD_DOWN)) {
//        	camera.translate(0, -Gdx.graphics.getDeltaTime() * 10f, 0);
//        }
//        if(Gdx.input.isKeyPressed(Keys.KEYCODE_DPAD_LEFT)) {
//        	camera.translate( Gdx.graphics.getDeltaTime() * 10f,0, 0);
//        }
//        if(Gdx.input.isKeyPressed(Keys.KEYCODE_DPAD_RIGHT)) {
//        	camera.translate( -Gdx.graphics.getDeltaTime() * 10f,0, 0);
//        }
//        
//        
//        camera.update();
//		
//		// NormalMap
//		shader.begin();		
//		shader.setUniformi("s_normalTex", 0);		
//		shader.setUniformi("s_reflectionTex", 1);
////		shader.setUniformMatrix("u_matViewInverse",  camera.combined.inv());
//		shader.setUniformMatrix("u_matViewProjection",model);
//		shader.setUniformf("u_lightPosition", light.x, light.y, light.z);
//		shader.setUniformf("u_eyePosition", camera.position.x, camera.position.y, camera.position.z);
//
//		
////    	"uniform vec2 BumpTranslation0;\n" +
////    	"uniform vec2 BumpScale0;\n" +
////    	"uniform vec2 BumpTranslation1;\n" +
////    	"uniform vec2 BumpScale1;\n" +
////    	"\n" +
////    	"uniform float PerturbScale;\n" +
//    	
//    	
//		mesh.render(shader, GL20.GL_TRIANGLES);
//		shader.end();
//	}
//
//	@Override
//	public void resize(int width, int height) {
//		Gdx.graphics.getGL20().glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//		camera.viewportHeight = Gdx.graphics.getHeight();
//		camera.viewportWidth = Gdx.graphics.getWidth();
//	}
//
//	@Override
//	public void resume() {
//
//	}
//
//}
