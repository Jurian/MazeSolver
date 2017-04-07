package knmi.msolve.model.solve;

import knmi.msolve.model.maze.Maze;

/**
 * Wrapper for IMazeSolver interface. Concrete solver algorithms should subclass this abstract class.
 * @author baasj
 *
 */
public abstract class MazeSolver implements IMazeSolver {

	private final Maze maze;
	
	public MazeSolver(Maze maze) {
		this.maze = maze;
	}

	public Maze getMaze() {
		return maze;
	}

}
