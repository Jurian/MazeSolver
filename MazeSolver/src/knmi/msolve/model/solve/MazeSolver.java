package knmi.msolve.model.solve;

import knmi.msolve.model.maze.Maze;

public abstract class MazeSolver implements IMazeSolver {

	private final Maze maze;
	
	public MazeSolver(Maze maze) {
		this.maze = maze;
	}

	public Maze getMaze() {
		return maze;
	}

}
