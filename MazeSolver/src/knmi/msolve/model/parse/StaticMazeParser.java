package knmi.msolve.model.parse;

import knmi.msolve.model.Maze;

public class StaticMazeParser implements IMazeParser {

	private static final Maze maze = new Maze(new Boolean[][]{
		{true, 	false, 	true, 	true},
		{true, 	false, 	false, 	true},
		{true, 	true, 	false, 	true},
		{true, 	false, 	false, 	true},
		{true, 	false, 	true, 	true},
	});
	
	@Override
	public Maze parse() {
		return StaticMazeParser.maze;
	}

}
