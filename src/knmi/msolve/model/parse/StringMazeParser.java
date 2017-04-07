package knmi.msolve.model.parse;

import java.util.Arrays;

/**
 * This class can parse a maze from a String array. One character (top left) is assumed to represent a wall, any others are treated as pathways.
 * @author baasj
 *
 */
public class StringMazeParser extends MazeParser {

	private final String[] rawData;
	
	public StringMazeParser(String[] rawData) {
		this.rawData = rawData;
	}
	
	@Override
	public Boolean[][] parseMaze() {
		// Find character used for walls
		int wallChar = rawData[0].codePointAt(0);
		Boolean[][] data = Arrays.stream(rawData).map(line -> {
			return line.chars()
			.boxed()
			.map(c -> c == wallChar ? true : false)
            .toArray(Boolean[]::new);
		}).toArray(Boolean[][]::new);
		
		return data;
	}

}
