package co.uk.cbaker.eu.game.pathfinding;

import co.uk.cbaker.eu.game.Config;

import com.badlogic.gdx.utils.Array;

/**
 * A path determined by some path finding algorithm. A series of steps from
 * the starting location to the target location. This includes a step for the
 * initial location. Paths are stored and read in reverse order. This is a 
 * by-product of the way they are created, searching from source to target
 * and then recreating from target back to source
 * 
 */
public class Path {

	/** The list of steps building up this path */
	private Array<Step> steps;
	private int stepIndex;
	private boolean atEnd;

	/**
	 * Create an empty path
	 */
	public Path(PathNode node) {
		super();
		steps = new Array<Step>();
		// build the path starting with the destination and working backward through the parents
		while (node.getParent() != null) {
			this.steps.add(new Step(node.getParent().getX(), node.getParent().getY()));
			// reset the nodes parent to null
			// this is not encapsulated in the path finder?
			PathNode parent = node.getParent();
			node.setParent(null);
			node = parent;
		}

		// start at last item and work back to 0
		this.stepIndex = this.steps.size - 1;
		this.atEnd = false;
	}

	/**
	 * @return the stepIndex
	 */
	public int getStepIndex() {
		return stepIndex;
	}
	
	/**
	 * Returns the world x coordinate of the current step
	 * 
	 * @return
	 */
	public float getWorldX() {		
		return this.steps.get(stepIndex).worldX;
	}
	
	/**
	 * Returns the world x coordinate of the current step
	 * 
	 * @return
	 */
	public float getWorldY() {		
		return this.steps.get(stepIndex).worldY;
	}

	/**
	 * Move the path onto the next step
	 */
	public void nextStep() {
		if(!this.atEnd) {
			this.stepIndex -= 1;
			if(this.stepIndex <= 0) {
				this.stepIndex = 0;
				this.atEnd = true;
			}
		}
	}
	
	public boolean atEnd() {
		return this.atEnd;
	}
	
	/**
	 * Get the length of the path, i.e. the number of steps
	 * 
	 * @return The number of steps in this path
	 */
	public int getLength() {
		return this.steps.size;
	}

	/**
	 * Get the step at a given index in the path
	 * 
	 * @param index The index of the step to retrieve. Note this should
	 *            be >= 0 and < getLength();
	 * @return The step information, the position on the map.
	 */
	public Step getStep(int index) {
		return this.steps.get(index);
	}

	@Override
	public String toString() {

		StringBuffer s = new StringBuffer();

		s.append("Length: ");
		s.append(this.steps.size);
		s.append("\n");

		for (int i = this.steps.size - 1; i >= 0; i -= 1) {
			s.append("(");
			s.append(this.steps.get(i).cellX);
			s.append(", ");
			s.append(this.steps.get(i).cellY);
			s.append(")");
			if (i < this.steps.size - 1) {
				s.append(" -> ");
			}
		}

		return s.toString();
	}

	/**
	 * A single step in a path. just contains spatial information 
	 *
	 * @author 	Chris B
	 * @date	10 Feb 2013
	 * @version	1.0
	 */
	private class Step {
		float cellX, cellY;
		float worldX, worldY;
		
		public Step(float x, float y) {
			this.cellX = x;
			this.cellY = y;
			this.worldX = (x * Config.SCALE) + Config.SCALE / 2;
			this.worldY = (y * Config.SCALE) + Config.SCALE / 2;
		}
	}
	
}
