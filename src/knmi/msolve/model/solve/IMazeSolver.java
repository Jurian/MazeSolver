package knmi.msolve.model.solve;

import knmi.msolve.model.maze.Path;

/**<p>
 * Interface for solving mazes. In most cases only a reference to this interface is necessary.
 * </p>
 * @author baasj
 *
 */
public interface IMazeSolver {
	Path solve();
}
