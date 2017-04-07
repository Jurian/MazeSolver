package knmi.msolve.model.generate;

import knmi.msolve.model.maze.Maze;

/**<p>
 * Interface for generating mazes. In most cases only a reference to this interface is necessary.
 * </p>
 * @author baasj
 *
 */
public interface IMazeGenerator {
	Maze generate();
}
