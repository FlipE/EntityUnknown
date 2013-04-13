package co.uk.cbaker.eu.game.pathfinding.heuristics;

/**
 * A heuristic that drives the search based on the Manhattan distance
 * between the current location and the target
 * 
 *
 * @author 	Chris B
 * @date	8 Feb 2013
 * @version	1.0
 */
public class ManhattanHeuristic implements AStarHeuristic {

	/**
	 * @see co.uk.cbaker.eu.game.pathfinding.heuristics.AStarHeuristic#getCost(int, int, int, int)
	 */
	public float getCost(int x, int y, int tx, int ty) {
		return (Math.abs(x - tx) + Math.abs(y - ty));
	}

}
