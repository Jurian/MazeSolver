package knmi.msolve.model.parse;

import java.util.Set;

import knmi.msolve.model.maze.Maze;
import knmi.msolve.model.maze.Node;

public class StaticMazeParser extends MazeParser {

	private static final Boolean[][] DATA = {
		{true, 	false, 	true, 	true},
		{true, 	false, 	false, 	true},
		{true, 	true, 	false, 	true},
		{true, 	false, 	false, 	true},
		{true, 	false, 	true, 	true},
	};

	@Override
	public Maze parse() {
		Set<Node> nodes = createGraph(DATA);
		
		return new Maze(getWidth(), getHeight(), getEntrance(), getExit(), nodes);
	}

}
