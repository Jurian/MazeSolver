package knmi.msolve.model.generate;

/**<p>
 * Generates a maze with no walls. Useful for debugging purposes
 * </p>
 * @author baasj
 *
 */
public class EmptyMazeGenerator extends MazeGenerator {

	public EmptyMazeGenerator(int width, int height, boolean polar) {
		super(width, height, polar);
	}

	@Override
	protected void generateMaze() {
		// Do nothing
	}

}
