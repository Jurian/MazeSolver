package knmi.msolve.model.parse;

import knmi.msolve.model.maze.Maze;

/**<p>
 * Interface for parsing mazes. In most cases only a reference to this interface is necessary.
 * </p>
 * @author baasj
 *
 */
public interface IMazeParser {
	Maze parse();
}
