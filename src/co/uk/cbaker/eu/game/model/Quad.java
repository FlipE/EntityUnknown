package co.uk.cbaker.eu.game.model;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

public class Quad {

	private Mesh mesh;
	private float[] vertices;
	
	public Quad(float[] vertices) {
		this.vertices = vertices;
		// property names must match the shader else they are ignored
		this.mesh = new Mesh(true, vertices.length, 4, new VertexAttribute(Usage.Position, 3, "a_position"), new VertexAttribute(Usage.ColorPacked, 4, "a_color"),  new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoord0"));
		this.mesh.setVertices(vertices);
		this.mesh.setIndices(new short[] {0, 1, 2, 3});
		setTextureMap(1, 1);
	}
	
	public float[] getVertices() {
		return this.vertices;
	}
	
	public Mesh getMesh() {
		return this.mesh;
	}
	
	public void setTextureMap(int segment, int numSegments) {
		
		// bl4,5 tl10,11 br16,17 tr22,23
		// uv is flipped
		this.vertices[10] = 1.0F;		this.vertices[22] = 0.0F;
		this.vertices[11] = 0.0F;		this.vertices[23] = 0.0F;
		
		this.vertices[4] = 1.0F;		this.vertices[16] = 0.0F;
		this.vertices[5] = 1.0F;		this.vertices[17] = 1.0F;
		
		this.mesh.setVertices(this.vertices);
	}	
	
}