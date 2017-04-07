package knmi.msolve.controller;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import knmi.msolve.model.generate.BackTrackingMazeGenerator;
import knmi.msolve.model.generate.IMazeGenerator;
import knmi.msolve.model.generate.KruskalMazeGenerator;
import knmi.msolve.model.generate.PrimMazeGenerator;
import knmi.msolve.model.generate.RecursiveDivisionMazeGenerator;
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

		/*
		IMazeGenerator gen = new BackTrackingMazeGenerator(200,100);
		Maze maze = gen.generate();

		IMazeSolver solver = new AStarMazeSolver(maze);
		Path path = solver.solve();
		
		if(path == null) {
			System.out.println("This maze has no solution!");
		}
		
		ViewPort vp = new ViewPort();
		vp.setVisible(true);
		vp.setMaze(maze);
		vp.setPath(path);
		
		
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
		*/
		IMazeParser parser = new StaticMazeParser();
		Maze maze = parser.parse();
		IMazeSolver solver = new AStarMazeSolver(maze);
		Path path = solver.solve();
		
		//path.forEach(node -> System.out.println("[x="+node.x + ",y=" + node.y+"]"));
		ViewPort vp = new ViewPort();
		vp.setVisible(true);
		vp.setMaze(maze);
		vp.setPath(path);
		
	}

}
