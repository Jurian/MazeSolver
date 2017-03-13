package knmi.msolve.view;

import javax.swing.JFrame;

import knmi.msolve.model.maze.Maze;
import knmi.msolve.model.maze.Path;

public class ViewPort extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private MazeView mazePanel;
	
	public ViewPort(){
		mazePanel = new MazeView();
		getContentPane().add(mazePanel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	}

	public void setMaze(Maze maze){
		mazePanel.setMaze(maze);
		pack();
		repaint();
	}
	
	public void setPath(Path path) {
		mazePanel.setPath(path);
		repaint();
	}
}
