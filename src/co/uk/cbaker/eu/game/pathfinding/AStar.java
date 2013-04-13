package co.uk.cbaker.eu.game.pathfinding;

import java.util.HashSet;
import java.util.PriorityQueue;

import com.badlogic.gdx.utils.Array;

import co.uk.cbaker.eu.game.model.Floor;
import co.uk.cbaker.eu.game.pathfinding.heuristics.AStarHeuristic;

/**
 * AStar.java
 * 
 * @author Chris B
 * @date 8 Feb 2013
 * @version 1.0
 */
public class AStar {

	// closed set of cells already searched.
	private HashSet<PathNode> closed = new HashSet<PathNode>(16);

	// open set of cells to be searched. These have been found and added to the queue
	private PriorityQueue<PathNode> open = new PriorityQueue<PathNode>(16);

	// The heuristic we're applying to determine which nodes to search first
	private AStarHeuristic heuristic;

	// the tile based map being searched
	private Floor floor;

	// the complete set of nodes across the map
	private PathNode[][] nodes;

	public AStar(Floor floor, AStarHeuristic heuristic) {
		this.floor = floor;
		this.heuristic = heuristic;
		this.initialise();
	}

	/**
	 * 
	 */
	public void initialise() {
		nodes = new PathNode[floor.getWidth()][floor.getHeight()];
		for (int x = 0; x < floor.getWidth(); x+=1) {
			for (int y = 0; y < floor.getHeight(); y+=1) {
				nodes[x][y] = new PathNode(x, y, nodes);
			}
		}
	}
	
	public Path findPath(int startX, int startY, int targetX, int targetY) {

		// easy first check, if the destination is blocked, we can't get there
		if (this.floor.isBlocked(targetX, targetY)) {
			return null;
		}

		// declare a node which will be overwritten for each child of a visited path node
		PathNode child;
		
		// clear open and closed lists
		this.closed.clear();
		this.open.clear();
		
		// set the current node as the start node
		PathNode current = nodes[startX][startY];
		
		// the cost of the start node is 0 because we are already there
		current.setGScore(0);
		
		// poor little orphan start node
		current.setParent(null);
		
		// add the starting location to the open list
		this.open.add(current);
		
		// search whilst the open list remains populated with delicous fresh nodes
		while(!this.open.isEmpty()) {
			try {
				// get the best candidate from the priority queue
				current = this.open.remove();
				
				// if this is the target then we are finished searching!
				// we do this here instead of in the while condition to avoid searching it's children
				if(current.equals(nodes[targetX][targetY])) {
					break;
				}
				
				// we don't need to look at this node again
				this.closed.add(current);
				
				// get all the children of this node
				Array<PathNode> children = current.getChildren();
				for(int i = 0; i < children.size; i+=1) {
					
					// grab the next child
					child = children.get(i);
					
					// if child is not in the closed set and the cell is not blocked
					if(!this.closed.contains(child) && !this.floor.isBlocked(child.getX(), child.getY())) {
						
						// set the child's parent 
						child.setParent(current);
						
						float gScore = current.getGScore() + child.getGScore();
						float hScore = this.heuristic.getCost(child.getX(), child.getY(), targetX, targetY);
						
						if(child.getGScore() > gScore) {
							// set the child's cost scores
							child.setGScore(gScore); // g(n)
							child.setHScore(hScore); // h(n)
						}
						
						// if the child is not in the openlist then add it
						if(!this.open.contains(child)) {
							this.open.add(child);													
						}
						
					}
				}
								
			}
			catch(Exception e) {
				// the list was empty! this should not happen because of the while condition
				System.out.println(e.getStackTrace());
				e.printStackTrace();
			}
			
		}
		
		// if we didn't find a path the target node will have a null parent
		if (nodes[targetX][targetY].getParent() == null) {
			return null;
		}
		
		// at this point current should be the target cell and we want to reconstruct the path
		return new Path(current);
	}
}