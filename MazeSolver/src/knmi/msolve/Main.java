package knmi.msolve;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import knmi.msolve.model.maze.Maze;
import knmi.msolve.model.parse.IMazeParser;
import knmi.msolve.model.parse.ImageMazeParser;
import knmi.msolve.model.parse.StaticMazeParser;

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

		Maze m = parser.parse();
		
		//IMazeParser parser = new StaticMazeParser();
		//Maze m = parser.parse();
		

	}

}
