package co.uk.cbaker.eu.game.model;

public class FloorCell {

	private float x, y;
	private int type;
	
	/**
	 * Each cell is made up of 6 possible quads. the quads all face inwards
	 * apart from the ceiling which is inverted because of the top down
	 * perspective the game uses.
	 * 
	 * 0 = Floor
	 * 1 = Ceiling
	 * 2 = South Facing
	 * 3 = West Facing
	 * 4 = North Facing
	 * 5 = East Facing
	 */
	private final Quad[] quads = new Quad[6];
	private final int[] textures = new int[6];
	
	public FloorCell(float x, float y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}

	public void setQuad(int index, Quad quad) {
		if(index >= 0 && index < this.quads.length) {
			this.quads[index] = quad;
		}
	}
	
	public Quad[] getQuads() {
		return this.quads;
	}
	
	public int getType() {
		return this.type;
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	/**
	 * Given an index returns the texture id for that quad
	 * @param index a number 0-5
	 * @return the texture id of the quad at index
	 */
	public int getTexture(int index) {
		int texture = 0; // default
		
		if(index >= 0 && index < textures.length) {
			texture = textures[index];
		}
		
		return texture;
	}
	
	public void setTexture(int index, int texture) {
		if(index >= 0 && index < this.textures.length) {
			this.textures[index] = texture;
		}
	}

	/**
	 * @return
	 */
	public int[] getTextures() {
		return this.textures;
	}
}