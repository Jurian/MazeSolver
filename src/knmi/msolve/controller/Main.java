package knmi.msolve.controller;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import knmi.msolve.model.maze.Maze;
import knmi.msolve.model.maze.Path;
import knmi.msolve.model.parse.IMazeParser;
import knmi.msolve.model.parse.ImageMazeParser;
import knmi.msolve.model.parse.StaticMazeParser;
import knmi.msolve.model.solve.AStarMazeSolver;
import knmi.msolve.model.solve.IMazeSolver;
import knmi.msolve.view.ViewPort;

public class Main {

	public static void main(String[] args) {
		
		
		//Create a file chooser
		final JFileChooser fc = new JFileChooser();
		IMazeParser parser = null;
		//In response to a button click:
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				parser = new ImageMazeParser(ImageIO.read(fc.getSelectedFile()));
	        } catch (IOException e) {
	        	
	        }
	    }
		//IMazeParser parser = new StaticMazeParser();
		Maze maze = parser.parse();
		IMazeSolver solver = new AStarMazeSolver(maze);
		Path path = solver.solve();
		
		System.out.println(path.length());
		//path.forEach(node -> System.out.println("[x="+node.x + ",y=" + node.y+"]"));
		ViewPort vp = new ViewPort();
		vp.setVisible(true);
		vp.setMaze(maze);
		vp.setPath(path);
		
	}

}
