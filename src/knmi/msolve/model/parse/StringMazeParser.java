package knmi.msolve.model.parse;

import java.util.Arrays;

import knmi.msolve.model.maze.Maze;

public class StringMazeParser implements IMazeParser {

	private final String[] rawData;
	
	public StringMazeParser(String[] rawData) {
		this.rawData = rawData;
	}
	
	@Override
	public Maze parse() {
		// Find character used for walls
		int wallChar = rawData[0].codePointAt(0);
		Boolean[][] data = Arrays.stream(rawData).map(line -> {
			return line.chars()
			.boxed()
			.map(c -> c == wallChar ? true : false)
            .toArray(Boolean[]::new);
		}).toArray(Boolean[][]::new);
		return new Maze(data);
	}

}
