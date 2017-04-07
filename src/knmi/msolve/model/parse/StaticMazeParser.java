package knmi.msolve.model.parse;

/**
 * Returns a simple pre-defined maze. Useful for debugging purposes.
 * @author baasj
 *
 */
public class StaticMazeParser extends MazeParser {

	private static final Boolean[][] DATA = {
		{true, 	true, 	true, 	true, false, true},
		{true, 	false, 	false, 	false, false, true},
		{true, 	false, 	true, 	true, false, true},
		{true, 	false, 	false, 	true, false, true},
		{true, 	true, 	false, 	true, true, true},

	};

	@Override
	public Boolean[][] parseMaze() {
		return DATA;
	}

}
