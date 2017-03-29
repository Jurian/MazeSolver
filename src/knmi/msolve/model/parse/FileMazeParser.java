package knmi.msolve.model.parse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import knmi.msolve.model.maze.Maze;
import knmi.msolve.model.maze.Node;

public class FileMazeParser extends MazeParser {

	private final Path filePath;
	
	public FileMazeParser(String fileDir) {
		this(Paths.get(fileDir));
	}
	
	public FileMazeParser(Path filePath) {
		this.filePath = filePath;
	}
	
	@Override
	public Maze parse() {
		
		try {
			Boolean[][] data = Files.lines(filePath).map(line -> {
				return line.chars()
				.boxed()
				.map(c -> c == 1 ? true : false)
                .toArray(Boolean[]::new);
			}).toArray(Boolean[][]::new);
			
			Set<Node> nodes = createGraph(data);
			
			return new Maze(getWidth(), getHeight(), getEntrance(), getExit(), nodes);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
