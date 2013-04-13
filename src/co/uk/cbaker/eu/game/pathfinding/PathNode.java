/**
 * 
 */
package co.uk.cbaker.eu.game.pathfinding;

import com.badlogic.gdx.utils.Array;

/**
 * PathNode.java
 *
 * @author 	Chris B
 * @date	8 Feb 2013
 * @version	1.0
 */
public class PathNode implements Comparable<PathNode> {

	private int x, y;
	private float gScore;
	private float hScore;
	private PathNode parent;
	private PathNode[][] nodes;
	
	/**
	 * @param x
	 * @param y
	 */
	public PathNode(int x, int y, PathNode[][] nodes) {
		super();
		this.x = x;
		this.y = y;
		this.nodes = nodes;
		// this is set to max so that it always evaluates greater than 
		// unless the node is already in the  
		this.gScore = Float.MAX_VALUE;
	}

	public Array<PathNode> getChildren() {
		Array<PathNode> children = new Array<PathNode>(8);
		
		for (int deltaX = -1; deltaX <= 1; deltaX++) {
			for (int deltaY = -1; deltaY <= 1; deltaY++) {
				// if not the current tile
				if (!((deltaX == 0) && (deltaY == 0))) {
					// if we're not allowing diaganol movement then only 
					// one of x or y can be set
					if ((deltaX != 0) && (deltaY != 0)) {
						continue;
					}
					children.add(nodes[this.x + deltaX][this.y + deltaY]);					
				}
				else {
					continue;
				}			
			}
		}
		
		return children;
	}
	
	/**
	 * Compare the two pathnodes based on the f(n) score which is g(n) + h(n)
	 * where g is the cost so far and h is the heuristic cost
	 */
	@Override
	public int compareTo(PathNode other) {
		float thisFScore = this.hScore + this.gScore;
		float otherFScore = other.hScore + other.gScore;
		
		if (thisFScore < otherFScore) {
			return -1;
		} else if (thisFScore > otherFScore) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof PathNode) {
			PathNode other = (PathNode) o;
			return this.x == other.x && this.y == other.y;
		}
		return false;		
	}
	
	@Override
	public int hashCode() {
		return this.x*this.y;
	}
	
	/**
	 * @return
	 */
	public float getGScore() {
		return this.gScore;
	}
	
	/**
	 * @param cost to visit this node
	 */
	public void setGScore(float cost) {
		this.gScore = cost;
	}

	/**
	 * @return
	 */
	public PathNode getParent() {
		return this.parent;
	}
	
	/**
	 * @param object
	 */
	public void setParent(PathNode parent) {
		this.parent = parent;
	}

	/**
	 * @return
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * @return
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * @param cost2
	 */
	public void setHScore(float cost) {
		this.hScore = cost;
	}

	/**
	 * @return
	 */
	public float getHScore() {
		return this.hScore;
	}		
	
}