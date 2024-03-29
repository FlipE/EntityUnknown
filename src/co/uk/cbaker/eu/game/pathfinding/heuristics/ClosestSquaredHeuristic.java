package co.uk.cbaker.eu.game.pathfinding.heuristics;

/**
 * A heuristic that uses the tile that is closest to the target
 * as the next best tile. In this case the sqrt is removed
 * and the distance squared is used instead
 * 
 * @author Kevin Glass
 */
public class ClosestSquaredHeuristic implements AStarHeuristic {

	/**
	 * @see co.uk.cbaker.eu.game.pathfinding.heuristics.AStarHeuristic#getCost(co.uk.cbaker.eu.game.pathfinding.TileBasedMap, co.uk.cbaker.eu.game.pathfinding.Mover, int, int, int, int)
	 */
	public float getCost(int x, int y, int tx, int ty) {		
		float dx = tx - x;
		float dy = ty - y;
		
		return ((dx*dx)+(dy*dy));
	}

}
