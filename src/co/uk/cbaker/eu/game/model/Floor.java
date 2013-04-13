package co.uk.cbaker.eu.game.model;

import co.uk.cbaker.eu.game.Config;
import co.uk.cbaker.eu.game.pathfinding.AStar;
import co.uk.cbaker.eu.game.pathfinding.Path;
import co.uk.cbaker.eu.game.pathfinding.heuristics.ClosestSquaredHeuristic;

/**
 * Holds the array of floor cells which makes up the world. this is used for
 * rendering and simulation, including ai navigation. The floor cells can hold
 * all objects in the world. The world is a 2D matrix of floor cells n x m; 0, 0 is bottom left
 * n, m is top right
 * 
 * Floor.java
 *
 * @author 	Chris B
 * @date	1 Feb 2013
 * @version	1.0
 */
public class Floor {
	
	private static final Floor instance = new Floor();
	private FloorCell[][] floor;
	private AStar pathFinder;
	
	private Floor() {
		super();
		this.floor = new FloorCell[1][1];
		this.pathFinder = new AStar(this, new ClosestSquaredHeuristic());
	}

	/**
	 * This method takes as input world coordinates, converts them to cell coordinates
	 * and passes them to the path finder algorithm. a path is returned.
	 * 
	 * @param startX
	 * @param startY
	 * @param targetX
	 * @param targetY
	 * @return
	 */
	public Path findPath(int startX, int startY, int targetX, int targetY) {
		
		startX = (int) (startX / Config.SCALE);
		startY = (int) (startY / Config.SCALE);
		targetX = (int) (targetX / Config.SCALE);
		targetY = (int) (targetY / Config.SCALE);
		
		return this.pathFinder.findPath(startX, startY, targetX, targetY);
	}
	
	/**
	 * @param gridX
	 * @param gridY
	 * @return 
	 */
	public boolean isInBounds(int gridX, int gridY) {
		return gridX >= 0 && gridX < floor.length	&& gridY >= 0 && gridY < floor[0].length;
	}
	
	/**
	 * Return the left most column number.
	 * Divide the camera position by the scale to get which floor cell the camera is in
	 * 
	 * @param cameraX 
	 * @param screenWidth
	 * @return
	 */
	public int getScreenLowerXBound(float cameraX, float screenWidth) {
		return Math.max((int)((cameraX / Config.SCALE) - (screenWidth/2)), 0);
	}
	
	public int getScreenLowerYBound(float cameraY, float screenHeight) {
		return Math.max((int)((cameraY / Config.SCALE) - (screenHeight/2)), 0);
	}
	
	public int getScreenUpperXBound(float cameraX, float screenWidth) {
		return Math.min((int)((cameraX / Config.SCALE) + (screenWidth/2)), floor.length);
	}
	
	public int getScreenUpperYBound(float cameraY, float screenHeight) {
		return Math.min((int)((cameraY / Config.SCALE) + (screenHeight/2)), floor[0].length );
	}
	
	/**
	 * @return the floor cells
	 */
	public FloorCell[][] getFloor() {
		return floor;
	}
	
	/**
	 * Set the floor cells
	 * 
	 * @param floor
	 */
	public void setFloor(FloorCell[][] floor) {
		this.floor = floor;
		this.pathFinder.initialise();
	}

	/**
	 * @return the floor instance
	 */
	public static Floor getInstance() {
		return instance;
	}
	
	/**
	 * Check to see if the cell at the given coordinates is blocked or not
	 * 
	 * @param x the x coordinate of the cell to check
	 * @param y the y coordinate of the cell to check
	 * @return true if the cell is blocked, false otherwise
	 */
	public boolean isBlocked(int x, int y) {
		return this.floor[x][y].getType() != Config.FLOOR_CELL;
	}
	
	public int getWidth() {
		return this.floor.length;
	}
	
	public int getHeight() {
		return this.floor[0].length;
	}
	
	@Override
	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		
		for (int y = 0; y < floor[0].length; y += 1) {
			for (int x = 0; x < floor.length; x += 1) {			
				sb.append(floor[x][y].getType());
				sb.append(" ");
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	
}