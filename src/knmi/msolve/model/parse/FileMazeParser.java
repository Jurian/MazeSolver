package knmi.msolve.model.parse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * This class can parse a maze from a text file. One character (top left) is assumed to represent a wall, any others are treated as pathways.
 * @author baasj
 *
 */
public class FileMazeParser extends MazeParser {

	private final Path filePath;
	
	public FileMazeParser(String fileDir) {
		this(Paths.get(fileDir));
	}
	
	public FileMazeParser(Path filePath) {
		this.filePath = filePath;
	}
	
	@Override
	public Boolean[][] parseMaze() {
		
		try {
			// Find character used for walls
			int wallChar = Files.lines(filePath).findFirst().get().codePointAt(0);
			Boolean[][] data = Files.lines(filePath).map(line -> {
				return line.chars()
				.boxed()
				.map(c -> c == wallChar ? true : false)
                .toArray(Boolean[]::new);
			}).toArray(Boolean[][]::new);
			
			return data;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
